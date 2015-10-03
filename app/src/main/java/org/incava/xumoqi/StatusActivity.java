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

package org.incava.xumoqi;

import org.incava.xumoqi.game.Game;
import org.incava.xumoqi.game.GameParameters;
import org.incava.xumoqi.gui.ActivityUtil;
import org.incava.xumoqi.gui.StatusView;
import org.incava.xumoqi.query.Query;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class StatusActivity extends Activity {
    private Game game = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        game = GameParameters.getGame(intent);

        Query query = game.getCurrentQuery();
        String inputText = GameParameters.getInputText(intent);
        long duration = GameParameters.getDuration(intent);
        
        StatusView.setUp(this, query, inputText, duration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return ActivityUtil.createOptionsMenu(this, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ActivityUtil.processOptionSelected(this, item);
        return true;
    }

    public void onClickRestart(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        nextActivity(intent);
    }

    public void onClickNext(View view) {
        Intent intent = new Intent(this, QueryActivity.class);
        nextActivity(intent);
    }

    private void nextActivity(Intent intent) {
        GameParameters.saveGame(intent, game);
        startActivity(intent);
    }
}
