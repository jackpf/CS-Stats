package com.jackpf.csstats;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jackpf.csstats.Steam.Data;
import com.jackpf.csstats.Steam.SteamStats;

public class UI
{
	/**
	 * Update UI
	 * TODO: This may need splitting up if/when it gets bigger
	 * 
	 * @param stats
	 */
	public void update(SteamStats profile, SteamStats stats)
	{
		Activity context = MainActivity.getInstance();
		
		if (Integer.parseInt(stats.get("visibilityState")) != SteamStats.VIEWABLE) {
			Lib.error(
				context,
				context.getString(R.string.error_not_viewable)
			);
		}
		
		// Set avatar
		new ImageLoader(
			(ImageView) context.findViewById(R.id.avatar)
		).execute(
			profile.get("avatarFull")
		);
		
		// Set username
		((TextView) context.findViewById(R.id.steamId)).setText(profile.get("steamID"));
		
		TabHost tabHost = (TabHost) context.findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.setVisibility(LinearLayout.VISIBLE);

        tabHost.addTab(
    		tabHost.newTabSpec("Summary")
	            .setIndicator("Summary")
	            .setContent(R.id.fragment_summary)
        );
        tabHost.addTab(
    		tabHost.newTabSpec("Last Game")
	            .setIndicator("Last Game")
	            .setContent(R.id.fragment_last_game)
        );
        tabHost.addTab(
    		tabHost.newTabSpec("Lifetime")
	            .setIndicator("Overview")
	            .setContent(R.id.fragment_lifetime)
        );
 
        tabHost.setCurrentTab(0);
        
        TableLayout fragmentSummary = (TableLayout) context.findViewById(R.id.fragment_summary);
        
        String[] summaryStats = {"rounds", "wins", "winpct"};
        for (int i = 0; i < summaryStats.length; i++) {
        	String stat = summaryStats[i];
        	
        	TextView tvKey = new TextView(context);
        	tvKey.setText(stat);
        	
        	TextView tvValue = new TextView(context);
        	tvValue.setText(stats.get("stats.summary." + stat));
        	
        	TableRow tr = new TableRow(context);
        	tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        	if (i % 2 == 1)
        		tr.setBackgroundColor(Color.argb(150, 128, 128, 128));
        	else
        		tr.setBackgroundColor(Color.argb(50, 128, 128, 128));
        	tr.addView(tvKey);
        	tr.addView(tvValue);
        	
        	fragmentSummary.addView(tr);
        }
        
        HashMap<String, Integer> mapData = new HashMap<String, Integer>();
        
        for (String map : Data.MAPS) {
        	mapData.put(map, Integer.valueOf(stats.get("stats.maps." + map + "_rounds")));
        }
        
        RelativeLayout chartContainer = (RelativeLayout) context.findViewById(R.id.fragment_lifetime);
        GraphicalView chartView = UIGraph.getNewInstance(context, mapData);
        chartContainer.addView(chartView);
	}
	
	public void error(Exception e)
	{
		Lib.error(MainActivity.getInstance(), e.getMessage());
	}
	
	/**
	 * Load images from a url
	 * Copied from halo4servicerecord
	 * TODO: Caching?
	 */
	private static class ImageLoader extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public ImageLoader(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}
	
	
}