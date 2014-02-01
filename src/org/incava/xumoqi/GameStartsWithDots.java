package org.incava.xumoqi;

import java.util.List;

import android.content.res.Resources;

public class GameStartsWithDots extends GameDottedWords {
	public GameStartsWithDots(Resources resources, int length, int nDots) {
		super(Dictionary.getWordList(resources, length), length, nDots);
	}
	
	private String getSubstring(String str) {
		int nDots = getNumDots();
		return str.substring(nDots, str.length());
	}
	
	public String getAsPattern(String word) {
		int nDots = getNumDots();
		return Util.repeat(".", nDots) + getSubstring(word); 
	}

	public List<String> getMatching(String queryString) {
		String str = getSubstring(queryString);
		WordList wordList = getWordList();
		return wordList.getEndingWith(str);
	}
}
