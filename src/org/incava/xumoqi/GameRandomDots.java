package org.incava.xumoqi;

import java.util.List;
import java.util.Random;

public class GameRandomDots extends Game {
	public GameRandomDots(int nDots) {
		super(nDots);
	}
	
	@Override
	public String getAsPattern(String word) {
		Random rnd = new Random();
		int dot = rnd.nextInt(word.length());
		return word.substring(0, dot) + "." + word.substring(dot + 1, word.length());
	}

	public List<String> getMatching(WordList wordList, String queryString) {
		return wordList.getMatching(queryString);
	}
}
