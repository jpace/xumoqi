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
import java.util.ArrayList;
import java.util.List;

import org.incava.xumoqi.io.IOReader;
import org.incava.xumoqi.util.ListUtil;

public class WordList {
    private final List<String> words;
    private final WordMapByChar byFirst;
    private final WordMapByChar byLast;
    
    public WordList() {
        words = new ArrayList<String>();
        byFirst = new WordMapByChar(0);
        byLast = new WordMapByChar(-1);
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
    
    public WordList(List<String> words) {
        this();
        for (String word : words) {
            addWord(word);
        }
    }

    public ArrayList<String> getMatching(Word queryWord) {
        WordMapByChar wordMap = queryWord.startsWithBlank() ? byLast : byFirst;
        return wordMap.getMatchingForChar(queryWord);
    }
    
    public String getRandomWord() {
        return ListUtil.getRandomElement(words);
    }
    
    private void addWord(String word) {
        words.add(word);
        // TODO: optimize this: this adds 30% when reading the word list
        byFirst.addWord(word);
        byLast.addWord(word);
    }
}
