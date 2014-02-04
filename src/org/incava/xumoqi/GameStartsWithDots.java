package org.incava.xumoqi;

import java.util.List;

import android.content.res.Resources;

public class GameStartsWithDots extends GameDottedWords {
	private final WordList wordList;
	
	public GameStartsWithDots(Resources resources, int length, int nDots) {
		this(Dictionary.getWordList(resources, length), length, nDots);
	}
	
	public GameStartsWithDots(WordList wordList, int length, int nDots) {
		super(wordList, length, nDots);
		this.wordList = wordList;
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
		return wordList.getEndingWith(str);
	}
}
