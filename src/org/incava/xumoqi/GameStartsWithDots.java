package org.incava.xumoqi;

import java.util.List;

public class GameStartsWithDots extends GameDottedWords {
	public GameStartsWithDots(WordList wordList, int nDots) {
		super(wordList, nDots);
	}
	
	private String getSubstring(String str) {
		int nDots = getNumDots();
		return str.substring(nDots, str.length());
	}
	
	public String getAsPattern(String word) {
		int nDots = getNumDots();
		return Util.repeat(".", nDots) + getSubstring(word); 
	}

	public List<String> getMatching(String queryString) {
		String str = getSubstring(queryString);
		return wordList.getEndingWith(str);
	}
}
