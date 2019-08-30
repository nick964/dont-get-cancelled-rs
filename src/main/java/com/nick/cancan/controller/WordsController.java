package com.nick.cancan.controller;

import com.nick.cancan.entity.BadWord;
import com.nick.cancan.model.MyQueryRequest;
import com.nick.cancan.repository.BadWordsRepository;
import com.nick.cancan.service.QueryBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/words", produces = "application/json")
public class WordsController {

    @Autowired
    BadWordsRepository badWordsRepository;


    @Autowired
    QueryBuilderService queryBuilderService;

    @CrossOrigin
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody
    List<BadWord> getTweetsFromTwit() throws Exception {
        List<BadWord> badWords;
        try {
            badWords = badWordsRepository.findAll();
        } catch(Exception e ) {
            throw new Exception("Error getting words: " + e.getLocalizedMessage(), e);
        }

        return badWords;
    }

    @CrossOrigin
    @RequestMapping(value = "/getQuery", method = RequestMethod.GET)
    public @ResponseBody
    MyQueryRequest getQuery() throws Exception {
        MyQueryRequest myQueryRequest = new MyQueryRequest();
        try {
            myQueryRequest = queryBuilderService.buildQuery("porncreeps");
        } catch(Exception e ) {
            throw new Exception("Error getting words: " + e.getLocalizedMessage(), e);
        }
        return myQueryRequest;
    }
}
