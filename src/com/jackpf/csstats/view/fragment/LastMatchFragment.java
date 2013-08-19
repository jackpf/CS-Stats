package com.jackpf.csstats.view.fragment;

import android.app.Activity;
import android.graphics.Color;
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
		
		TableLayout fragment = (TableLayout) context.findViewById(R.id.fragment_last_match);
        
        String[] stats	= {"t_wins", "ct_wins", "wins",
			        	   "favwpn", "shots", "hits", "dmg",
			        	   "kills", "deaths", "kdratio", "ksratio",
						   "acc", "stars", "max_players",
						   "money", "costkill",
						   "dominations", "revenges"},
        		 keys	= {"T wins", "CT wins", "Your wins",
			        	   "Fav. weapon", "Shots", "Hits", "Damage inflicted",
			        	   "Kills", "Deaths", "K/D Ratio", "K/S Ratio",
						   "Accuracy", "Stars earned", "Players",
						   "Money spent", "Cost per kill",
						   "Dominations", "Revenges"},
        		 types	= {"int", "int", "int",
			        	   "string", "int", "int", "int",
			        	   "int", "int", "float", "float",
						   "int", "int", "int",
						   "money", "money",
						   "int", "int"};
        
        for (int i = 0; i < stats.length; i++) {
        	String stat = stats[i], key = keys[i], type = types[i];
        	
        	String value = SummaryFragment.parseValue(ui.get("stats").get("stats.lastmatch." + stat), type);
        	
        	TableRow tr = (TableRow) inflator.inflate(R.layout._table_row_stat, null);
        	
        	((TextView) tr.findViewById(R.id.key)).setText(key);
        	((TextView) tr.findViewById(R.id.value)).setText(value);
        	
        	if (i % 2 == 1)
        		tr.setBackgroundColor(Color.argb(150, 128, 128, 128));
        	else
        		tr.setBackgroundColor(Color.argb(50, 128, 128, 128));
        	
        	fragment.addView(tr);
        }
	}
}