package com.nick.cancan.service;

import com.nick.cancan.entity.BadWord;
import com.nick.cancan.model.MyQueryRequest;
import com.nick.cancan.repository.BadWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.List;

@Service
public class QueryBuilderServiceImpl implements QueryBuilderService {

    boolean inTest = true;

    @Autowired
    BadWordsRepository badWordsRepository;

    @Override
    public MyQueryRequest buildQuery(String screenname) {
        if(StringUtils.isEmpty(screenname)) {
            //TODO: log and throw an error
        }
        MyQueryRequest req = new MyQueryRequest();
        String q = req.getQuery();

        List<BadWord> badWords = badWordsRepository.findAllWithLimit();

        Iterator iterator = badWords.iterator();
        BadWord nextWord = (BadWord) iterator.next();

        while(iterator.hasNext()) {
            q = nextWord.getText().concat(" OR ").concat(q);
            nextWord = (BadWord) iterator.next();
        }
        //for the last word, don't add 'OR'
        //also add the 'from'
        q = q.concat(nextWord.getText()).concat(" from:").concat(screenname);

        req.setQuery(q);

        return req;
    }
}
