package com.nick.cancan.service;

import com.nick.cancan.entity.BadWord;
import com.nick.cancan.repository.BadWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class QueryBuilderServiceImpl implements QueryBuilderService {

    @Autowired
    BadWordsRepository badWordsRepository;

    @Override
    public String buildQuery(String screenname) {
        if(StringUtils.isEmpty(screenname)) {
            //TODO: log and throw an error
        }
        String q = "?query=from:" + screenname;

        List<BadWord> badWords = badWordsRepository.findAll();

        for(BadWord word : badWords) {
            q = q.concat(" ").concat(word.getText());
        }

        return q;
    }
}
