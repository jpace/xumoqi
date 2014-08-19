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

package org.incava.xumoqi.games;

import java.util.ArrayList;
import java.util.TreeSet;

import org.incava.xumoqi.utils.Lo;
import org.incava.xumoqi.utils.Timer;
import org.incava.xumoqi.words.Word;
import org.incava.xumoqi.words.WordList;

public class GameNToNPlusOne implements Game {
    private final WordList fromWordList;
    private final WordList toWordList;
    private ArrayList<String> matching = null;
    
    public GameNToNPlusOne(WordList fromWordList, WordList toWordList) {
        this.fromWordList = fromWordList;
        this.toWordList = toWordList;
    }

    @Override
    public Word getQueryWord() {
        return getQueryWord(false);
    }
    
    private Word getQueryWord(boolean includeWhenOnlyPlurals) {
        // TODO: no plurals (other than 2-letter words?)
        Timer t = new Timer("N-N+1", "getQueryWord()");
        while (true) {
            String shorterWord = fromWordList.getRandomWord();
            matching = fetchMatching(shorterWord);
            t.done("matching " + matching);
            
            if (matchingContainsOnlyPlural(shorterWord)) {
                matching = null;
            }
            
            if (matching != null && !matching.isEmpty()) {
                String asQuery = "_" + shorterWord + ", " + shorterWord + "_";
                Word word = new Word(shorterWord, Word.NO_INDEX, asQuery, null);
                t.done("word: " + word + " from fromWordList");
                return word;
            }
        }
    }

    public ArrayList<String> getMatching(Word queryWord) {
        return matching;
    }
    
    private ArrayList<String> getMatchingForWord(String str, int dotIndex) {
        Word word = new Word(str, dotIndex);
        ArrayList<String> matching = toWordList.getMatching(word);
        Lo.g(this, "matching", matching);
        return matching;
    }

    private ArrayList<String> fetchMatching(String str) {
        ArrayList<String> msw = getMatchingForWord(str + ".", str.length());
        ArrayList<String> mew = getMatchingForWord("." + str, 0);
        
        TreeSet<String> matchSet = new TreeSet<String>(msw);
        matchSet.addAll(mew);
        Lo.g(this, "matchSet", matchSet);
        
        return new ArrayList<String>(matchSet);
    }

    private boolean matchingContainsOnlyPlural(String str) {
        return matching.size() == 1 && matching.get(0).equals(str + 's');
    }
}
