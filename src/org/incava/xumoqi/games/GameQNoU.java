package org.incava.xumoqi.games;

import java.util.ArrayList;

import org.incava.xumoqi.words.Word;
import org.incava.xumoqi.words.WordList;
import org.incava.xumoqi.words.WordLists;

import android.content.res.Resources;

public class GameQNoU extends GameDottedWords {
	public GameQNoU(Resources resources, int length, int nDots) {
		this(WordLists.getWordList(resources, length), length, nDots);
	}
	
	public GameQNoU(WordList wordList, int length, int nDots) {
		super(wordList, length, nDots, 2);
	}

	@Override
	public Word getQueryWord() {
		return new Word("qaid", 2);
	}

	@Override
	public ArrayList<String> getMatching(Word queryWord) {
		ArrayList<String> matching = new ArrayList<String>();
		matching.add("qaid");
		return matching;
	}

	@Override
	public String getAsQuery(Word word) {
		return "qa_d";
	}
}
