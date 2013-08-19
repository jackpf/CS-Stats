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
		
		TableLayout fragmentSummary = (TableLayout) context.findViewById(R.id.fragment_summary);
        
        String[] summaryStats = {"rounds", "wins", "winpct"};
        for (int i = 0; i < summaryStats.length; i++) {
        	String stat = summaryStats[i];
        	
        	TableRow tr = (TableRow) inflator.inflate(R.layout._table_row_stat, null);
        	
        	((TextView) tr.findViewById(R.id.key)).setText(stat);
        	((TextView) tr.findViewById(R.id.value)).setText(ui.get("stats").get("stats.summary." + stat));
        	
        	if (i % 2 == 1)
        		tr.setBackgroundColor(Color.argb(150, 128, 128, 128));
        	else
        		tr.setBackgroundColor(Color.argb(50, 128, 128, 128));
        	
        	fragmentSummary.addView(tr);
        }
	}
}