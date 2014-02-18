package org.incava.xumoqi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WordMapByChar {
    private final Map<Character, ArrayList<String>> byChar;
    
    public WordMapByChar() {
        byChar = new HashMap<Character, ArrayList<String>>();
    }

    public void addWord(Character ch, String word) {
        ArrayList<String> currList = byChar.get(ch);
        if (currList == null) {
            currList = new ArrayList<String>();
            byChar.put(ch, currList);
        }
        currList.add(word);
    }

    public List<String> getForChar(Character ch) {
        return byChar.get(ch);
    }
    
    public ArrayList<String> getMatches(Character ch, String str) {
        ArrayList<String> matches = new ArrayList<String>();
        List<String> words = getForChar(ch);
        if (words == null) {
            return matches;
        }
        for (String word : words) {
            if (isMatch(word, str)) {
                matches.add(word);
            }
        }
        return matches;
    }

    public abstract boolean isMatch(String word, String str);
}
