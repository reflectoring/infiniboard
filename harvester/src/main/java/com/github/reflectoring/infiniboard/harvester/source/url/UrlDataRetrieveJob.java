package com.github.reflectoring.infiniboard.harvester.source.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * job to retrieve UrlData (configured via DB)
 */
@Component
public class UrlDataRetrieveJob {

    @Autowired
    private UrlDataRepository urlDataRepository;

    public void retrieve() {

    }

    void updateDB(UrlData urlData) {
        if (urlDataRepository.exists(urlData.getUrl())) {
            urlDataRepository.delete(urlData.getUrl());
        }
        urlDataRepository.save(urlData);
    }

}
