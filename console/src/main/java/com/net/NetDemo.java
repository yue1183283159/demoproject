package com.net;

import java.net.InetAddress;

public class NetDemo
{

	public static void main(String[] args) throws Exception
	{
		InetAddress address=InetAddress.getLocalHost();
		String ip=address.getHostAddress();
		String hostName=address.getHostName();
		System.out.println(address);
		System.out.println(ip);
		System.out.println(hostName);

		
		
		
		
	}

}
