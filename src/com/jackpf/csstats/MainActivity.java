package com.jackpf.csstats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity
{
	private static MainActivity instance;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
    	instance = this;
    	
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);

        if (true) {
        	startActivity(new Intent(this, LoginActivity.class));
        } else {
        	new NetworkThread().execute("jcak");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }
    
    public static MainActivity getInstance()
    {
    	return instance;
    }
}
