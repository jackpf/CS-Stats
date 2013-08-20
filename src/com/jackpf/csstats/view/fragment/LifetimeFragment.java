package com.jackpf.csstats.view.fragment;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.text.Html;
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
        
        String[] stats    = {"headshots", "zsniperkills", "blindkills",
                           "enemywpnkills", "knifekills", "",
                           "dominations", "dominationoverkills", "revenges",
                           "bombsplanted", "bombsdefused", "hostagesrescued",
                           "dmg", "money", "decals"},
                 types    = {"int", "int", "int",
                           "int", "int", "null",
                           "int", "int", "int",
                           "int", "int", "int",
                           "int", "money", "int"};
        
        for (int i = 0, k = 0; i < stats.length; k++) {
            TableRow tr = (TableRow) inflator.inflate(R.layout._table_row_stat_triplet, null);
            
            for (int j = 1; i < stats.length && j <= 3; j++, i++) {
                String stat = stats[i], type = types[i];
                String key = SummaryFragment.getKey(stat, context);
                
                String value = SummaryFragment.parseValue(ui.get("stats").get("stats.lifetime." + stat), type);
                String html = String.format(
                    context.getString(R.string.stat_format),
                    key,
                    value
                );
                
                int tv = context.getResources().getIdentifier("col" + j , "id", context.getPackageName());
                
                ((TextView) tr.findViewById(tv)).setText(Html.fromHtml(html));
            }
            
            if (k % 2 == 1)
                tr.setBackgroundColor(Color.argb(50, 128, 128, 128));
            else
                tr.setBackgroundColor(Color.argb(50, 129, 179, 215));
            
            table.addView(tr);
        }
        
        ((LinearLayout) context.findViewById(R.id.fragment_lifetime)).addView(fragment);
    }
}