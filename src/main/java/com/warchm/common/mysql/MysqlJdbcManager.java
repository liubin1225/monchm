package com.warchm.common.mysql;

import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlJdbcManager {

	public static String url;
	private static String driver;
	private static String username;
	private static String password;
	@SuppressWarnings("unused")
	private static Integer min;
	@SuppressWarnings("unused")
	private static Integer max;
	@SuppressWarnings("unused")
	private static Integer initialSize;
	@SuppressWarnings("unused")
	private static Integer maxIdle;
	private static MysqlJdbcManager instance;
	private Logger logger = Logger.getLogger(MysqlJdbcManager.class);

	public static MysqlJdbcManager getInstance() {
		if (instance == null) {
			instance = new MysqlJdbcManager();
		}
		return instance;
	}

	private MysqlJdbcManager() {
	}

	static {
		try {
			initDBPrompties();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(driver);// 动态加载mysql驱动
			// 一个Connection代表一个数据库连接
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException ex) {
			logger.info(ex.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 初始化连接池
	 * 
	 * @throws UnknownHostException
	 */
	private static void initDBPrompties() throws UnknownHostException {
		// 其他参数根据实际情况进行添加
		try {
			url = ProMysqlUtils.getProperties().getProperty("jdbc.url").trim();
			driver = ProMysqlUtils.getProperties().getProperty("jdbc.driver").trim();
			username = ProMysqlUtils.getProperties().getProperty("jdbc.username").trim();
			password = ProMysqlUtils.getProperties().getProperty("jdbc.password").trim();
			min = Integer.parseInt(ProMysqlUtils.getProperties().getProperty("jdbc.min").trim());
			max = Integer.parseInt(ProMysqlUtils.getProperties().getProperty("jdbc.max").trim());
			initialSize = Integer.parseInt(ProMysqlUtils.getProperties().getProperty("jdbc.initialSize").trim());
			maxIdle = Integer.parseInt(ProMysqlUtils.getProperties().getProperty("jdbc.maxIdle").trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
