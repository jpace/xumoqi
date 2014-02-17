package org.incava.xumoqi;

import java.util.ArrayList;

import android.content.res.Resources;

public class GameNToNPlusOne extends Game {
	private final WordList fromWordList;
	private final WordList toWordList;
	
	public GameNToNPlusOne(Resources resources, int length) {
		this.fromWordList = WordLists.getWordList(resources, length);
		// TODO: read this asynchronously (it gets displayed in the status activity, after the query activity)
		this.toWordList = WordLists.getWordList(resources, length + 1);
	}

	@Override
	public Word getQueryWord() {
		// TODO: ensure there is at least one N+1 word
		// TODO: no plurals (other than 2-letter words?)
		return new Word(fromWordList.getRandomWord(), -1);
	}
	
	public ArrayList<String> getMatching(Word queryWord) {
		return toWordList.getStartingOrEndingWith(queryWord.toString());
	}
}
