package org.incava.xumoqi;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

public class MainActivity extends Activity {
	public static final String WORD_LENGTH = "wordLength";
	public static final String GAME_TYPE = "gameType";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        NumberPicker np = getNumberPicker();
        np.setMaxValue(8);
        np.setMinValue(2);
        
        Spinner gameTypeSpinner = getGameTypeSpinner();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.game_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameTypeSpinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private NumberPicker getNumberPicker() {
    	return (NumberPicker)findViewById(R.id.wordLengthNumberPicker);
    }
    
    private Spinner getGameTypeSpinner() {
    	return (Spinner)findViewById(R.id.gameTypeSpinner);
    }
    
    public void onClickNext(View view) {
    	Intent intent = new Intent(this, QueryActivity.class);
    	NumberPicker np = getNumberPicker();
        intent.putExtra(WORD_LENGTH, np.getValue());
        
        Spinner gameTypeSpinner = getGameTypeSpinner();
        String gameType = gameTypeSpinner.getSelectedItem().toString();
        intent.putExtra(GAME_TYPE, gameTypeSpinner.getSelectedItem().toString());
        
    	startActivity(intent);
    }
}
