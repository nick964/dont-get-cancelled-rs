package com.nick.cancan.controller;

import com.nick.cancan.entity.Word;
import com.nick.cancan.model.MyQueryRequest;
import com.nick.cancan.repository.WordsRepository;
import com.nick.cancan.service.QueryBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/words", produces = "application/json")
public class WordsController {

    @Autowired
    WordsRepository wordsRepository;


    @Autowired
    QueryBuilderService queryBuilderService;

    @CrossOrigin
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody
    List<Word> getTweetsFromTwit() throws Exception {
        List<Word> words;
        try {
            words = wordsRepository.findAll();
        } catch(Exception e ) {
            throw new Exception("Error getting words: " + e.getLocalizedMessage(), e);
        }

        return words;
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
