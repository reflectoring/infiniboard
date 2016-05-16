package com.github.reflectoring.infiniboard.harvester.source.url;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.packrat.source.UrlSource;
import com.github.reflectoring.infiniboard.packrat.source.UrlSourceRepository;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Date;

/**
 * job to retrieve UrlSource (configured via DB)
 */
public class UrlSourceRetrieveJob implements Job {

    private final static Logger LOG = LoggerFactory.getLogger(UrlSourceRetrieveJob.class);

    void retrieve(UrlSource urlSource, UrlSourceRepository repository) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(urlSource.getUrl());
        CloseableHttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();

        if (HttpStatus.SC_OK != statusCode) {
            LOG.error("could not fetch url {} / status {} / because of {}", urlSource.getUrl(), statusCode, response.getStatusLine().getReasonPhrase());
            return;
        }
        String content = IOUtils.toString(response.getEntity().getContent());

        //Save modified urlSource
        urlSource.setLastFetched(new Date());
        urlSource.setContent(content);
        urlSource.setStatusCode(statusCode);
        repository.save(urlSource);
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap configuration = context.getJobDetail().getJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) configuration.get(SchedulingService.PARAM_CONTEXT);
        UrlSourceRepository urlSourceRepository = applicationContext.getBean(UrlSourceRepository.class);
        String sourceConfigId = configuration.get("id").toString();
        UrlSource urlSource = urlSourceRepository.findOne(sourceConfigId);
        try {
            retrieve(urlSource, urlSourceRepository);
        } catch (IOException e) {
            //TODO
        }
    }
}
