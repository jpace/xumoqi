/*
  XuMoQi - word game program
  Copyright (C) 2014  Jeff Pace

  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along
  with this program; if not, write to the Free Software Foundation, Inc.,
  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

  See https://raw.github.com/jpace/xumoqi/master/LICENSE for the full license.

  The full source code for this program is available at:
  http://github.com/jpace/xumoqi

  This program includes code from the GPL'd program:
  http://sourceforge.net/projects/scrabbledict/
 */

package org.incava.xumoqi.games;

import org.incava.xumoqi.words.Blanks;
import org.incava.xumoqi.words.Word;
import org.incava.xumoqi.words.WordList;

public abstract class GameDottedWords extends Game {
	private final WordList wordList;
	private final int length;
	private final Blanks blanks;

	public GameDottedWords(WordList wordList, int length, int nDots, int index) {
		this.wordList = wordList;
		this.length = length;
		this.blanks = new Blanks(nDots, index);
	}

	public Word getQueryWord() {
		String word = getRandomWord();
		int dotIdx = blanks.getIndex();
		return new Word(word, dotIdx);
	}

	protected int getNumDots() {
		return blanks.getTotal();
	}

	protected String getRandomWord() {
		return wordList.getRandomWord();
	}

	protected int getLength() {
		return length;
	}

	protected Blanks getBlanks() {
		return blanks;
	}

	protected boolean isBlank(int idx) {
		return blanks.isBlank(idx);
	}
}
