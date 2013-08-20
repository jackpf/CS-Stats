package com.jackpf.csstats;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jackpf.csstats.Steam.SteamUser;

public class MainActivity extends Activity
{
	private static MainActivity instance;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
    	instance = this;
    	
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main_loading);

        // Check credentials
        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        
        String id = prefs.getString(LoginActivity.KEY_ID, null),
        	   type = prefs.getString(LoginActivity.KEY_TYPE, null);
        
        if (type == null || id == null) {
        	Intent loginActivity = new Intent(this, LoginActivity.class);
        	loginActivity.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        	
        	startActivity(loginActivity);
        } else {
        	SteamUser user = new SteamUser(id, type);
        	
        	new NetworkThread().execute(user);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        	case R.id.menu_login:
        		startActivity(new Intent(this, LoginActivity.class));
        	break;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    public static MainActivity getInstance()
    {
    	return instance;
    }
}
