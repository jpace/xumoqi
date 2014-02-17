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
	
	@Override
	public String getAsPattern(String word) {
		// handles only one dot for now
		Random rnd = new Random();
		int dotIdx = rnd.nextInt(word.length());
		return word.substring(0, dotIdx) + "." + word.substring(dotIdx + 1, word.length());
	}

	public ArrayList<String> getMatching(String queryString) {
		return wordList.getMatching(queryString);
	}
}
