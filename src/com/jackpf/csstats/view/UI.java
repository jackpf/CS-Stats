package com.jackpf.csstats.view;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jackpf.csstats.MainActivity;
import com.jackpf.csstats.R;
import com.jackpf.csstats.Steam.Data;
import com.jackpf.csstats.Steam.SteamStats;
import com.jackpf.csstats.lib.Lib;

public class UI
{
	/**
	 * Update UI
	 * TODO: This may need splitting up if/when it gets bigger
	 * TODO: ScrollView
	 * 
	 * @param stats
	 */
	public void update(SteamStats profile,
		SteamStats stats,
		SteamStats screenshots)
	{
		final Activity context = MainActivity.getInstance();
		final LayoutInflater inflator = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
		
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
		
		// Setup tabs
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
    		tabHost.newTabSpec("Maps")
	            .setIndicator("Maps")
	            .setContent(R.id.fragment_maps)
        );
        final SteamStats screenshotsTab = screenshots;
        tabHost.addTab(
    		tabHost.newTabSpec("Screenshots")
	            .setIndicator("Screenshots")
	            .setContent(new TabHost.TabContentFactory() {
	                public View createTabContent(String tag) {
	                    LinearLayout tabContent = (LinearLayout) context.findViewById(R.id.fragment_screenshots);
	                    
	                    ImageView iv = new ImageView(context);
	                    
	                    new ImageLoader(iv).execute(screenshotsTab.get("0"));
	                    
	                    tabContent.addView(iv);
	                    
	                    return tabContent;
	                }
	            })
        );
 
        tabHost.setCurrentTab(0);
        
        // Summary tab
        TableLayout fragmentSummary = (TableLayout) context.findViewById(R.id.fragment_summary);
        
        String[] summaryStats = {"rounds", "wins", "winpct"};
        for (int i = 0; i < summaryStats.length; i++) {
        	String stat = summaryStats[i];
        	
        	TableRow tr = (TableRow) inflator.inflate(R.layout._table_row_stat, null);
        	
        	((TextView) tr.findViewById(R.id.key)).setText(stat);
        	((TextView) tr.findViewById(R.id.value)).setText(stats.get("stats.summary." + stat));
        	
        	if (i % 2 == 1)
        		tr.setBackgroundColor(Color.argb(150, 128, 128, 128));
        	else
        		tr.setBackgroundColor(Color.argb(50, 128, 128, 128));
        	
        	fragmentSummary.addView(tr);
        }
        
        // Maps tab
        HashMap<String, Integer> mapData = new HashMap<String, Integer>();
        
        for (String map : Data.MAPS) {
        	mapData.put(map, Integer.valueOf(stats.get("stats.maps." + map + "_rounds")));
        }
        
        RelativeLayout chartContainer = (RelativeLayout) context.findViewById(R.id.fragment_maps_chart);
        GraphicalView chartView = UIGraph.getNewInstance(context, mapData);
        //chartContainer.addView(chartView);
        
        TableLayout mapsTable = (TableLayout) context.findViewById(R.id.fragment_maps_table);
        
        for (int i = 0; i < Data.MAPS.length; i++) {
        	String map = Data.MAPS[i];
        	
        	TableRow tr = (TableRow) inflator.inflate(R.layout._table_row_stat, null);
        	
        	((TextView) tr.findViewById(R.id.map)).setText(map);
        	((TextView) tr.findViewById(R.id.rounds)).setText(stats.get("stats.maps." + map + "_rounds"));
        	((TextView) tr.findViewById(R.id.wins)).setText(stats.get("stats.maps." + map + "_wins"));
        	
        	// If 0 rounds played, don't display 100%
        	if (Integer.parseInt(stats.get("stats.maps." + map + "_rounds")) == 0)
        		((TextView) tr.findViewById(R.id.winpct)).setText("~");
        	else
        		((TextView) tr.findViewById(R.id.winpct)).setText(Math.round(Float.parseFloat(stats.get("stats.maps." + map + "_winpct"))) + "%");
        	
        	if (i % 2 == 1)
        		tr.setBackgroundColor(Color.argb(150, 128, 128, 128));
        	else
        		tr.setBackgroundColor(Color.argb(50, 128, 128, 128));
        	
        	mapsTable.addView(tr);
        }
        
        sortTable(mapsTable, 2);
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