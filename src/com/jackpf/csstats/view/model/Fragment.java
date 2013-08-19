package com.jackpf.csstats.view.model;

import android.app.Activity;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.jackpf.csstats.view.UI;

public interface Fragment
{
	public TabSpec getSpec(TabHost tabHost);
	public void setup(UI ui, Activity context);
}