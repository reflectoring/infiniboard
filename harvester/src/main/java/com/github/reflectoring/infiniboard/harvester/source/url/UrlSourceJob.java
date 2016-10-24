package com.github.reflectoring.infiniboard.harvester.source.url;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.github.reflectoring.infiniboard.harvester.source.SourceJob;
import com.github.reflectoring.infiniboard.packrat.source.SourceData;
import com.github.reflectoring.infiniboard.packrat.source.SourceDataRepository;

/**
 * job to retrieve UrlSource (configured via DB)
 */
public class UrlSourceJob extends SourceJob {

    private static final Logger LOG = LoggerFactory.getLogger(UrlSourceJob.class);

    /**
     * name used for registering this job
     */
    static final String JOB_TYPE = "urlSource";

    static final String PARAM_STATUS            = "status";
    static final String PARAM_CONTENT           = "content";
    static final String PARAM_URL               = "url";
    static final String PARAM_ENABLE_SSL_VERIFY = "enableSslVerification";

    @Override
    protected void executeInternal(ApplicationContext context, JobKey jobKey, Map<String, Object> configuration) {
        String  url              = configuration.get(PARAM_URL).toString();
        boolean enableSslVerify = isSslVerificationEnabled(configuration);

        try (CloseableHttpClient httpClient = getHttpClient(enableSslVerify)) {
            HttpGet               httpGet  = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);

            HashMap<String, Object> results = new HashMap<>();
            results.put(PARAM_STATUS, response.getStatusLine().getStatusCode());
            if (response.getEntity() != null) {
                results.put(PARAM_CONTENT, IOUtils.toString(response.getEntity().getContent()));
            } else {
                results.put(PARAM_CONTENT, response.getStatusLine().getReasonPhrase());
            }

            upsertResults(context, jobKey, results);

        } catch (SSLHandshakeException e) {
            String msg = "Could not establish SSL connection to '%s'. '%s' is set to '%s'. Cause: '%s'";
            logConnectionError(msg, url, enableSslVerify, e);
        } catch (HttpHostConnectException e) {
            String msg = "Could not establish connection to '%s'. '%s' is set to '%s'. Cause: '%s'";
            logConnectionError(msg, url, enableSslVerify, e);
        } catch (UnknownHostException e) {
            LOG.error("Could not establish connection to unknown host '{}'", e.getMessage());
        } catch (IOException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            LOG.error("could not fetch url '{}'", url, e);
        }
    }

    private boolean isSslVerificationEnabled(Map<String, Object> configuration) {
        Object o = configuration.get(PARAM_ENABLE_SSL_VERIFY);
        if (o == null) {
            return true;
        }

        return Boolean.valueOf(o.toString());
    }

    private void logConnectionError(String msg, String url, boolean enableSslVerify, IOException e) {
        String message = String.format(msg, url, PARAM_ENABLE_SSL_VERIFY, enableSslVerify, e.getMessage());
        if (enableSslVerify) {
            LOG.error(message);
        } else {
            LOG.warn(message);
        }
    }

    private void upsertResults(ApplicationContext context, JobKey jobKey, HashMap<String, Object> results) {
        SourceDataRepository repository   = context.getBean(SourceDataRepository.class);
        SourceData           existingData = repository.findByWidgetIdAndSourceId(jobKey.getGroup(), jobKey.getName());
        if (existingData != null) {
            existingData.setData(results);
        } else {
            existingData = new SourceData(jobKey.getGroup(), jobKey.getName(), results);
        }
        repository.save(existingData);
    }

    CloseableHttpClient getHttpClient(boolean disableSslVerify) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        if (disableSslVerify) {
            return HttpClients
                    .custom()
                    .setSSLHostnameVerifier(new NoopHostnameVerifier())
                    .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, (x509Certificates, s) -> true).build())
                    .build();
        }

        return HttpClients.createDefault();
    }

}
