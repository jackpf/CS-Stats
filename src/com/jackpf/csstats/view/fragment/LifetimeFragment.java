package com.jackpf.csstats.view.fragment;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jackpf.csstats.MainActivity;
import com.jackpf.csstats.R;
import com.jackpf.csstats.view.UI;
import com.jackpf.csstats.view.model.Fragment;

public class LifetimeFragment implements Fragment
{
	public TabSpec getSpec(TabHost tabHost)
	{
		return tabHost.newTabSpec("Lifetime")
            .setIndicator("Lifetime")
            .setContent(R.id.fragment_lifetime);
	}
	
	public void setup(UI ui, Activity context)
	{
		final LayoutInflater inflator = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
		
		View fragment = inflator.inflate(R.layout._fragment_lifetime, null);
		
		TableLayout table = (TableLayout) fragment.findViewById(R.id.fragment_lifetime_table);
        
        String[] stats	= {"bombsplanted", "bombsdefused", "hostagesrescued", "pistolrounds",
						   "dmg", "dominations", "dominationoverkills", "revenges",
						   "headshots", "zsniperkills", "blindkills", "enemywpnkills", "knifekills",
						   "money", "decals", "nvgdmg", "winbroken", "wpndonated"},
        		 types	= {"int", "int", "int", "int",
						   "int", "int", "int", "int",
						   "int", "int", "int", "int", "int",
						   "money", "int", "int", "int", "int"};
        
        for (int i = 0; i < stats.length; i++) {
        	String stat = stats[i], type = types[i];
        	String key = SummaryFragment.getKey(stat, context);
        	
        	String value = SummaryFragment.parseValue(ui.get("stats").get("stats.lifetime." + stat), type);
        	
        	TableRow tr = (TableRow) inflator.inflate(R.layout._table_row_stat, null);
        	
        	((TextView) tr.findViewById(R.id.key)).setText(key);
        	((TextView) tr.findViewById(R.id.value)).setText(value);
        	
        	if (i % 2 == 1)
        		tr.setBackgroundColor(Color.argb(150, 128, 128, 128));
        	else
        		tr.setBackgroundColor(Color.argb(50, 128, 128, 128));
        	
        	table.addView(tr);
        }
        
		((LinearLayout) context.findViewById(R.id.fragment_lifetime)).addView(fragment);
	}
}