package com.jackpf.csstats.Steam;

import com.jackpf.csstats.Steam.parser.XmlParser;

/**
 * Steam User
 */
public class SteamUser
{
    /**
     * ID of user
     * Can be either a profile id or custom url depending on type
     */
    private String id;
    
    /**
     * Url type
     * Can be either "profiles" or "id"
     */
    private String type;

    /**
     * Constructor
     *
     * @param steamId
     */
    public SteamUser(String id, String type)
    {
        this.id = id;
        this.type = type;
    }

    /**
     * Get ID
     *
     * @return id
     */
    public String getId()
    {
        return id;
    }

    /**
     * Get type
     *
     * @return type
     */
    public String getType()
    {
        return type;
    }
    
    /**
     * Shorthand to get profile stats for user
     * 
     * @return SteamStats
     */
    public SteamStats getProfile() throws Exception
    {
        return new SteamStats(this, SteamStats.PROFILE_URL)
            .request()
            .parse(new XmlParser());
    }
    
    /**
     * Shorthand to get stats for user
     * 
     * @return SteamStats
     */
    public SteamStats getStats() throws Exception
    {
        return new SteamStats(this, SteamStats.CSSTATS_URL)
            .request()
            .parse(new XmlParser());
    }
}