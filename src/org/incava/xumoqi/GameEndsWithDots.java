package org.incava.xumoqi;

import java.util.ArrayList;

import android.content.res.Resources;

public class GameEndsWithDots extends GameDottedWords {
	private final WordList wordList;

	public GameEndsWithDots(Resources resources, int length, int nDots) {
		this(WordLists.getWordList(resources, length), length, nDots);
	}
	
	public GameEndsWithDots(WordList wordList, int length, int nDots) {
		super(wordList, length, nDots);
		this.wordList = wordList;
	}
	
	public int getDotIndex() {
		return getLength() - 1;
	}
	
	private String getStartString(Word queryWord) {
		// this is the substring up to the "." at the end
		String qstr = queryWord.toString();
		return qstr.substring(0, qstr.length() - 1);
	}
	
	public ArrayList<String> getMatching(Word queryWord) {
		String starting = getStartString(queryWord);
		return wordList.getStartingWith(starting);
	}
}
