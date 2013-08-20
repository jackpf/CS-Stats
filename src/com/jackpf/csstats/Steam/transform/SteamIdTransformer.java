package com.jackpf.csstats.Steam.transform;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SteamIdTransformer
{
	/**
	 * Type of the id, eg: "profiles" or "id"
	 * To be used in the url
	 */
	private String type;
	
	/**
	 * Id
	 * If identifier = profiles, this will be a profile id
	 * If identifier = id this will be the custom url id
	 */
	private String id;
	
	/**
	 * Regex patterns
	 */
	private Pattern pUrlProfileId = Pattern.compile(".*\\/(profiles)\\/([0-9]+)");
	private Pattern pUrlSteamId = Pattern.compile(".*\\/(id)\\/([0-9a-zA-Z]+)");
	private Pattern pSteamId = Pattern.compile("(STEAM_[0-9]:[0-9]:[0-9]+)");
	
	/**
	 * Constructor
	 * 
	 * @param input
	 */
	public SteamIdTransformer(String input) throws TransformException
	{
		transform(input);
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
	 * Get id
	 * 
	 * @return id
	 */
	public String getId()
	{
		return id;
	}
	
	/**
	 * Transform
	 * 
	 * @param input
	 */
	private void transform(String input) throws TransformException
	{
		Matcher mUrlProfileId = pUrlProfileId.matcher(input);
		Matcher mUrlSteamId = pUrlSteamId.matcher(input);
		Matcher mSteamId = pSteamId.matcher(input);
		
		if (mUrlSteamId.find()) {
			type = mUrlSteamId.group(1);
			id = mUrlSteamId.group(2);
		} else if (mUrlProfileId.find()) {
			type = mUrlProfileId.group(1);
			id = mUrlProfileId.group(2);
		} else if(mSteamId.find()) {
			type = "profiles";
			id = Long.toString(steamIdToProfileId(input));
		} else {
			throw new TransformException("Malformed input");
		}
	}
	
	/**
	 * Get a profileId from a STEAM_ID
	 * Thanks to alliedmodders
	 * 
	 * @param steamId
	 * @return
	 */
	private Long steamIdToProfileId(String steamId)
	{
		String[] parts = steamId.split(":");
		
		int server = Integer.parseInt(parts[1]),
		    authId = Integer.parseInt(parts[2]);
		
		return (authId * 2) + 76561197960265728L + server;
	}
}