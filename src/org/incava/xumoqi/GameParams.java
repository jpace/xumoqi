package org.incava.xumoqi;

public class GameParams {
	private final int wordLength;
	private final String gameType;
	
	public GameParams(int wordLength, String gameType) {
		this.wordLength = wordLength;
		this.gameType = gameType;
	}

	public int getWordLength() {
		return wordLength;
	}

	public String getGameType() {
		return gameType;
	}
}
