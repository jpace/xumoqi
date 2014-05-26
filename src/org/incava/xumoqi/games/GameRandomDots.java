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
import java.util.Random;

import org.incava.xumoqi.utils.Util;
import org.incava.xumoqi.words.Word;
import org.incava.xumoqi.words.WordList;

public class GameRandomDots extends GameDottedWords {
    private static int getRandomIndex(int length) {
        // handles only one dot for now
        Random rnd = new Random();
        return rnd.nextInt(length);
    }
    
    private final WordList wordList;
    
    public GameRandomDots(WordList wordList, int length, int nDots) {
        super(wordList, length, nDots, getRandomIndex(length));
        this.wordList = wordList;
    }
    
    public ArrayList<String> getMatching(Word queryWord) {
        String pat = queryWord.asPattern();
        Util.log(getClass(), "pat", pat);
        String qstr = queryWord.toString();
        Util.log(getClass(), "qstr", qstr);
        if (isBlank(0)) {
            Util.log(getClass(), "qstr", qstr);
            char lastChar = qstr.charAt(getLength() - 1);
            Util.log(getClass(), "lastChar", lastChar);
            return wordList.getMatchingEndsWith(lastChar, pat);
        }
        else {
        	char firstChar = qstr.charAt(0);
            Util.log(getClass(), "firstChar", firstChar);
            return wordList.getMatchingStartsWith(firstChar, pat);
        }
    }
}
