package org.incava.xumoqi;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

import android.util.Log;

public class WordList {
    private final List<String> words;
    private final WordMapByChar byFirst;
    private final WordMapByChar byLast;
    private final Random random;
    private final List<String> lastWords;
    
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
    	random = new Random();
    	lastWords = new ArrayList<String>();
    }

    public WordList(InputStream is) {
    	this();
    	
        long start = System.currentTimeMillis();
        Log.i("WORDLIST", "start: " + start);

        IOReader iordr = new IOReader() {
        	public void onRead(String str) {
        		addWord(str);
        	}
        };
        iordr.readStream(is);
        
        long end = System.currentTimeMillis();
        Log.i("WORDLIST", "end: " + start);
        Log.i("WORDLIST", "duration: " + (end - start));
    }

    public void addWord(String word) {
        words.add(word);
        // TODO: optimize this: this adds 30% when reading the word list
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
    
	public String getRandomWord() {
		for (int i = 0; i < 100; ++i) {
			// TODO: this won't repeat the word, but it can repeat
			// the pattern created from the word. 
			int idx = random.nextInt(words.size());
			String word = words.get(idx);
	    	Log.i("WORDLIST", "word: " + word);
			if (!lastWords.contains(word)) {
		    	Log.i("WORDLIST", "returning: " + word);
		    	lastWords.add(word);
				return word;
			}
		}
    	Log.i("WORDLIST", "returning at 0: " + words.get(0));
		return words.get(0);
	}
}
