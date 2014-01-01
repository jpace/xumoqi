package org.incava.xumoqi;

import java.util.*;
import java.util.regex.Pattern;

import android.util.Log;

public class WordList {
    private final List<String> words;
    private final WordMapByChar byFirst;
    private final WordMapByChar byLast;
    
    public WordList() {
        words = new ArrayList<String>();
        byFirst = new WordMapByChar() {
                public boolean isMatch(String word, String str) {
                    return word.startsWith(str);
                }
            };
        
        byLast = new WordMapByChar() {
                public boolean isMatch(String word, String str) {
                    return word.endsWith(str);
                }
            };
    }

    public void addWord(String word) {
        words.add(word);
        addWord(byFirst, 0, word);
        addWord(byLast, word.length() - 1, word);
    }
    
    private void addWord(WordMapByChar wordMap, int idx, String word) {
    	wordMap.addWord(word.charAt(idx), word);
    }

    public List<String> getWords() {
        return words;
    }

    public List<String> getStartingWith(String str) {
        return byFirst.getMatches(str.charAt(0), str);
    }

    public List<String> getEndingWith(String str) {
        return byLast.getMatches(str.charAt(str.length() - 1), str);
    }
    
    public List<String> getMatching(String pat) {
    	long start = System.currentTimeMillis();
    	Log.i("WORDLIST", "starting: " + start);
    	List<String> matching = new ArrayList<String>();
    	Pattern pattern = Pattern.compile(pat);
    	for (String word : words) {
    		if (pattern.matcher(word).matches()) {
    			matching.add(word);
    		}
    	}
    	long done = System.currentTimeMillis();
    	Log.i("WORDLIST", "done: " + done + "; " + (done - start));
    	return matching;
    }
}
