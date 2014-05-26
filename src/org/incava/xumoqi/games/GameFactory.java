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

public class GameFactory {
    public static Game createGame(String gameType, int length, int nDots) {
        if (gameType.contains("Starting")) {
            return new GameStartsWithDots(getWordList(length), length, nDots);
        }
        else if (gameType.contains("Random")) {
            return new GameRandomDots(getWordList(length), length, nDots);
        }
        else if (gameType.contains("Ending")) {
            return new GameEndsWithDots(getWordList(length), length, nDots);
        }
        else if (gameType.contains("to-make")) {
            return new GameNToNPlusOne(getWordList(length), getWordList(length + 1));
        }
        else if (gameType.equals("Q without U")) {
            return new GameQNoU(null, length, nDots);
        }
        return null;
    }

    public static WordList getWordList(int length) {
        return WordLists.getInstance().getWordList(length);
    }
}
