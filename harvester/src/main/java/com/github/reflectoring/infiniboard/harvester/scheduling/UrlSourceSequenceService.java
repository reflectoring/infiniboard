package com.github.reflectoring.infiniboard.harvester.scheduling;

import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.source.UrlSource;
import org.quartz.utils.counter.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.*;
import org.springframework.stereotype.Service;

@Service
public class UrlSourceSequenceService {

    @Autowired
    private MongoOperations mongoOperations;

    public long getNextSequence(String collection){
        UrlSource urlSource = mongoOperations.findAndModify(
                query(where("id").is(collection)),
                new Update().inc("seq", 1),
                options().returnNew(true),
                UrlSource.class);
        return urlSource.getSeq();
    }
}
