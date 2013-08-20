package com.jackpf.csstats;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jackpf.csstats.Steam.transform.SteamIdTransformer;
import com.jackpf.csstats.Steam.transform.TransformException;
import com.jackpf.csstats.lib.Lib;

public class LoginActivity extends Activity
{
	/**
	 * Key used to access saved credentials in prefs
	 */
	public final static String KEY_TYPE	= "type",
							   KEY_ID	= "id";
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
    	
        setContentView(R.layout.activity_login);
        
        // Display any login errors sent from MainActivity
        String error = getIntent().getStringExtra("error");
        
        if (error != null) {
        	Lib.error(this, error);
        }
    }
	
	public void login(View view)
	{
		String input = ((EditText) findViewById(R.id.steamId_login)).getText().toString();
		
		try {
			SteamIdTransformer transformer = new SteamIdTransformer(input);
			
			SharedPreferences.Editor prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE).edit();

			prefs.putString(LoginActivity.KEY_TYPE, transformer.getType());
			prefs.putString(LoginActivity.KEY_ID, transformer.getId());
			
			prefs.commit();
			
			Intent loginActivity = new Intent(this, MainActivity.class);
        	startActivity(loginActivity);
		} catch(TransformException e) {
			Lib.error(this, "Invalid input");
		}
	}
}
