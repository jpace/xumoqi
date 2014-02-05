package org.incava.xumoqi;

import java.util.List;

import android.content.res.Resources;

public class GameNToNPlusOne extends Game {
	private final WordList fromWordList;
	private final WordList toWordList;
	
	public GameNToNPlusOne(Resources resources, int length) {
		super(null, length);
		this.fromWordList = Dictionary.getWordList(resources, length);
		this.toWordList = Dictionary.getWordList(resources, length + 1);
	}

	@Override
	public String getQueryWord() {
		// TODO: ensure there is at least one N+1 word
		// TODO: no plurals (other than 2-letter words?)
		return fromWordList.getRandomWord();
	}

	public List<String> getMatching(String queryString) {
		return toWordList.getStartingOrEndingWith(queryString);
	}
}
