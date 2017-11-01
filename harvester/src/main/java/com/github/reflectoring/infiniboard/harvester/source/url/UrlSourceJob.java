package com.github.reflectoring.infiniboard.harvester.source.url;

import com.github.reflectoring.infiniboard.harvester.source.SourceJob;
import com.github.reflectoring.infiniboard.packrat.source.SourceData;
import com.github.reflectoring.infiniboard.packrat.source.SourceDataRepository;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/** Job to retrieve content from an URL. */
public class UrlSourceJob extends SourceJob {

  private static final Logger LOG = LoggerFactory.getLogger(UrlSourceJob.class);

  /*
   * Name used for registering this job.
   */
  static final String JOB_TYPE = "urlSource";

  static final String PARAM_STATUS = "status";
  static final String PARAM_CONTENT = "content";
  static final String PARAM_ERRORS = "errors";
  static final String PARAM_URL = "url";
  static final String PARAM_USERNAME = "username";
  static final String PARAM_PASSWORD = "password";
  static final String PARAM_ENABLE_SSL_VERIFY = "enableSslVerification";

  @Override
  protected void executeInternal(
      ApplicationContext context, JobKey jobKey, Map<String, Object> configuration) {
    String url = configuration.get(PARAM_URL).toString();
    boolean enableSslVerify = isSslVerificationEnabled(configuration);
    CredentialsProvider credentialsProvider = configureBasicAuthentication(configuration);
    HttpClientContext clientContext = configureContext(credentialsProvider, url);

    HashMap<String, Object> data = new HashMap<>();
    List<String> errors = new ArrayList<>();
    try (CloseableHttpClient httpClient = configureHttpClient(enableSslVerify)) {
      HttpGet httpGet = new HttpGet(url);

      CloseableHttpResponse response;
      if (clientContext != null) {
        response = httpClient.execute(httpGet, clientContext);
      } else {
        response = httpClient.execute(httpGet);
      }

      data.put(PARAM_STATUS, response.getStatusLine().getStatusCode());
      if (response.getEntity() != null) {
        data.put(PARAM_CONTENT, IOUtils.toString(response.getEntity().getContent()));
      } else {
        data.put(PARAM_CONTENT, response.getStatusLine().getReasonPhrase());
      }

    } catch (SSLHandshakeException e) {
      String msg =
          logConnectionError(
              "Could not establish SSL connection to '%s'. '%s' is set to '%s'. Cause: '%s'",
              url, enableSslVerify, e);
      errors.add(msg);

    } catch (HttpHostConnectException e) {
      String msg =
          logConnectionError(
              "Could not establish connection to '%s'. '%s' is set to '%s'. Cause: '%s'",
              url, enableSslVerify, e);
      errors.add(msg);

    } catch (UnknownHostException e) {
      String msg =
          String.format("Could not establish connection to unknown host '%s'", e.getMessage());
      errors.add(msg);
      LOG.error(msg);

    } catch (IOException e) {
      String msg = String.format("could not fetch url '%s'", url);
      errors.add(msg);
      LOG.error(msg, e);
    }

    if (!errors.isEmpty()) {
      data.put(PARAM_ERRORS, errors);
    }

    upsertResults(context, jobKey, data);
  }

  private boolean isSslVerificationEnabled(Map<String, Object> configuration) {
    Object o = configuration.get(PARAM_ENABLE_SSL_VERIFY);
    if (o == null) {
      return true;
    }

    return Boolean.valueOf(o.toString());
  }

  private String logConnectionError(
      String msg, String url, boolean enableSslVerify, IOException e) {
    String message =
        String.format(msg, url, PARAM_ENABLE_SSL_VERIFY, enableSslVerify, e.getMessage());
    if (enableSslVerify) {
      LOG.error(message);
    } else {
      LOG.warn(message);
    }

    return message;
  }

  private void upsertResults(
      ApplicationContext context, JobKey jobKey, HashMap<String, Object> data) {
    SourceDataRepository repository = context.getBean(SourceDataRepository.class);
    SourceData existingData =
        repository.findByWidgetIdAndSourceId(jobKey.getGroup(), jobKey.getName());
    if (existingData != null) {
      existingData.setData(data);
    } else {
      existingData = new SourceData(jobKey.getGroup(), jobKey.getName(), data);
    }
    repository.save(existingData);
  }

  private <T> T getConfiguration(Map<String, Object> configuration, String key, Class<T> clazz) {
    Object o = configuration.get(key);

    if (o == null) {
      return null;
    }

    return clazz.cast(o);
  }

  private CredentialsProvider configureBasicAuthentication(Map<String, Object> configuration) {

    String username = getConfiguration(configuration, PARAM_USERNAME, String.class);
    String password = getConfiguration(configuration, PARAM_PASSWORD, String.class);

    if (username == null || password == null) {
      return null;
    }

    CredentialsProvider provider = new BasicCredentialsProvider();
    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
    provider.setCredentials(AuthScope.ANY, credentials);

    return provider;
  }

  private HttpClientContext configureContext(CredentialsProvider credentialsProvider, String url) {
    if (credentialsProvider != null) {
      try {
        URL targetUrl = new URL(url);

        HttpHost targetHost =
            new HttpHost(targetUrl.getHost(), targetUrl.getPort(), targetUrl.getProtocol());
        AuthCache authCache = new BasicAuthCache();
        authCache.put(targetHost, new BasicScheme());

        final HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credentialsProvider);
        context.setAuthCache(authCache);

        return context;
      } catch (MalformedURLException e) {
        LOG.error("Cannot parse URL '{}'", url, e);
      }
    }
    return null;
  }

  CloseableHttpClient configureHttpClient(boolean enableSslVerify) {

    HttpClientBuilder builder = HttpClientBuilder.create();

    if (enableSslVerify) {
      return builder.build();
    }

    SSLContext sslContext = null;
    try {
      sslContext =
          new SSLContextBuilder().loadTrustMaterial(null, (x509Certificates, s) -> true).build();
    } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
      LOG.error("Could not create ssl context", e);
    }

    builder.setSSLHostnameVerifier(new NoopHostnameVerifier()).setSSLContext(sslContext);

    return builder.build();
  }
}
