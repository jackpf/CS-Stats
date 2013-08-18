package com.jackpf.csstats;

import com.jackpf.csstats.Steam.SteamStats;
import com.jackpf.csstats.Steam.SteamUser;

import android.os.AsyncTask;

public class NetworkThread extends AsyncTask<String, Void, Void>
{
    protected Void doInBackground(String... params)
    {
        SteamStats stats = new SteamStats(new SteamUser("jcak"), SteamStats.CSSTATS_URL);
        try {
            stats.getStats();
            
            UI ui = new UI();
            ui.update(stats);
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}