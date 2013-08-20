package com.jackpf.csstats.view.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jackpf.csstats.MainActivity;
import com.jackpf.csstats.R;
import com.jackpf.csstats.view.UI;
import com.jackpf.csstats.view.model.Fragment;

public class LastMatchFragment implements Fragment
{
	public TabSpec getSpec(TabHost tabHost)
	{
		return tabHost.newTabSpec("Last Match")
            .setIndicator("Last Match")
            .setContent(R.id.fragment_last_match);
	}
	
	public void setup(UI ui, Activity context)
	{
		final LayoutInflater inflator = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);

		View fragment = inflator.inflate(R.layout._fragment_last_match, null);
		
		TableLayout table = (TableLayout) fragment.findViewById(R.id.fragment_last_match_table);
        
        String[] stats	= {"t_wins", "ct_wins", "wins",
			        	   "shots", "hits", "acc",
			        	   "kills", "deaths", "kdratio",
						   "stars", "revenges", "dominations",
						   "dmg", "money", "costkill"},
        		 types	= {"int", "int", "int",
			        	   "int", "int", "pct",
			        	   "int", "int", "float",
						   "int", "int", "int",
						   "int", "money", "money"};
        
        for (int i = 0, k = 0; i < stats.length; k++) {
        	TableRow tr = (TableRow) inflator.inflate(R.layout._table_row_stat_triplet, null);
        	
        	for (int j = 1; i < stats.length && j <= 3; j++, i++) {
	        	String stat = stats[i], type = types[i];
	        	String key = SummaryFragment.getKey(stat, context);
	        	
	        	String value = SummaryFragment.parseValue(ui.get("stats").get("stats.lastmatch." + stat), type);
	        	//TODO: Put in values
	        	String html = "<small>" + key + "</small><br /><strong>" + value + "</strong>";

	        	int tv = context.getResources().getIdentifier("col" + j , "id", context.getPackageName());
	        	
	        	((TextView) tr.findViewById(tv)).setText(Html.fromHtml(html));
        	}
        	
        	if (i % 2 == 1)
        		tr.setBackgroundColor(Color.argb(50, 128, 128, 128));
        	else
        		tr.setBackgroundColor(Color.argb(50, 129, 179, 215));
        	
        	table.addView(tr);
        }
        
		((LinearLayout) context.findViewById(R.id.fragment_last_match)).addView(fragment);
	}
}