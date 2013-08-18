package com.jackpf.csstats.Steam;

/**
 * Steam User
 */
public class SteamUser
{
    /**
     * Steam ID of user
     */
    private String steamId;

    /**
     * Profile ID of user
     */
    private String profileId;

    /**
     * Constructor
     *
     * @param steamId
     */
    public SteamUser(String steamId)
    {
        setSteamId(steamId);
    }

    /**
     * Set Steam ID
     *
     * @param steamId
     */
    public void setSteamId(String steamId)
    {
        this.steamId = steamId;
    }

    /**
     * Get Steam ID
     *
     * @return steamId
     */
    public String getSteamId()
    {
        return steamId;
    }

    /**
     * Set Profile ID
     *
     * @param profileId
     */
    public void setProfileId(String profileId)
    {
        this.profileId = profileId;
    }

    /**
     * Get Profile ID
     *
     * @return profileId
     */
    public String getProfileId()
    {
        return profileId;
    }
}