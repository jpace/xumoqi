package org.incava.xumoqi;

import java.util.*;
import java.io.*;

import android.content.res.Resources;

/**
 * Represents a list of words. The input file must in in the format:
 *
 * <pre>
 * abc
 * def
 * ghij
 * klmno
 * </pre>
 *
 * and must be in ascending order of the number of characters.
 */
public class Dictionary {
    public static final String TWL_SORTED = "twl-sorted-by-length.txt";
    private static Dictionary twl = null;
    private static int dictLength;
    
    public static Dictionary getTWL(Resources resources, int length) {
    	if (twl == null || length > dictLength) {
    		InputStream is = resources.openRawResource(R.raw.twl);
    		twl = new Dictionary(is, length);
    		dictLength = length;
    	}
		return twl;
    }
    
    private final Map<Integer, WordList> wordsByLength;

    public Dictionary(String dictFileName, Integer maxLength) throws Exception {
        this(new FileInputStream(dictFileName), maxLength);
    }

    public Dictionary(InputStream dictStream, Integer maxLength) {
        this(dictStream, maxLength, null);
    }

    public Dictionary(InputStream dictStream, Integer maxLength, Integer minLength) {
        this.wordsByLength = new HashMap<Integer, WordList>();
        
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(dictStream));
            String line = null;
            while ((line = br.readLine()) != null) {
                int len = line.length();
                if (maxLength != null && len > maxLength) {
                    break;
                }
                else if (minLength == null || len >= minLength) {
                    addWord(line, len);
                }
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void addWord(String word, int len) {
        WordList wl = wordsByLength.get(len);
        if (wl == null) {
            wl = new WordList();
            wordsByLength.put(len, wl);
        }
        wl.addWord(word);
    }

    public WordList getWordList(int len) {
        return wordsByLength.get(len);
    }

    public List<String> getStartingWith(String str, int len) {
        WordList wl = getWordList(len);
        return wl == null ? new ArrayList<String>() : wl.getStartingWith(str);
    }

    public List<String> getEndingWith(String str, int len) {
        WordList wl = getWordList(len);
        return wl == null ? new ArrayList<String>() : wl.getEndingWith(str);
    }
}
