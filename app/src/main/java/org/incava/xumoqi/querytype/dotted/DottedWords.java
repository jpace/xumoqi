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

  This program includes code from the GPL program:
  http://sourceforge.net/projects/scrabbledict/
*/

package org.incava.xumoqi.querytype.dotted;

import java.util.ArrayList;

import org.incava.xumoqi.querytype.QueryType;
import org.incava.xumoqi.words.Word;
import org.incava.xumoqi.words.WordList;

public abstract class DottedWords implements QueryType {
    private final WordList wordList;
    private final int length;

    public DottedWords(WordList wordList, int length) {
        this.wordList = wordList;
        this.length = length;
    }
    
    public abstract int getBlankIndex();
    
    public Word getQueryWord() {
        String word = wordList.getRandomWord();
        int dotIdx = getBlankIndex();
        return new Word(word, dotIdx);
    }
    
    public ArrayList<String> getMatching(Word queryWord) {
        return wordList.getMatching(queryWord);
    }
    
    public String getHint() {
        return null;
    }

    protected int getLength() {
        return length;
    }
}
