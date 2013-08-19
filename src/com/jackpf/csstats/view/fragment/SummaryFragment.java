package com.jackpf.csstats.view.fragment;

import java.text.DecimalFormat;

import android.app.Activity;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jackpf.csstats.MainActivity;
import com.jackpf.csstats.R;
import com.jackpf.csstats.view.UI;
import com.jackpf.csstats.view.model.Fragment;

public class SummaryFragment implements Fragment
{
	public TabSpec getSpec(TabHost tabHost)
	{
		return tabHost.newTabSpec("Summary")
            .setIndicator("Summary")
            .setContent(R.id.fragment_summary);
	}
	
	public void setup(UI ui, Activity context)
	{
		final LayoutInflater inflator = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
		
		TableLayout fragment = (TableLayout) context.findViewById(R.id.fragment_summary);
        
        String[] stats	= {"kills", "deaths", "kdratio",
        						   "rounds", "wins", "winpct", /*"stars",*/
        						   "shots", "shotshit", "shotpct"},
        		 keys	= {"Kills", "Deaths", "K/D",
        						   "Rounds", "Wins", "Win %", /*"Stars",*/
        						   "Shots", "Shots hit", "Hit %"},
        		 types	= {"int", "int", "float",
        						   "int", "int", "pct", /*"int",*/
        						   "int", "int", "pct"};
        
        for (int i = 0, k = 0; i < stats.length; k++) {
        	TableRow tr = (TableRow) inflator.inflate(R.layout._table_row_stat_triplet, null);
        	
        	for (int j = 1; i < stats.length && j <= 3; j++, i++) {
	        	String stat = stats[i], key = keys[i], type = types[i];
	        	
	        	String value = SummaryFragment.parseValue(ui.get("stats").get("stats.summary." + stat), type);
	        	//TODO: Put in values
	        	String html = "<small>" + key + "</small><br /><strong>" + value + "</strong>";
	        	
	        	int tv = context.getResources().getIdentifier("col" + j , "id", context.getPackageName());
	        	
	        	((TextView) tr.findViewById(tv)).setText(Html.fromHtml(html));
        	}
        	
        	if (k % 2 == 1)
        		tr.setBackgroundColor(Color.argb(150, 128, 128, 128));
        	else
        		tr.setBackgroundColor(Color.argb(50, 128, 128, 128));
        	
        	fragment.addView(tr);
        }
	}
	
	/**
	 * A bit naughty but this used elsewhere to format table types
	 * TODO: Could change Fragment to abstract and implement it there?
	 * 
	 * @param value
	 * @param type
	 * @return string
	 */
	public static String parseValue(String value, String type)
	{
		if (value == null) {
			return null;
		}
		
		// Do some rounding for ints to make sure they're ints
		// Format floats to 2dp
    	if (type.equals("int") || type.equals("pct") || type.equals("money")) {
    		value = Integer.toString(Math.round(Float.parseFloat(value)));
    		value = new DecimalFormat("#,###").format(Double.parseDouble(value));
    		
    		if (type.equals("pct")) {
    			value += "%";
    		} else if(type.equals("money")) {
    			value = "$" + value;
    		}
    	} else if (type.equals("float")) {
    		value = String.format("%.2f", Float.parseFloat(value));
    	}
    	
    	return value;
	}
}