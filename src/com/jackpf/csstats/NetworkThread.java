package com.jackpf.csstats;

import com.jackpf.csstats.Steam.SteamStats;
import com.jackpf.csstats.Steam.SteamUser;

import android.os.AsyncTask;

public class NetworkThread extends AsyncTask<String, Void, Void>
{
    protected Void doInBackground(String... params)
    {
    	SteamUser user = new SteamUser("jcak");
    	
        UI ui = new UI();
        
        try {
        	SteamStats profile = user.getProfile(), stats = user.getStats();
        	
            ui.update(profile, stats);
        } catch(Exception e) {
            System.err.println(e.getMessage());
            
            ui.error(e);
        }

        return null;
    }
}