package net.brojo.irc;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class UserInfo {

	/**
	 * Username of user
	 */
	private String username;
	
	/**
	 * Real name of user
	 */
	private String realname;
	
	/**
	 * Nick that actually shows up in-game
	 */
	private String nick;
	
	/**
	 * Password of the user
	 */
	private String pass;
	
	/**
	 * Version of the user
	 */
	private String version;
	
	/**
	 * Quit message that is displayed when the user sends a QUIT command
	 */
	private String quitmsg;
	
	/**
	 * Channels this user is in
	 */
	private List<String> channels;
	
	/**
	 * Marshaller in charge of getting and setting of user data to/from a file
	 */
	private InfoMarshaller infoMarshaller;
	
	public UserInfo(String filePath) throws ParserConfigurationException, SAXException, IOException {
		infoMarshaller = new InfoMarshaller(filePath);
	}
	
	public String getUserName() {
		return infoMarshaller.getUserName();
	}
	
	public String getRealName() {
		return infoMarshaller.getRealName();
	}
	
	public String getQuitMsg() {
		return infoMarshaller.getQuitMsg();
	}
	
	public String getVersion() {
		return infoMarshaller.getVersion();
	}
	
	public String getPass() {
		return infoMarshaller.getPass();
	}
	
	public String getNick() {
		return infoMarshaller.getNick();
	}
	
	public List<String> getChannels() {
		return infoMarshaller.getChannels();
	}
	
	//TODO: Setters

}
