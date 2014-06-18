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
import org.incava.xumoqi.utils.Lo;

public class WordList {
    private final List<String> words;
    private final WordMapByChar byFirst;
    private final WordMapByChar byLast;
    private final Random random;
    
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

    public ArrayList<String> getMatching(Word queryWord) {
        return queryWord.getDotIndex() == 0 ? getMatchingEndsWith(queryWord) : getMatchingStartsWith(queryWord);
    }
    
    public String getRandomWord() {
    	int idx = random.nextInt(words.size());
    	return words.get(idx);
    }

    private void addWord(String word) {
        words.add(word);
        // TODO: optimize this: this adds 30% when reading the word list
        addWord(byFirst, 0, word);
        addWord(byLast, word.length() - 1, word);
    }
    
    private void addWord(WordMapByChar wordMap, int idx, String word) {
        wordMap.addWord(word.charAt(idx), word);
    }

    private ArrayList<String> getMatchingEndsWith(Word queryWord) {
        String qstr = queryWord.toString();
    	Lo.g(this, "qstr", qstr);
    	return getMatchingForChar(byLast, queryWord, qstr.length() - 1);
    }
    
    private ArrayList<String> getMatchingStartsWith(Word queryWord) {
    	return getMatchingForChar(byFirst, queryWord, 0);
    }    

    private ArrayList<String> getMatchingForChar(WordMapByChar wordMap, Word queryWord, int idx) {
        String pat = queryWord.asPattern();
        String qstr = queryWord.toString();
        char ch = qstr.charAt(idx);
        List<String> forChar = wordMap.getForChar(ch);
        return getMatching(pat, forChar);
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
}
