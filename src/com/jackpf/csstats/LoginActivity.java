package com.jackpf.csstats;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jackpf.csstats.Steam.transform.SteamIdTransformer;
import com.jackpf.csstats.Steam.transform.TransformException;
import com.jackpf.csstats.lib.Lib;

public class LoginActivity extends Activity
{
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
    	
        setContentView(R.layout.activity_login);
    }
	
	public void login(View view)
	{
		String input = ((EditText) findViewById(R.id.steamId_login)).getText().toString();
		
		try {
			SteamIdTransformer transformer = new SteamIdTransformer(input);
			
			System.err.println(transformer.getType() + "/" + transformer.getId());
		} catch(TransformException e) {
			Lib.warning(this, "Invalid input");
		}
	}
}
