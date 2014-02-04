package org.incava.xumoqi;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

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
    	
        IOReader iordr = new IOReader() {
        	public void onRead(String str) {
        		addWord(str);
        	}
        };
        iordr.readStream(is);
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
    	List<String> matching = new ArrayList<String>();
    	Pattern pattern = Pattern.compile(pat);
    	for (String word : words) {
    		if (pattern.matcher(word).matches()) {
    			matching.add(word);
    		}
    	}
    	return matching;
    }
    
	public String getRandomWord() {
		for (int i = 0; i < 100; ++i) {
			// TODO: this won't repeat the word, but it can repeat
			// the pattern created from the word. 
			int idx = random.nextInt(words.size());
			String word = words.get(idx);
			if (!lastWords.contains(word)) {
		    	lastWords.add(word);
				return word;
			}
		}
		return words.get(0);
	}

    public List<String> getStartingOrEndingWith(String str) {
    	Set<String> matching = new TreeSet<String>();
    	for (String word : words) {
    		if (word.endsWith(str) || word.startsWith(str)) {
    			matching.add(word);
    		}
    	}
    	return new ArrayList<String>(matching);
    }
}
