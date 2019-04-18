package com.nick.cancan.util;

import com.nick.cancan.entity.BadWord;

import java.util.HashMap;
import java.util.List;

public class BadWordsUtil {

    public static HashMap<String, Integer> buildBadWordMaps(List<BadWord> words) {
        HashMap<String, Integer> badWordsMap = new HashMap<>();
        for(BadWord word : words) {
            badWordsMap.put(word.getText(), word.getSeverity());
        }
        return badWordsMap;
    }
}
