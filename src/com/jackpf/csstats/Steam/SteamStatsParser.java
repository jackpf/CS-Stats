package com.jackpf.csstats.Steam;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Steam Stats Parser
 */
public class SteamStatsParser
{
	/**
	 * Xml stream
	 */
    private InputStream is;
    
    /**
     * Constructor
     * 
     * @param is
     */
    public SteamStatsParser(InputStream is)
    {
        this.is = is;
    }

    /**
     * Parse
     * 
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public Element parse() throws IOException, ParserConfigurationException, SAXException
    {
    	DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = db.parse(is);
		
		return document.getDocumentElement();
    }
    
    /**
     * Get node value by key names
     * Eg. "stats.weapons.deagle_shots"
     * 
     * @param node
     * @param key
     * @return string
     */
    public String getValue(Node node, String key)
    {
    	String[] keys = key.split("\\.");
    	NodeList stats = node.getChildNodes();
		
		for (int i = 0; i < stats.getLength(); i++) {
			if (stats.item(i).getNodeType() != Node.ELEMENT_NODE) { 
				continue;
			}
			
			Element stat = (Element) stats.item(i);

			if (stat.getNodeName().equals(keys[0])) {
				if (stat.getChildNodes().getLength() > 1) {
					// Reform key
					String _key = "";
					for (int j = 1; j < keys.length; j++) {
						_key += keys[j];
						if (j != keys.length) {
							_key += ".";
						}
					}
					
					return getValue(stat, _key);
				} else {
					return stat.getTextContent();
				}
			}
		}
		
		return null;
    }
}