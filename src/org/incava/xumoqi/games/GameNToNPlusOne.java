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
import java.util.List;
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
    
    public String getAsQuery(Word word) {
        String str = word.toString();
        return "_" + str + ", " + str + "_";  
    }
    
    public Word getQueryWord(boolean includeOnlyPlurals) {
        // TODO: no plurals (other than 2-letter words?)
        Timer t = new Timer("N-N+1", "getQueryWord()");
        Word word = null;
        while (matching == null || matching.isEmpty()) {
            word = new Word(fromWordList.getRandomWord(), Word.NO_INDEX);
            t.done("word: " + word + " from fromWordList");
            matching = getMatching(word);
            t.done("matching " + matching);
            
            if (containsOnlyPlural(word) && !includeOnlyPlurals) {
                matching = null;
            }
        }

        t.done("final WORD: " + word);
        return word;
    }

    public ArrayList<String> getMatching(Word queryWord) {
        String str = queryWord.toString();
        
        Word startingWith = new Word(str + ".", str.length());
        ArrayList<String> msw = toWordList.getMatching(startingWith);
        Lo.g(this, "msw", msw);
        
        Word endingWith = new Word("." + str, 0);
        ArrayList<String> mew = toWordList.getMatching(endingWith);
        Lo.g(this, "mew", mew);
        
        TreeSet<String> matchSet = new TreeSet<String>(msw);
        matchSet.addAll(mew);
        
        Lo.g(this, "matchSet", matchSet);
        
        return new ArrayList<String>(matchSet);
    }

    private boolean containsOnlyPlural(Word word) {
        return matching.size() == 1 && matching.get(0).equals(word.toString() + 's');
    }
}
