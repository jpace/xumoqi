/*
  XuMoQi - word game program
  Copyright (C) 2014  Jeff Pace

  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along
  with this program; if not, write to the Free Software Foundation, Inc.,
  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

  See https://raw.github.com/jpace/xumoqi/master/LICENSE for the full license.

  The full source code for this program is available at:
  http://github.com/jpace/xumoqi

  This program includes code from the GPL'd program:
  http://sourceforge.net/projects/scrabbledict/
*/

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
    	
    	Timer timer = new Timer("WORDLIST", "read");
    	
        IOReader iordr = new IOReader() {
        	public void onRead(String str) {
        		addWord(str);
        	}
        };
        iordr.readStream(is);

        timer.done();
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
    
    public ArrayList<String> getMatchingStartsWith(Character ch, String pat) {
    	List<String> forChar = byFirst.getForChar(ch);
    	return getMatching(pat, forChar);
    }
    
    public ArrayList<String> getMatchingEndsWith(Character ch, String pat) {
    	List<String> forChar = byLast.getForChar(ch);
    	return getMatching(pat, forChar);
    }
    
    public ArrayList<String> getStartingWith(String str) {
    	Timer timer = new Timer("WORDLIST", "getStartingWith");
    	ArrayList<String> matching = byFirst.getMatches(str.charAt(0), str);
    	timer.done();
    	return matching;
    }

    public ArrayList<String> getEndingWith(String str) {
    	Timer timer = new Timer("WORDLIST", "getEndingWith");
    	ArrayList<String> matching = byLast.getMatches(str.charAt(str.length() - 1), str);
    	timer.done();
    	return matching;
    }
    
    public ArrayList<String> getMatching(String pat) {
    	return getMatching(pat, words);
    }
    
    public ArrayList<String> getMatching(String pat, List<String> strs) {
    	Timer timer = new Timer("WORDLIST", "getMatching");

    	ArrayList<String> matching = new ArrayList<String>();
    	Pattern pattern = Pattern.compile(pat);
    	for (String str : strs) {
    		if (pattern.matcher(str).matches()) {
    			matching.add(str);
    		}
    	}
    	timer.done();
    	
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

    public ArrayList<String> getStartingOrEndingWith(String str) {
    	Timer timer = new Timer("WORDLIST", "getStartingOrEndingWith");

    	Set<String> matching = new TreeSet<String>();
    	for (String word : words) {
    		if (word.endsWith(str) || word.startsWith(str)) {
    			matching.add(word);
    		}
    	}
    	
    	timer.done();
    	
    	return new ArrayList<String>(matching);
    }
}
