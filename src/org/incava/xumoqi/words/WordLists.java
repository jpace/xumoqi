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
import org.incava.xumoqi.utils.Timer;
import org.incava.xumoqi.utils.Util;

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
	
	private Resources resources;
    private SparseIntArray lenToRes;
    private final SparseArray<WordList> wordListsByLength;
    
    private WordLists() {
        lenToRes = new SparseIntArray();
    	lenToRes.put(2, R.raw.twl2);
    	lenToRes.put(3, R.raw.twl3);
    	lenToRes.put(4, R.raw.twl4);
    	lenToRes.put(5, R.raw.twl5);
    	lenToRes.put(6, R.raw.twl6);
    	lenToRes.put(7, R.raw.twl7);
    	lenToRes.put(8, R.raw.twl8);
    	lenToRes.put(9, R.raw.twl9);
    	lenToRes.put(10, R.raw.twl10);
    	lenToRes.put(11, R.raw.twl11);
    	lenToRes.put(12, R.raw.twl12);
    	lenToRes.put(13, R.raw.twl13);
    	lenToRes.put(14, R.raw.twl14);
    	lenToRes.put(15, R.raw.twl15);
        
        wordListsByLength = new SparseArray<WordList>();
    }
    
    public void init(Resources res) {
    	resources = res;
    }
    
    public WordList getWordList(int length) {
    	Timer t = new Timer("WORDLISTS", "getWordList(..., " + length + ")");
    	Util.log("WORDLISTS", "resources", resources);
    	WordList wordList = wordListsByLength.get(length);
    	if (wordList != null) {
    		t.done("already exists");
    		return wordList;
    	}
    	
    	int twlRes = lenToRes.get(length);
		InputStream is = resources.openRawResource(twlRes);
		wordList = new WordList(is);
		wordListsByLength.put(length, wordList);
		t.done("read");
		return wordList;
    }
}
