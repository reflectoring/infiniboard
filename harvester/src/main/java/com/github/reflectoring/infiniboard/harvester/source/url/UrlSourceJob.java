package com.github.reflectoring.infiniboard.harvester.source.url;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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

    private final static Logger LOG = LoggerFactory.getLogger(UrlSourceJob.class);

    /**
     * name used for registering this job
     */
    public static final String JOBTYPE = "urlSource";

    static final String PARAM_STATUS  = "status";
    static final String PARAM_CONTENT = "content";
    static final String PARAM_URL     = "url";

    @Override
    protected void executeInternal(ApplicationContext context, JobKey jobKey, Map configuration) {
        String url = configuration.get(PARAM_URL).toString();
        try (CloseableHttpClient httpClient = getHttpClient()) {
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

        } catch (IOException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            LOG.error("could not fetch url {} because {}", url, e);
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

    CloseableHttpClient getHttpClient()
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        // simple SSL hack, should be temporary (https://github.com/reflectoring/infiniboard/issues/40)
        return HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier())
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, (x509Certificates, s) -> true).build())
                .build();
    }

}
