package net.brojo.irc;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class InfoMarshaller{

	/**
	 * XML Document that all the data will be retrieved/sent from/to
	 */
	private Document doc;

	/**
	 * Path to the XML file
	 */
	private String filePath;
	
	/**
	 * Name of the node that stores all the user data
	 */
	private final static String NODE_NAME = "brojo";

	public InfoMarshaller(String path) throws ParserConfigurationException, SAXException, IOException {
		this.filePath = path;
		File fXmlFile = new File(path);
		System.out.println(fXmlFile.exists());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(fXmlFile);
	}
	
	private Node getNode(String attrib) {
		return ((Element) doc.getElementsByTagName(NODE_NAME).item(0)).getElementsByTagName(attrib).item(0);
	}
	
	private String getAttribute(String attrib) {
		return ((Element) doc.getElementsByTagName(NODE_NAME).item(0)).getElementsByTagName(attrib).item(0).getTextContent();
	}
	
	public String getNick() {
		System.out.println("Returning nick: " + getAttribute("nick"));
		return getAttribute("nick");
	}

	public String getRealName() {
		return getAttribute("realname");
	}
	
	public String getUserName() {
		return getAttribute("username");
	}
	
	public String getVersion() {
		return getAttribute("version");
	}
	
	public String getQuitMsg() {
		return getAttribute("quitmsg");
	}
	
	public String getPass() {
		return getAttribute("pass");
	}
	
	public List<String> getChannels() {
		return Arrays.asList(getAttribute("channels").split(",", 50));
	}

	public boolean isOp(String sender) {
		return Arrays.asList(getAttribute("ops").split(",", 50)).contains(sender);
	}

	public void setNick(String newNick) {
		getNode("nick").setNodeValue(newNick);
	}
	
	//TODO: Setters
}
