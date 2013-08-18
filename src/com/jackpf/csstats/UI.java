package com.jackpf.csstats;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

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
		if (Integer.parseInt(stats.get("visibilityState")) != SteamStats.VIEWABLE) {
			Lib.error(
				MainActivity.getInstance(),
				MainActivity.getInstance().getString(R.string.error_not_viewable)
			);
		}
		
		
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