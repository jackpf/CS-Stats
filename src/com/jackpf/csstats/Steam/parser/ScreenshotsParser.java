package com.jackpf.csstats.Steam.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jackpf.csstats.Steam.model.Parser;

/**
 * Screenshots html Parser
 */
public class ScreenshotsParser implements Parser
{
    /**
     * Html stream
     */
    private InputStream is;
    
    /**
     * Array of screenshot urls
     */
    private ArrayList<String> screenshots = new ArrayList<String>();
    
    /**
     * Set input stream
     * 
     * @param is
     */
    public void setContent(InputStream is)
    {
        this.is = is;
    }

    /**
     * Parse
     * 
     * @throws IOException
     */
    public Parser parse() throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        String line;
        String pattern = "<img src=\\\"(http://cloud.*?\\.resizedimage)\\\"";
        Pattern p = Pattern.compile(pattern);
        while ((line = br.readLine()) != null) {
            Matcher m = p.matcher(line);
            
            if (m.find()) {
                screenshots.add(m.group(1));
            }
        }

        return this;
    }

    /**
     * Get screenshot
     *
     * @param key
     * @return string
     */
    public String getValue(String key)
    {
        int iKey = Integer.parseInt(key);
        
        if (iKey >= screenshots.size()) {
            return null;
        }
        
        return screenshots.get(iKey);
    }
}