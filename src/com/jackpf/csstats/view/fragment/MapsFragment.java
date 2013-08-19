package com.jackpf.csstats.view.fragment;

import java.util.HashMap;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
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

public class MapsFragment implements Fragment
{
	public TabSpec getSpec(TabHost tabHost)
	{
		return tabHost.newTabSpec("Maps")
            .setIndicator("Maps")
            .setContent(R.id.fragment_maps);
	}
	
	public void setup(UI ui, Activity context)
	{
		final LayoutInflater inflator = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
		
		HashMap<String, Integer> mapData = new HashMap<String, Integer>();
        
        for (String map : Data.MAPS) {
        	mapData.put(map, Integer.valueOf(ui.get("stats").get("stats.maps." + map + "_rounds")));
        }
        
        RelativeLayout chartContainer = (RelativeLayout) context.findViewById(R.id.fragment_maps_chart);
        GraphicalView chartView = MapGraph.getNewInstance(context, mapData);
        //chartContainer.addView(chartView);
        
        TableLayout mapsTable = (TableLayout) context.findViewById(R.id.fragment_maps_table);
        
        for (int i = 0; i < Data.MAPS.length; i++) {
        	String map = Data.MAPS[i];
        	
        	TableRow tr = (TableRow) inflator.inflate(R.layout._table_row_map, null);
        	
        	((TextView) tr.findViewById(R.id.map)).setText(map);
        	((TextView) tr.findViewById(R.id.rounds)).setText(ui.get("stats").get("stats.maps." + map + "_rounds"));
        	((TextView) tr.findViewById(R.id.wins)).setText(ui.get("stats").get("stats.maps." + map + "_wins"));
        	
        	// If 0 rounds played, don't display 100%
        	if (Integer.parseInt(ui.get("stats").get("stats.maps." + map + "_rounds")) == 0)
        		((TextView) tr.findViewById(R.id.winpct)).setText("~");
        	else
        	{
        		int winpct = Math.round(Float.parseFloat(ui.get("stats").get("stats.maps." + map + "_winpct")));
        		TextView winpctTv = (TextView) tr.findViewById(R.id.winpct);
        		winpctTv.setText(winpct + "%");
        		
        		// Colour
        		if (winpct >= 50) {
        			winpctTv.setTextColor(Color.GREEN);
        		} else {
        			winpctTv.setTextColor(Color.RED);
        		}
        	}
        	
        	if (i % 2 == 1)
        		tr.setBackgroundColor(Color.argb(150, 128, 128, 128));
        	else
        		tr.setBackgroundColor(Color.argb(50, 128, 128, 128));
        	
        	mapsTable.addView(tr);
        }
	}
}