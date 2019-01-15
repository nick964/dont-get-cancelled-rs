package com.nick.cancan.service;

import com.nick.cancan.entity.BadWord;
import com.nick.cancan.model.MyQueryRequest;
import com.nick.cancan.repository.BadWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        MyQueryRequest req = new MyQueryRequest(screenname);
        String q = req.getQuery();

        List<BadWord> badWords = badWordsRepository.findAllWithLimit();
        for(BadWord word : badWords) {
            q = q.concat(" OR").concat(word.getText());
        }


        req.setQuery(q);

        return req;
    }
}
