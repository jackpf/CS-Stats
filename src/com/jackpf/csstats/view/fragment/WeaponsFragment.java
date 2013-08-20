package com.jackpf.csstats.view.fragment;

import java.util.HashMap;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jackpf.csstats.MainActivity;
import com.jackpf.csstats.R;
import com.jackpf.csstats.Steam.Data;
import com.jackpf.csstats.view.UI;
import com.jackpf.csstats.view.graph.MapGraph;
import com.jackpf.csstats.view.model.Fragment;

public class WeaponsFragment implements Fragment
{
	public TabSpec getSpec(TabHost tabHost)
	{
		return tabHost.newTabSpec("Weapons")
            .setIndicator("Weapons")
            .setContent(R.id.fragment_weapons);
	}
	
	public void setup(UI ui, Activity context)
	{
		final LayoutInflater inflator = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);

		View fragment = inflator.inflate(R.layout._fragment_weapons, null);
		
        LinearLayout table = (LinearLayout) fragment.findViewById(R.id.fragment_weapons_table);
        
        String format = context.getString(R.string.stat_format);
        
        for (int i = 0; i < Data.WEAPONS.length; i++) {
        	String weapon = Data.WEAPONS[i];
        	
        	LinearLayout tr = (LinearLayout) inflator.inflate(R.layout._table_row_weapon, null);
        	
        	((TextView) tr.findViewById(R.id.key)).setText(SummaryFragment.getKey(weapon, context));
        	((TextView) tr.findViewById(R.id.col1)).setText(Html.fromHtml(
    			String.format(format,
	        		context.getString(R.string.kills),
	        		SummaryFragment.parseValue(ui.get("stats").get("stats.weapons." + weapon + "_kills"), "int"))
        	));
        	((TextView) tr.findViewById(R.id.col2)).setText(Html.fromHtml(
    			String.format(format,
	        		context.getString(R.string.shots),
	        		SummaryFragment.parseValue(ui.get("stats").get("stats.weapons." + weapon + "_shots"), "int"))
        	));
        	((TextView) tr.findViewById(R.id.col3)).setText(Html.fromHtml(
        		String.format(format,
	        		context.getString(R.string.hits),
	        		SummaryFragment.parseValue(ui.get("stats").get("stats.weapons." + weapon + "_hits"), "int"))
        	));
        	((TextView) tr.findViewById(R.id.col4)).setText(Html.fromHtml(
        		String.format(format,
	        		context.getString(R.string.acc),
	        		SummaryFragment.parseValue(ui.get("stats").get("stats.weapons." + weapon + "_acc"), "pct"))
        	));
        	
        	if (i % 2 == 1)
        		tr.setBackgroundColor(Color.argb(50, 128, 128, 128));
        	else
        		tr.setBackgroundColor(Color.argb(50, 129, 179, 215));
        	
        	table.addView(tr);
        }
		
		((LinearLayout) context.findViewById(R.id.fragment_weapons)).addView(fragment);
	}
}