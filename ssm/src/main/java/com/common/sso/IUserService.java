package com.common.sso;

public interface IUserService {
	String login(String username, String password);

	boolean checkLogin(String ticket);
	
	void removeTicket(String ticket);
}
