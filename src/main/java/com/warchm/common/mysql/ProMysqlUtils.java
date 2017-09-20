package com.warchm.common.mysql;

import java.io.IOException;
import java.util.Properties;

public class ProMysqlUtils {
	public static Properties getProperties(){
		return getProperties("mysql.properties");
	}
	public static Properties getProperties(String properties){
		Properties pro=new Properties();
		try {
			pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(properties));
			return pro;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
