package com.warchm.common.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author liubin
 * @version 2017年8月29日下午4:07:48
 */
public class AddressUtils {
	
	@SuppressWarnings("rawtypes")
	public static String ipUtils() throws SocketException{
		Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		String s = "";
		while(allNetInterfaces.hasMoreElements())
		{
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
//			System.out.println(netInterface.getName());
			Enumeration addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address) {
//					System.out.println("本机的IP = " + ip.getHostAddress());
					s = ip.getHostAddress();
				}
			}
		}
		return s;
	}

}
