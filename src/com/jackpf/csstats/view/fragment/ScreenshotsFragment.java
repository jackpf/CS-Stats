package com.jackpf.csstats.view.fragment;


/*public class ScreenshotsFragment implements Fragment
{
	public TabSpec getSpec(TabHost tabHost)
	{
		return tabHost.newTabSpec("Screenshots")
	            .setIndicator("Screenshots")
	            .setContent(new TabHost.TabContentFactory() {
	                public View createTabContent(String tag) {
	                    LinearLayout tabContent = (LinearLayout) context.findViewById(R.id.fragment_screenshots);
	                    
	                    String screenshot;
	                    for (int i = 0; (screenshot = screenshotsTab.get(Integer.toString(i))) != null; i++) {
		                    ImageView iv = new ImageView(context);
		                    
	                    	new ImageLoader(iv).execute(screenshotsTab.get("0"));
		                    
		                    tabContent.addView(iv);
	                    }
	                    
	                    return tabContent;
	                }
	            })
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
}*/