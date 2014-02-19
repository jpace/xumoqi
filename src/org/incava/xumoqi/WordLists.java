package org.incava.xumoqi;

import java.io.InputStream;

import android.content.res.Resources;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;

/**
 * A set of word lists.
 */
public class WordLists {
    private static SparseIntArray lenToRes = new SparseIntArray();
    private static final SparseArray<WordList> wordListsByLength = new SparseArray<WordList>(); 
    
    static {
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
    }
    
    public static WordList getWordList(Resources resources, int length) {
    	Timer t = new Timer("WORDLISTS", "getWordList(..., " + length + ")");
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
