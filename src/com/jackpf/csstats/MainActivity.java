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
        	Intent loginActivity = new Intent(this, LoginActivity.class);
        	loginActivity.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        	startActivity(loginActivity);
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
