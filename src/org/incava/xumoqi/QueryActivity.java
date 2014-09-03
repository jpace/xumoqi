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

package org.incava.xumoqi;

import org.incava.xumoqi.games.Game;
import org.incava.xumoqi.games.GameParameters;
import org.incava.xumoqi.gui.Enterable;
import org.incava.xumoqi.gui.EnterableEditText;
import org.incava.xumoqi.query.Query;
import org.incava.xumoqi.util.Lo;
import org.incava.xumoqi.util.Timer;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class QueryActivity extends Activity implements Enterable {
    private Timer timer = null;
    private Game gameIterations = null;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        
        Intent intent = getIntent();
        gameIterations = GameParameters.getGameIterations(intent);

        Resources resources = getResources();
        Query query = gameIterations.getNextQuery(resources);

        EnterableEditText.setupEditText(this, this, getInputTextView());
        setQueryText(query);
        
        timer = new Timer(this, "onCreate");
        startTime = System.currentTimeMillis();
    }

    /*
    protected void onStart() {
        timer.done("onStart");
        Lo.time("onStart:currTime");
        super.onStart();
    }
    */
    
    public void onEnter() {
        onClickNext(null);
    }

    public void onClickNext(View view) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        Lo.g("startTime", startTime);
        Lo.g("endTime  ", endTime);
        Lo.g("duration", duration);
        
        timer.done("onClickNext");

        Intent intent = new Intent(this, StatusActivity.class);

        GameParameters.saveDuration(intent, duration);

        EditText et = getInputTextView();
        String inputText = et.getText().toString();

        GameParameters.saveInputText(intent, inputText);
        GameParameters.saveGameIterations(intent, gameIterations);
        
        startActivity(intent);
    }
    
    public void onClickRestart(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        GameParameters.saveGameIterations(intent, gameIterations);
        startActivity(intent);
    }

    private TextView getQueryTextView() {
        return (TextView)findViewById(R.id.queryText);
    }
    
    private EditText getInputTextView() {
        return (EditText)findViewById(R.id.queryInput);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.query, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setQueryText(Query query) {
        String queryStr = query.getQueryString();
        TextView tv = getQueryTextView();
        tv.setText(queryStr);
    }
}
