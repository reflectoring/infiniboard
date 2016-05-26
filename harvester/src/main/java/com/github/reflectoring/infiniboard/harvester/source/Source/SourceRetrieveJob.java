package com.github.reflectoring.infiniboard.harvester.source.Source;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import com.github.reflectoring.infiniboard.packrat.source.UrlResult;
import com.github.reflectoring.infiniboard.packrat.source.UrlResultRepository;
import com.github.reflectoring.infiniboard.packrat.source.UrlSource;
import com.github.reflectoring.infiniboard.packrat.source.UrlSourceRepository;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Date;

/**
 * job to retrieve UrlSource (configured via DB)
 */
public class SourceRetrieveJob implements Job {

    private final static Logger LOG = LoggerFactory.getLogger(SourceRetrieveJob.class);


    private RetrieveResult retrieve(UrlSource urlSource) {
        //TODO: For write dummyvalue for testing
        /*
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(urlSource.getUrl());

        RetrieveResult retrieveResult = new RetrieveResult();

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            retrieveResult.setStatusCode(response.getStatusLine().getStatusCode());
            if (retrieveResult.getStatusCode() != HttpStatus.SC_OK) {
                LOG.error(String.format("Could not fetch url %s / status %s / because of %s ", urlSource.getUrl(), retrieveResult.getStatusCode(), response.getStatusLine().getReasonPhrase()));
            }
            retrieveResult.setContent(IOUtils.toString(response.getEntity().getContent()));
        } catch (IOException e) {
            LOG.error(String.format("Could not retrieve sourceid %s cause of ", urlSource.getId()), e);
            retrieveResult.setErrorOccurred(true);
        }

        LOG.info(String.format("Executed SourceRetrieveJob for %s", urlSource.getId()));
        return retrieveResult;
        */

        LOG.info(String.format("Executed SourceRetrieveJob for %s", urlSource.getId()));
        return new RetrieveResult(false, 42, "new Content " + new Date());
    }

    private void updateUrlSource(UrlSourceRepository urlSourceRepository, UrlSource urlSource, int statusCode) {
        //Update urlSource
        urlSource.setStatusCode(statusCode);
        urlSourceRepository.save(urlSource);
    }

    private void updateContent(UrlResultRepository urlResultRepository, UrlResult urlResult, String content) {
        //Save Result in UrlResultDocument
        urlResult.setContent(content);
        urlResultRepository.save(urlResult);
    }

    private UrlResult getUrlResultObject(UrlResultRepository urlResultRepository, UrlSource urlSource) {
        UrlResult result = urlResultRepository.findByUrlSource(urlSource);

        //If no resultobject could be found, insert a knew one
        if (result == null) {
            result = urlResultRepository.save(new UrlResult(urlSource));
        }

        return result;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap configuration = context.getJobDetail().getJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) configuration.get(SchedulingService.PARAM_CONTEXT);
        UrlSourceRepository urlSourceRepository = applicationContext.getBean(UrlSourceRepository.class);
        UrlResultRepository urlResultRepository = applicationContext.getBean(UrlResultRepository.class);

        String urlSourceId = configuration.get("id").toString();
        UrlSource urlSource = urlSourceRepository.findOne(urlSourceId);
        UrlResult urlResult = getUrlResultObject(urlResultRepository, urlSource);

        //Handle Result
        RetrieveResult retrieveResult = retrieve(urlSource);
        updateUrlSource(urlSourceRepository, urlSource, retrieveResult.getStatusCode());
        if (!retrieveResult.isErrorOccurred()) {
            updateContent(urlResultRepository, urlResult, retrieveResult.getContent());
        }
    }

    private class RetrieveResult {

        private boolean errorOccurred;

        private int statusCode;

        private String content;

        RetrieveResult() {
            errorOccurred = false;
            statusCode = 0;
            content = StringUtils.EMPTY;
        }

        RetrieveResult(boolean errorOccurred, int statusCode, String content) {
            this.errorOccurred = errorOccurred;
            this.statusCode = statusCode;
            this.content = content;
        }

        public boolean isErrorOccurred() {
            return errorOccurred;
        }

        public void setErrorOccurred(boolean errorOccurred) {
            this.errorOccurred = errorOccurred;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
