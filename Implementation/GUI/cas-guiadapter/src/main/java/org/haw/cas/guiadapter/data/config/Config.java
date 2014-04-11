package org.haw.cas.guiadapter.data.config;

/**
 * 
 * This class holds all relevant Config-Information.
 * 
 * @author alex
 * 
 */

public class Config {
	public final String host;
	public final Integer port;
	public final String user;
	public final String password;
	
	public static Config official = new Config("3ten.de",5672, "amdin","IlmSG400");
	public static Config localhost = new Config("localhost",5672, "guest","guest");

	public Config(String host, Integer port, String user, String password) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public Integer getPort() {
		return port;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public static Config getOfficial() {
		return official;
	}

	public static Config getLocalhost() {
		return localhost;
	}

	@Override
	public String toString() {
		return "Config [host=" + host + ", port=" + port + ", user=" + user
				+ ", password=" + password + "]";
	}
	
	

}
