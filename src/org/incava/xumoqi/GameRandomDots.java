package org.incava.xumoqi;

import java.util.ArrayList;
import java.util.Random;

import android.content.res.Resources;

public class GameRandomDots extends GameDottedWords {
	private final WordList wordList;
	
	public GameRandomDots(Resources resources, int length, int nDots) {
		this(WordLists.getWordList(resources, length), length, nDots);
	}
	
	public GameRandomDots(WordList wordList, int length, int nDots) {
		super(wordList, length, nDots);
		this.wordList = wordList;
	}
	
	public int getDotIndex() {
		// handles only one dot for now
		Random rnd = new Random();
		return rnd.nextInt(getLength());
	}
	
	public ArrayList<String> getMatching(Word queryWord) {
		String pat = queryWord.asPattern();
		return wordList.getMatching(pat);
	}
}
