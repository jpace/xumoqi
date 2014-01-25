package org.incava.xumoqi;

import java.io.*;

import android.content.res.Resources;
import android.util.Log;
import android.util.SparseArray;

/**
 * Represents a list of words. The input file must be sorted, in the format:
 *
 * <pre>
 * abc
 * def
 * ghij
 * klmno</pre>
 *
 * and must be in ascending order of the number of characters.
 */
public class Dictionary {
    public static final String TWL_SORTED = "twl-sorted-by-length.txt";
    private static Dictionary twl = null;
    private static int dictLength;
    
    public static WordList getWordList(Resources resources, int length) {
    	if (twl == null || length > dictLength) {
    		InputStream is = resources.openRawResource(R.raw.twl);
    		twl = new Dictionary(is, length);
    		dictLength = length;
    	}
		return twl.getWordList(length);
    }
    
    private final static InputStream getInputStream(Resources resources, int length) {
    	return null;
    }
    
    private final SparseArray<WordList> wordListsByLength;

    public Dictionary(String dictFileName, Integer maxLength) throws Exception {
        this(new FileInputStream(dictFileName), maxLength);
    }

    public Dictionary(InputStream dictStream, Integer maxLength) {
        this(dictStream, maxLength, null);
    }

    public Dictionary(Resources resources, int length) {
    	this(getInputStream(resources, length), length);
    }

    public Dictionary(InputStream dictStream, Integer maxLength, Integer minLength) {
        this.wordListsByLength = new SparseArray<WordList>();
        long start = System.currentTimeMillis();
        Log.i("DICT", "start: " + start);
        
        IO io = new IO() {
        	public void onRead(String str, Integer len) {
        		addWord(str, len);
        	}
        };
        io.readStream(dictStream, maxLength);
        
        long end = System.currentTimeMillis();
        Log.i("DICT", "end: " + start);
        Log.i("DICT", "duration: " + (end - start));
    }

    private void addWord(String word, int len) {
        WordList wl = wordListsByLength.get(len);
        if (wl == null) {
            wl = new WordList();
            wordListsByLength.put(len, wl);
        }
        wl.addWord(word);
    }

    public WordList getWordList(int len) {
        return wordListsByLength.get(len);
    }
}
