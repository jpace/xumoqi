package org.incava.xumoqi;

import java.util.Arrays;
import java.util.List;

import android.content.res.Resources;

public class GameTwoToThree extends Game {
	private final WordList fromWordList;
	private final WordList toWordList;
	
	public GameTwoToThree(Resources resources, int length) {
		super(null, length);
		this.fromWordList = Dictionary.getWordList(resources, length);
		this.toWordList = Dictionary.getWordList(resources, length + 1);
	}

	@Override
	public String getQueryWord() {
		return fromWordList.getRandomWord();
	}

	public List<String> getMatching(String queryString) {
		return toWordList.getStartingOrEndingWith(queryString);
	}
}
