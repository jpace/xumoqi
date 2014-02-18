package org.incava.xumoqi;

import java.util.ArrayList;
import java.util.Random;

import android.content.res.Resources;

public class GameRandomDots extends GameDottedWords {
	private final WordList wordList;
	
	public GameRandomDots(Resources resources, int length, int nDots) {
		this(WordLists.getWordList(resources, length), length, nDots);
	}
	
	public GameRandomDots(WordList wordList, int length, int nDots) {
		super(wordList, length, nDots);
		this.wordList = wordList;
	}
	
	public int getDotIndex() {
		// handles only one dot for now
		Random rnd = new Random();
		return rnd.nextInt(getLength());
	}
	
	public ArrayList<String> getMatching(Word queryWord) {
		Util.log("GRANDOM", "queryWord", queryWord);
		int dotIdx = queryWord.getDotIndex();
		Util.log("GRANDOM", "dotIdx", dotIdx);
		String pat = queryWord.asPattern();
		Util.log("GRANDOM", "pat", pat);
		String qstr = queryWord.toString();
		if (dotIdx != 0) {
			return wordList.getMatchingStartsWith(qstr.charAt(0), pat);
		}
		else {
			return wordList.getMatchingEndsWith(qstr.charAt(getLength() - 1), pat);
		}
	}
}
