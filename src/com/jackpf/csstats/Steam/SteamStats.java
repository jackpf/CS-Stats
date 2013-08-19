package com.jackpf.csstats.Steam;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Element;

import com.jackpf.csstats.Steam.model.Parser;


/**
 * Steam Stats
 */
public class SteamStats
{
    /**
     * URL of counter strike stats xml file
     */
	public final static String PROFILE_URL = "http://steamcommunity.com/id/{id}?xml=1";
    public final static String SCREENSHOTS_URL = "http://steamcommunity.com/id/{id}/screenshots?appid=" + Data.APP_ID + "&sort=newestfirst&browsefilter=myfiles&view=imagewall";
    public final static String CSSTATS_URL = "http://steamcommunity.com/id/{id}/stats/CS:S?xml=1";

    /**
     * Some useful constants
     */
    public final static int VIEWABLE = 3;
    
    /**
     * Steam User
     */
    private SteamUser user;

    /**
     * Url
     */
    private String url;
    
    /**
     * Parser
     */
    private Parser parser;
    
    /**
     * Response
     */
    private InputStream response;

    /**
     * Construct
     *
     * @param user
     * @param url
     */
    public SteamStats(SteamUser user, String url)
    {
        this.user = user;
        this.url = url;
    }
    
    /**
     * Request given url
     * 
     * @return this
     */
    public SteamStats request() throws Exception
    {
    	DefaultHttpClient client = new DefaultHttpClient();

        HttpGet request = new HttpGet(replaceVar(url, "id", user.getSteamId()));

        HttpResponse response = client.execute(request);

        int responseCode = response.getStatusLine().getStatusCode();

        if (responseCode != 200) {
            throw new IOException(String.format("Server returned status code: %d", responseCode));
        }
        
        this.response = response.getEntity().getContent();
        
        return this;
    }

    /**
     * Parse requested page
     */
    public SteamStats parse(Parser parser) throws Exception
    {
    	this.parser = parser;
    	
    	parser.setContent(response);
        parser.parse();
        
        return this;
    }
    
    /**
     * Get key from the parsed response
     * 
     * @param key
     * @return
     */
    public String get(String key)
    {
    	return parser.getValue(key);
    }

    /**
     * Replace url vars
     *
     * @param string
     * @param name
     * @param value
     * @return string
     */
    private String replaceVar(String string, String name, String value)
    {
        return string.replaceAll("\\{" + name + "\\}", URLEncoder.encode(value));
    }
}