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

package org.incava.xumoqi.words;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

import org.incava.xumoqi.io.IOReader;

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
    
    private ArrayList<String> getMatchingForChar(WordMapByChar wordMap, Character ch, String pat) {
    	List<String> forChar = wordMap.getForChar(ch);
    	return getMatching(pat, forChar);
    }
    
    public ArrayList<String> getMatchingStartsWith(Character ch, String pat) {
    	return getMatchingForChar(byFirst, ch, pat);
    }
    
    public ArrayList<String> getMatchingEndsWith(Character ch, String pat) {
    	return getMatchingForChar(byLast, ch, pat);
    }

    private ArrayList<String> getForIndex(WordMapByChar wordMap, String str, int idx) {
    	return wordMap.getMatches(str.charAt(idx), str);
    }

    public ArrayList<String> getStartingWith(String str) {
    	return getForIndex(byFirst, str, 0);
    }

    public ArrayList<String> getEndingWith(String str) {
    	return getForIndex(byLast, str, str.length() - 1);
    }
    
    public ArrayList<String> getMatching(String pat) {
    	return getMatching(pat, words);
    }
    
    private ArrayList<String> getMatching(String pat, List<String> strs) {
    	ArrayList<String> matching = new ArrayList<String>();
    	Pattern pattern = Pattern.compile(pat);
    	for (String str : strs) {
    		if (pattern.matcher(str).matches()) {
    			matching.add(str);
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

    public ArrayList<String> getStartingOrEndingWith(String str) {
    	Set<String> matching = new TreeSet<String>();
    	for (String word : words) {
    		if (word.endsWith(str) || word.startsWith(str)) {
    			matching.add(word);
    		}
    	}
    	
    	return new ArrayList<String>(matching);
    }
}
