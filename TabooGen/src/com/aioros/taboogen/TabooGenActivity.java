package com.aioros.taboogen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class TabooGenActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void createCard(View b) {
    	CardCreatorTask creator = new CardCreatorTask(this);
    	creator.execute();
    }
}