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
		
		TableLayout fragment = (TableLayout) context.findViewById(R.id.fragment_lifetime);
        
        String[] stats	= {"bombsplanted", "bombsdefused", "hostagesrescued", "pistolrounds",
						   "dmg", "dominations", "dominationoverkills", "revenges",
						   "headshots", "zsniperkills", "blindkills", "enemywpnkills", "knifekills",
						   "money", "decals", "nvgdmg", "winbroken", "wpndonated"},
        		 keys	= {"Bombs planted", "Bombs defused", "Hostages rescued", "Pistol rounds",
						   "Damage inflicted", "Dominations", "Overkills", "Revenges",
						   "Headshots", "Sniper kills", "Blind kills", "Kills w/ enemy weapon", "Knife kills",
						   "Money spent", "Sprays", "Nightvision damage", "Windows broken", "Weapons donated"},
        		 types	= {"int", "int", "int", "int",
						   "int", "int", "int", "int",
						   "int", "int", "int", "int", "int",
						   "money", "int", "int", "int", "int"};
        
        for (int i = 0; i < stats.length; i++) {
        	String stat = stats[i], key = keys[i], type = types[i];
        	
        	String value = SummaryFragment.parseValue(ui.get("stats").get("stats.lifetime." + stat), type);
        	
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