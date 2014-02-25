package org.incava.xumoqi;

import java.util.ArrayList;

import android.content.res.Resources;

public class GameNToNPlusOne extends Game {
	private final WordList fromWordList;
	private final WordList toWordList;
	private ArrayList<String> matching = null;
	
	public GameNToNPlusOne(Resources resources, int length) {
		this.fromWordList = WordLists.getWordList(resources, length);
		this.toWordList = WordLists.getWordList(resources, length + 1);
	}

	@Override
	public Word getQueryWord() {
		// TODO: no plurals (other than 2-letter words?)
		Timer t = new Timer("N-N+1", "getQueryWord()");
		NtoNPlusOneWord word = null;
		while (matching == null || matching.isEmpty()) {
			word = new NtoNPlusOneWord(fromWordList.getRandomWord());
			t.done("word: " + word + " from fromWordList");
			matching = getMatching(word);
			t.done("matching " + matching);
		}

		t.done("final WORD: " + word);
		return word;
	}
	
	public ArrayList<String> getMatching(Word queryWord) {
		Timer t = new Timer("N-N+1", "getMatching(" + queryWord + ")");
		ArrayList<String> matching = toWordList.getStartingOrEndingWith(queryWord.toString());
		t.done();
		return matching;
	}
}
