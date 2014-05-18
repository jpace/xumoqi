package org.incava.xumoqi.games;

import java.util.ArrayList;

import org.incava.xumoqi.utils.Timer;
import org.incava.xumoqi.utils.Util;
import org.incava.xumoqi.words.Word;
import org.incava.xumoqi.words.WordList;

public class GameQNoU extends GameDottedWords {
	public GameQNoU(int length, int nDots) {
		this(getWordList(length), length, nDots);
	}
	
	public GameQNoU(WordList wordList, int length, int nDots) {
		super(wordList, length, nDots, 2);
	}

	@Override
	public Word getQueryWord() {
		Timer t = new Timer("GAME-Q-NO-U", "getQueryWord()");
		for (int iters = 0; iters < 1000; ++iters) {
			String word = getRandomWord();
			Util.log("GAME-Q-NO-U", "word", word);
			t.done("iteration: " + iters + "; word: " + word);
			int qidx = word.indexOf('q');
			if (qidx >= 0 && (qidx == word.length() || word.charAt(qidx + 1) != 'u')) {
				t.done("word: " + word);
				return new Word(word, 2);
			}
		}
		t.done("done");
		return null;
	}

	@Override
	public ArrayList<String> getMatching(Word queryWord) {
		ArrayList<String> matching = new ArrayList<String>();
		matching.add("qaid");
		return matching;
	}

	@Override
	public String getAsQuery(Word word) {
		return word.asQuery();
	}
}
