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

import org.incava.xumoqi.R;
import org.incava.xumoqi.utils.Lo;
import org.incava.xumoqi.utils.Timer;
import android.content.res.Resources;
import android.util.SparseArray;
import android.util.SparseIntArray;

/**
 * A set of word lists.
 */
public class WordLists {
    private static WordLists instance = null;
        
    public static WordLists getInstance() {
        if (instance == null) {
            instance = new WordLists();
        }
        return instance;
    }
        
    private SparseIntArray lenToRes;
    private final SparseArray<WordList> wordListsByLength;
    
    private WordLists() {
        int[] twls = new int[] {
            R.raw.twl2,
            R.raw.twl3,
            R.raw.twl4,
            R.raw.twl5,
            R.raw.twl6,
            R.raw.twl7,
            R.raw.twl8,
            R.raw.twl9,
            R.raw.twl10,
            R.raw.twl11,
            R.raw.twl12,
            R.raw.twl13,
            R.raw.twl14,
            R.raw.twl15,
        };

        lenToRes = new SparseIntArray();

        int len = 2;
        for (int twl : twls) {
            lenToRes.put(len, twl);
            ++len;
        }
        
        wordListsByLength = new SparseArray<WordList>();
    }
    
    public WordList getWordList(Resources resources, int length) {
        Timer t = new Timer("WORDLISTS", "getWordList(" + length + ")");
        Lo.g(this, "getWordList.resources", resources);
        WordList wordList = wordListsByLength.get(length);
        if (wordList == null) {
            wordList = readWordList(resources, length); 
        }
        t.done();
        return wordList;
    }
    
    private WordList readWordList(Resources resources, int length) {
        Timer t = new Timer("WORDLISTS", "readWordList(" + length + ")");
        int twlRes = lenToRes.get(length);
        InputStream is = resources.openRawResource(twlRes);
        WordList wordList = new WordList(is);
        wordListsByLength.put(length, wordList);
        t.done();
        return wordList;
    }
}
