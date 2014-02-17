package org.incava.xumoqi;

import java.util.ArrayList;

import android.content.res.Resources;

public class GameStartsWithDots extends GameDottedWords {
	private final WordList wordList;
	
	public GameStartsWithDots(Resources resources, int length, int nDots) {
		this(WordLists.getWordList(resources, length), length, nDots);
	}
	
	public GameStartsWithDots(WordList wordList, int length, int nDots) {
		super(wordList, length, nDots);
		this.wordList = wordList;
	}
	
	public int getDotIndex() {
		return 0;
	}
	
	private String getSubstring(String str) {
		int nDots = getNumDots();
		return str.substring(nDots, str.length());
	}
	
	public ArrayList<String> getMatching(Word queryWord) {
		// this is the substring after the "." at the beginning
		String qstr = queryWord.toString();
		String substr = qstr.substring(1, qstr.length());
		return wordList.getEndingWith(substr);
	}
}
