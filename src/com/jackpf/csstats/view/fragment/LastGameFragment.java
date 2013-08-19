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

public class LastGameFragment implements Fragment
{
	public TabSpec getSpec(TabHost tabHost)
	{
		return tabHost.newTabSpec("Last Game")
            .setIndicator("Last Game")
            .setContent(R.id.fragment_last_game);
	}
	
	public void setup(UI ui, Activity context)
	{
		
	}
}