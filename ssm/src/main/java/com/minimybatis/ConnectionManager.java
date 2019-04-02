package com.minimybatis;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {
	public static Connection get() throws Exception {
		return DriverManager.getConnection(Config.DEFAULT.getUrl(), Config.DEFAULT.getUser(), Config.DEFAULT.getPwd());
	}
}
