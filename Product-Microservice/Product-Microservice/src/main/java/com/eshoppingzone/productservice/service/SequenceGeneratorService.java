package com.eshoppingzone.productservice.service;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.eshoppingzone.productservice.entity.DbSequence;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;



@Service
public class SequenceGeneratorService {

    /*declaring Mongo Operations a object named mongo*/
	
    @Autowired
    public MongoOperations mongo;

    public int getSequenceNum(String seqName) {
        Query query=new Query(Criteria.where("id").is(seqName));

        /*incrementing key value by 1 every time the method call happens*/
        
        Update update = new Update().inc("seq", 1);
        DbSequence counter= mongo.findAndModify(query, update, options().returnNew(true).upsert(true), DbSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
