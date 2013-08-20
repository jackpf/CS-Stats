package com.jackpf.csstats;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
	}
}
