package com.jackpf.csstats.view;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jackpf.csstats.MainActivity;
import com.jackpf.csstats.R;
import com.jackpf.csstats.Steam.SteamStats;
import com.jackpf.csstats.lib.Lib;
import com.jackpf.csstats.view.fragment.LastMatchFragment;
import com.jackpf.csstats.view.fragment.LifetimeFragment;
import com.jackpf.csstats.view.fragment.MapsFragment;
import com.jackpf.csstats.view.fragment.SummaryFragment;
import com.jackpf.csstats.view.fragment.WeaponsFragment;
import com.jackpf.csstats.view.model.Fragment;

public class UI
{
	private HashMap<String, SteamStats> stats = new HashMap<String, SteamStats>();

	/**
	 * Some methods for fragments
	 */
	public SteamStats get(String key)
	{
		return stats.get(key);
	}
	
	/**
	 * Update UI
	 * 
	 * @param stats
	 */
	public void update(SteamStats profile,
		SteamStats stats,
		SteamStats screenshots)
	{
		// Store stats for fragments
		this.stats.put("profile", profile);
		this.stats.put("stats", stats);
		this.stats.put("screenshots", screenshots);
		
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
		// Parse html since it's given as special chars
		((TextView) context.findViewById(R.id.steamId)).setText(
			Html.fromHtml(profile.get("steamID"))
		);
		
		// Setup tabs
		TabHost tabHost = (TabHost) context.findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.setVisibility(LinearLayout.VISIBLE);
		
		Fragment[] fragments = {
			new SummaryFragment(),
			new LastMatchFragment(),
			new LifetimeFragment(),
			new MapsFragment(),
		};
		
		for (Fragment fragment : fragments) {
	        tabHost.addTab(fragment.getSpec(tabHost));
	        
	        fragment.setup(this, context);
		}
 
        tabHost.setCurrentTab(0);
	}
	
	/**
	 * Render errors thrown by backend
	 * 
	 * @param e
	 */
	public void error(Exception e)
	{
		e.printStackTrace();
		
		Lib.error(MainActivity.getInstance(), e.getMessage());
	}
	
	/**
	 * Sort a table view by a given column
	 * TODO: Currently not working
	 * 
	 * @param table
	 * @param sortColumn
	 */
	private void sortTable(TableLayout table, int sortColumn)
	{
		Map<Integer, TableRow> rows = new TreeMap<Integer, TableRow>();
		
		for(int i = 0; i < table.getChildCount(); i++) {
		    TableRow row = (TableRow) table.getChildAt(i);
		    
		    try {
		    	rows.put(Integer.parseInt(((TextView) row.getChildAt(sortColumn)).getText().toString()), row);
		    } catch(NumberFormatException e) {
		    }
		}
		
		for (TableRow row : rows.values()) {
		    table.removeView(row);
		}
	
		for (TableRow row : rows.values()) { 
		    table.addView(row);
		}
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