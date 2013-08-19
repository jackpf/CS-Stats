package com.jackpf.csstats.Steam.model;

import java.io.InputStream;

public interface Parser
{
	public void setContent(InputStream content);
	public Object parse() throws Exception;
	public String getValue(Object parsedContent, String key);
}