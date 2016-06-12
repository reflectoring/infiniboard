package com.github.reflectoring.infiniboard.harvester.source.url;

import com.github.reflectoring.infiniboard.harvester.source.SourceJob;
import com.github.reflectoring.infiniboard.packrat.source.SourceData;
import com.github.reflectoring.infiniboard.packrat.source.SourceDataRepository;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * job to retrieve UrlSource (configured via DB)
 */
public class UrlSourceJob extends SourceJob {

    private final static Logger LOG = LoggerFactory.getLogger(UrlSourceJob.class);
    static final String PARAM_STATUS = "status";
    static final String PARAM_CONTENT = "content";
    static final String PARAM_URL = "url";

    @Override
    protected void executeInternal(ApplicationContext context, JobKey jobKey, Map configuration) {
        SourceDataRepository repository = context.getBean(SourceDataRepository.class);
        String url = configuration.get(PARAM_URL).toString();
        try (CloseableHttpClient httpClient = getHttpClient()) {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);

            HashMap<String, Object> results = new HashMap<>();
            results.put(PARAM_STATUS, response.getStatusLine().getStatusCode());
            if (response.getEntity() != null) {
                results.put(PARAM_CONTENT, IOUtils.toString(response.getEntity().getContent()));
            } else {
                results.put(PARAM_CONTENT, response.getStatusLine().getReasonPhrase());
            }

            repository.save(new SourceData(jobKey.getGroup(), jobKey.getName(), results));
        } catch (IOException e) {
            LOG.error("could not fetch url {} because {}", url, e);
        }
    }

    CloseableHttpClient getHttpClient() {
        return HttpClients.createDefault();
    }

}
