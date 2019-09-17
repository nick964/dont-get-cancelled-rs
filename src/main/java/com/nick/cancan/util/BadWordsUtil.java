package com.nick.cancan.util;

import com.nick.cancan.entity.Word;

import java.util.HashMap;
import java.util.List;

public class BadWordsUtil {

    public static HashMap<String, Integer> buildBadWordMaps(List<Word> words) {
        HashMap<String, Integer> badWordsMap = new HashMap<>();
        for(Word word : words) {
            badWordsMap.put(word.getText(), word.getSeverity());
        }
        return badWordsMap;
    }
}
