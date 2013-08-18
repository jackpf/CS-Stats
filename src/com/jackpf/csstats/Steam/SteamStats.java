package com.jackpf.csstats.Steam;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


/**
 * Steam Stats
 */
public class SteamStats
{
    /**
     * URL of counter strike stats xml file
     */
    public final static String CSSTATS_URL = "http://steamcommunity.com/id/{id}/stats/CS:S?xml=1";

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
    private SteamStatsParser parser;
    
    /**
     * Parsed xml root
     */
    private Element xml;

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
     * Get a given user's stats for a given xml file
     */
    public void getStats() throws IOException, ClientProtocolException, ParserConfigurationException, SAXException
    {
        DefaultHttpClient client = new DefaultHttpClient();

        HttpGet request = new HttpGet(replaceVar(url, "id", user.getSteamId()));

        HttpResponse response = client.execute(request);

        int responseCode = response.getStatusLine().getStatusCode();

        if (responseCode != 200) {
            throw new IOException(String.format("Server returned status code: %d", responseCode));
        }

        parser = new SteamStatsParser(response.getEntity().getContent());
        xml = parser.parse();
    }
    
    /**
     * Get key from the parsed xml
     * 
     * @param key
     * @return
     */
    public String get(String key)
    {
    	return parser.getValue(xml, key);
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
        return string.replaceAll("\\{" + name + "\\}", value);
    }
}