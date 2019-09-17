package com.nick.cancan.service;

import com.nick.cancan.entity.Word;
import com.nick.cancan.exception.CancelledServiceException;
import com.nick.cancan.model.MyQueryRequest;
import com.nick.cancan.repository.WordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.List;

@Service
public class QueryBuilderServiceImpl implements QueryBuilderService {

    @Autowired
    WordsRepository wordsRepository;

    @Override
    public MyQueryRequest buildQuery(String screenname) throws CancelledServiceException {
        if(StringUtils.isEmpty(screenname)) {
            //TODO: log and throw an error
        }

        MyQueryRequest req = new MyQueryRequest();
        String q = req.getQuery();
        q = q.concat("(");

        List<Word> words = wordsRepository.findAll();
        Iterator iterator = words.iterator();
        Word nextWord = (Word) iterator.next();
        while(iterator.hasNext()) {
            q = q.concat(nextWord.getText()).concat(" OR ");
            nextWord = (Word) iterator.next();
        }
        //for the last word, don't add 'OR'
        //also add the 'from'
        q = q.concat(nextWord.getText()).concat(") ");

        //append fund operator
        q = q.concat(buildFromOperation(screenname));

        if(q.length() > 1024) {
            throw new CancelledServiceException("Query is too long, user's screenname should not be this long..");
        }
        req.setQuery(q);

        return req;
    }

    private String buildFromOperation(String screenname) {
        return ("(from:".concat(screenname).concat(")"));
    }

}
