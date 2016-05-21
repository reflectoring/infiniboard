package com.github.reflectoring.infiniboard.harvester.source;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.packrat.source.Result;
import com.github.reflectoring.infiniboard.packrat.source.ResultRepository;
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
public class SourceRetrieveJob implements Job {

    private final static Logger LOG = LoggerFactory.getLogger(SourceRetrieveJob.class);

    void retrieve(UrlSource urlSource, UrlSourceRepository urlSourceRepository, Result result, ResultRepository resultRepository) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(urlSource.getUrl());
        CloseableHttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();

        if (HttpStatus.SC_OK != statusCode) {
            LOG.error(String.format("Could not fetch url %s / status %s / because of %s ", urlSource.getUrl(), statusCode, response.getStatusLine().getReasonPhrase()));
            return;
        }
        String content = IOUtils.toString(response.getEntity().getContent());

        //Update urlSource
        urlSource.setLastFetched(new Date());
        urlSource.setStatusCode(statusCode);
        urlSourceRepository.save(urlSource);

        //Update result
        result.setContent(content);
        resultRepository.save(result);
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap configuration = context.getJobDetail().getJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) configuration.get(SchedulingService.PARAM_CONTEXT);
        UrlSourceRepository urlSourceRepository = applicationContext.getBean(UrlSourceRepository.class);
        ResultRepository resultRepository = applicationContext.getBean(ResultRepository.class);

        String sourceConfigId = configuration.get("id").toString();
        UrlSource urlSource = urlSourceRepository.findOne(sourceConfigId);
        Result result = resultRepository.findOne(sourceConfigId);

        try {
            retrieve(urlSource, urlSourceRepository, result, resultRepository);
        } catch (IOException e) {
            LOG.error(String.format("Could not retrieve sourceid %s cause of ", sourceConfigId), e);
        }
    }
}
