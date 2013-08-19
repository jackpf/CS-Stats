package com.jackpf.csstats;

import android.os.AsyncTask;

import com.jackpf.csstats.Steam.SteamStats;
import com.jackpf.csstats.Steam.SteamUser;
import com.jackpf.csstats.Steam.parser.ScreenshotsParser;
import com.jackpf.csstats.view.UI;

public class NetworkThread extends AsyncTask<String, Void, Void>
{
	SteamStats profile, stats, screenshots;
	
	Exception e = null;
	
	@Override
    protected Void doInBackground(String... params)
    {
    	SteamUser user = new SteamUser("seanyshaunshawn");
    	
        try {
        	profile = user.getProfile();
        	stats = user.getStats();
        	screenshots = new SteamStats(user, SteamStats.SCREENSHOTS_URL)
	            .request()
	            .parse(new ScreenshotsParser());
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
				stats,
				screenshots);
		} else {
			ui.error(e);
		}
	}
}