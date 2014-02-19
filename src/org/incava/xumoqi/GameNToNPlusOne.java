package org.incava.xumoqi;

import java.util.ArrayList;

import android.content.res.Resources;

public class GameNToNPlusOne extends Game {
	private final WordList fromWordList;
	private final WordList toWordList;
	private ArrayList<String> matching = null;
	
	public GameNToNPlusOne(Resources resources, int length) {
		this.fromWordList = WordLists.getWordList(resources, length);
		// TODO: read this asynchronously (it gets displayed in the status activity, after the query activity)
		this.toWordList = WordLists.getWordList(resources, length + 1);
	}

	@Override
	public Word getQueryWord() {
		// TODO: ensure there is at least one N+1 word
		// TODO: no plurals (other than 2-letter words?)
		Timer t = new Timer("N-N+1", "getQueryWord()");
		Word word = null;
		while (matching == null || matching.isEmpty()) {
			word = new Word(fromWordList.getRandomWord(), -1);
			t.done("word: " + word + " from fromWordList");
			matching = getMatching(word);
			t.done("matching " + matching);
		}

		t.done("final word: " + word);
		return word;
	}
	
	public ArrayList<String> getMatching(Word queryWord) {
		Timer t = new Timer("N-N+1", "getMatching(" + queryWord + ")");
		ArrayList<String> matching = toWordList.getStartingOrEndingWith(queryWord.toString());
		t.done();
		return matching;
	}
}
