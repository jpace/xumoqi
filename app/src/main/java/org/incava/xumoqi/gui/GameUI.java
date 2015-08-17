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

  This program includes code from the GPL program:
  http://sourceforge.net/projects/scrabbledict/
*/

package org.incava.xumoqi.gui;

import org.incava.xumoqi.MainActivity;
import org.incava.xumoqi.android.NumberPickerUtil;
import org.incava.xumoqi.game.Game;
import org.incava.xumoqi.game.GameTypeOptions;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

public class GameUI {
    private final Game game;
    private final GameTypeOptions options;
    
    public GameUI(Game game) {
        this.game = game;
        this.options = new GameTypeOptions();
    }
    
    public void setUp(MainActivity mainActivity) {
        NumberPicker np = mainActivity.getNumberPicker();
        setUpWordLengthPicker(np);
        Spinner gameTypeSpinner = mainActivity.getGameTypeSpinner();
        setUpGameTypeSpinner(gameTypeSpinner, mainActivity);
    }

    private void setUpWordLengthPicker(NumberPicker numberPicker) {
        int min = 2;
        int max = options.getMaxWordLength();
        int current = game == null ? options.getDefaultLength() : game.getLength();
        NumberPickerUtil.setValues(numberPicker, min, max, current);
    }
    
    private void setUpGameTypeSpinner(Spinner gameTypeSpinner, Activity activity) {
        int types = options.getGameTypes();
        int gameTypeIndex = options.getDefaultGameTypeIndex();
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameTypeSpinner.setAdapter(adapter);
        gameTypeSpinner.setSelection(gameTypeIndex);
    }
}
