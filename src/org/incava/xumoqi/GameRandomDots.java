package org.incava.xumoqi;

import java.util.Random;

public class GameRandomDots extends GameDottedWords {
	public GameRandomDots(WordList wordList, int nDots) {
		super(wordList, nDots);
	}
	
	@Override
	public String getAsPattern(String word) {
		Random rnd = new Random();
		int dot = rnd.nextInt(word.length());
		return word.substring(0, dot) + "." + word.substring(dot + 1, word.length());
	}
}
