package com.jackpf.csstats.Steam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SteamIdTransformer
{
	/**
	 * Type of the id, eg: "profiles" or "id"
	 * To be used in the url
	 */
	private String identifier;
	
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
	public SteamIdTransformer(String input)
	{
		transform(input);
	}
	
	private void transform(String input)
	{
		Matcher mUrlProfileId = pUrlProfileId.matcher(input);
		Matcher mUrlSteamId = pUrlSteamId.matcher(input);
		Matcher mSteamId = pSteamId.matcher(input);
		
		if (mUrlProfileId.find() || mUrlSteamId.find()) {
			identifier = mUrlProfileId.group(1);
			id = mUrlProfileId.group(2);
		} else {
			steamIdToProfileId(input);
		}
	}
	
	private void steamIdToProfileId(String steamId)
	{
		
		
	}
}