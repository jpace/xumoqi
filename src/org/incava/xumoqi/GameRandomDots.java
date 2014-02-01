package org.incava.xumoqi;

import java.util.Random;

import android.content.res.Resources;

public class GameRandomDots extends GameDottedWords {
	public GameRandomDots(Resources resources, int length, int nDots) {
		super(Dictionary.getWordList(resources, length), length, nDots);
	}
	
	@Override
	public String getAsPattern(String word) {
		// handles only one dot for now
		Random rnd = new Random();
		int dot = rnd.nextInt(word.length());
		return word.substring(0, dot) + "." + word.substring(dot + 1, word.length());
	}
}
