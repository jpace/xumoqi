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

import android.content.Intent;

public class GameParameters {
    private final static String DURATION = "Duration";
    private final static String GAME_ITERATIONS = "GameIterations";
    private final static String INPUT_TEXT = "InputText";

    public static GameIterations getGameIterations(Intent intent) {
        return intent.getParcelableExtra(GAME_ITERATIONS);
    }

    public static void saveGameIterations(Intent intent, GameIterations gameIterations) {
        intent.putExtra(GAME_ITERATIONS, gameIterations);
    }

    public static long getDuration(Intent intent) {
        String durStr = intent.getStringExtra(DURATION);
        return Long.valueOf(durStr);
    }
    
    public static void saveDuration(Intent intent, long duration) {
        String durStr = String.valueOf(duration);
        intent.putExtra(DURATION, durStr);
    }

    public static String getInputText(Intent intent) {
        return intent.getStringExtra(INPUT_TEXT);
    }
    
    public static void saveInputText(Intent intent, String inputText) {
        intent.putExtra(INPUT_TEXT, inputText);
    }
}
