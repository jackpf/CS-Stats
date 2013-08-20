package com.jackpf.csstats;

import android.os.AsyncTask;

import com.jackpf.csstats.Steam.SteamStats;
import com.jackpf.csstats.Steam.SteamUser;
import com.jackpf.csstats.Steam.parser.ScreenshotsParser;
import com.jackpf.csstats.view.UI;

public class NetworkThread extends AsyncTask<String, Void, Void>
{
	SteamStats profile, stats;
	
	Exception e = null;
	
	@Override
    protected Void doInBackground(String... params)
    {
    	SteamUser user = new SteamUser(params[0]);
    	
        try {
        	profile = user.getProfile();
        	stats = user.getStats();
        } catch(Exception e) {
            this.e = e;
        }

        return null;
    }
    
	@Override
    protected void onPostExecute(Void _void)
	{
		UI ui = new UI();
		
		if (e == null) {
			ui.update(profile,
				stats);
		} else {
			ui.error(e);
		}
	}
}