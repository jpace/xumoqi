package org.incava.xumoqi;

import java.util.List;
import java.util.Random;

import android.content.res.Resources;

public class GameRandomDots extends GameDottedWords {
	private final WordList wordList;
	
	public GameRandomDots(Resources resources, int length, int nDots) {
		this(Dictionary.getWordList(resources, length), length, nDots);
	}
	
	public GameRandomDots(WordList wordList, int length, int nDots) {
		super(wordList, length, nDots);
		this.wordList = wordList;
	}
	
	@Override
	public String getAsPattern(String word) {
		// handles only one dot for now
		Random rnd = new Random();
		int dot = rnd.nextInt(word.length());
		return word.substring(0, dot) + "." + word.substring(dot + 1, word.length());
	}

	public List<String> getMatching(String queryString) {
		return wordList.getMatching(queryString);
	}
}
