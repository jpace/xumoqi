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

import org.incava.xumoqi.words.WordList;
import org.incava.xumoqi.words.WordLists;

import android.content.res.Resources;

public class GameFactory {
    public static Game createGame(Resources resources, String gameType, int length, int nDots) {
        if (gameType.contains("Starting")) {
            return new GameStartsWithDots(getWordList(resources, length), length, nDots);
        }
        else if (gameType.contains("Random")) {
            return new GameRandomDots(getWordList(resources, length), length, nDots);
        }
        else if (gameType.contains("Ending")) {
            return new GameEndsWithDots(getWordList(resources, length), length, nDots);
        }
        else if (gameType.contains("to-make")) {
            return new GameNToNPlusOne(getWordList(resources, length), getWordList(resources, length + 1));
        }
        else if (gameType.equals("Q without U")) {
        	// this doesn't use a word list (yet)
            return new GameQNoU(null, length, nDots);
        }
        return null;
    }

    private static WordList getWordList(Resources resources, int length) {
        return WordLists.getInstance().getWordList(resources, length);
    }
}
