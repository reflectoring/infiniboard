package com.github.reflectoring.infiniboard.harvester.source.url;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.quartz.JobKey;
import org.springframework.context.ApplicationContext;

import com.github.reflectoring.infiniboard.packrat.source.SourceData;
import com.github.reflectoring.infiniboard.packrat.source.SourceDataRepository;

import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.*;

public class UrlSourceJobTest {

    private static final String SOURCE_ID  = "sourceId";
    private static final String WIDGET_ID  = "widgetId";
    private static final String MY_CONTENT = "myContent";
    private static final String MY_REASON  = "myReason";

    private ApplicationContext applicationContext;

    private SourceDataRepository repository;

    /**
     * overwrites the getHttpClient method to be able to inject mocked client
     */
    class TestJob extends UrlSourceJob {

        private CloseableHttpClient client;

        public TestJob(CloseableHttpClient client) {
            this.client = client;
        }

        @Override
        CloseableHttpClient getHttpClient() {
            return client;
        }
    }

    @Before
    public void initializeMocks() {
        applicationContext = mock(ApplicationContext.class);
        repository = mock(SourceDataRepository.class);
        when(applicationContext.getBean(SourceDataRepository.class)).thenReturn(repository);
    }

    @Test
    public void executeInternalReturnsContent()
            throws IOException {

        TestJob job = new TestJob(prepareHttpClientMock(true));
        job.executeInternal(applicationContext, new JobKey(SOURCE_ID, WIDGET_ID), createConfigMap());

        HashMap<String, Object> expectedData = new HashMap<>();
        expectedData.put(UrlSourceJob.PARAM_STATUS, HttpStatus.SC_OK);
        expectedData.put(UrlSourceJob.PARAM_CONTENT, MY_CONTENT);
        verify(repository).save(refEq(new SourceData(WIDGET_ID, SOURCE_ID, expectedData)));
    }

    @Test
    public void executeInternalReturnsReasonIfThereIsNoContent()
            throws IOException {

        TestJob job = new TestJob(prepareHttpClientMock(false));
        job.executeInternal(applicationContext, new JobKey(SOURCE_ID, WIDGET_ID), createConfigMap());

        HashMap<String, Object> expectedData = new HashMap<>();
        expectedData.put(UrlSourceJob.PARAM_STATUS, HttpStatus.SC_OK);
        expectedData.put(UrlSourceJob.PARAM_CONTENT, MY_REASON);
        verify(repository).save(refEq(new SourceData(WIDGET_ID, SOURCE_ID, expectedData)));
    }

    private HashMap<String, Object> createConfigMap() {
        HashMap<String, Object> config = new HashMap<>();
        config.put(UrlSourceJob.PARAM_URL, "myUrl");
        return config;
    }

    /**
     * @param withContent
     *         defines if an entity should be returned (otherwise simulating an empty result)
     */
    private CloseableHttpClient prepareHttpClientMock(boolean withContent)
            throws IOException {
        CloseableHttpClient   httpClient   = mock(CloseableHttpClient.class);
        CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        StatusLine statusLine = mock(StatusLine.class);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(statusLine.getReasonPhrase()).thenReturn(MY_REASON);

        if (withContent) {
            HttpEntity httpEntity = mock(HttpEntity.class);
            when(httpResponse.getEntity()).thenReturn(httpEntity);
            when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream(MY_CONTENT.getBytes()));
        }

        return httpClient;
    }

}
