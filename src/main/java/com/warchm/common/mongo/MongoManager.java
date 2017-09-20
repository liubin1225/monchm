package com.warchm.common.mongo;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MongoManager {
	
	public static String gridFSstr;
	private static String loginuser;
	private static String loginpas;
	private static String HOST;
	@SuppressWarnings("unused")
	private static Integer PORT;
	private static Integer slaverPORT;
	private static String mongoDefault;
	private static String mongoDbName;
	@SuppressWarnings("unused")
	private static String masterHost,slaverHost,arbiterHost;
	@SuppressWarnings("unused")
	private static int MAX_WAIT_TIME,SOCKET_TIME_OUT,CONNECT_TIMEOUT,POOLSIZE,BLOCKSIZE;
    private static MongoManager instance;
    @SuppressWarnings("unused")
    private static Mongo mongo = null;
    @SuppressWarnings("unused")
	private static SimpleDateFormat df = new SimpleDateFormat("yyyyMM");//设置日期格式
    private static Logger logger = Logger.getLogger(MongoManager.class);
    
    public static MongoManager getInstance(){
    	if(instance == null){
    		instance = new MongoManager();
    	}
    	return instance;
    }
    private MongoManager() { }  
  
    static {  
        try {
			initDBPrompties();
		} catch (UnknownHostException e) {
//			e.printStackTrace();
			logger.error(e.getMessage());
		}  
    }  
    
    @SuppressWarnings({ "resource", "deprecation" })
	public DB getDB() {
    	DB db = null;
		//连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址  
		//ServerAddress()两个参数分别为 服务器地址 和 端口  
    	List<ServerAddress> addrs = new ArrayList<ServerAddress>();
    	
    	//仲裁节点不涉及存储数据，可以不添加
    	ServerAddress address1 = new ServerAddress(HOST,slaverPORT);
    	//ServerAddress address2 = new ServerAddress(masterHost,PORT); 
    	//ServerAddress address3 = new ServerAddress(slaverHost,PORT); 
    	addrs.add(address1);  
//    	addrs.add(address2); 
//    	addrs.add(address3);
//		ServerAddress serverAddress;
//		serverAddress = new ServerAddress(HOST,PORT);
//		List<ServerAddress> addrs = new ArrayList<ServerAddress>();  
//		addrs.add(serverAddress); 
		//MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码  
		MongoCredential credential = MongoCredential.createCredential(loginuser,mongoDefault,loginpas.toCharArray());
		
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		credentials.add(credential);  
		
		//参数设置
		//MongoClientOptions options = MongoClientOptions.builder().serverSelectionTimeout(1000).build();
		//connectionsPerHost 不宜配置过大，官方默认值由原来10改成100,mongodb默认最大连接数2000
		MongoClientOptions options = MongoClientOptions.builder()
                .connectionsPerHost(1000) //每个主机设置的最大连接数
                .threadsAllowedToBlockForConnectionMultiplier(20) //集乘数的线程数量允许块等待连接。
                .serverSelectionTimeout(3000) //设置服务器选择超时以毫秒为单位
                .build();
//		.readPreference(ReadPreference.nearest())
		MongoClient mongoClient = new MongoClient(addrs,credentials,options);
		db = mongoClient.getDB(mongoDbName);  
          
        //通过连接认证获取MongoDB连接 
        //MongoClient mongoClient = new MongoClient(HOST,PORT);
        //MongoDB 3.0版本以前适用安全认证方法
        //boolean flag = db.authenticate(loginuser, loginpas.toCharArray());
        return db;
    }
    
    /**
     * MongoDatabase MongoDb3.0+以上版本，获取DB方法
     * @return
     */
    @SuppressWarnings({ "resource" })
	public MongoDatabase getMongoDatabase() {
    	//连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址  
		//ServerAddress()两个参数分别为 服务器地址 和 端口  
    	List<ServerAddress> addrs = new ArrayList<ServerAddress>();
   
    	//仲裁节点不涉及存储数据，可以不添加
    	ServerAddress address1 = new ServerAddress(HOST,slaverPORT);
//    	ServerAddress address2 = new ServerAddress(masterHost,PORT); 
//    	ServerAddress address3 = new ServerAddress(slaverHost,PORT); 
    	addrs.add(address1);  
//    	addrs.add(address2); 
//    	addrs.add(address3);
//    			ServerAddress serverAddress;
//    			serverAddress = new ServerAddress(HOST,PORT);
//    			List<ServerAddress> addrs = new ArrayList<ServerAddress>();  
//    			addrs.add(serverAddress); 
		//MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码  
		MongoCredential credential = MongoCredential.createCredential(loginuser,mongoDefault,loginpas.toCharArray());
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		credentials.add(credential);   
//		//connectionsPerHost 不宜配置过大，官方默认值由原来10改成100,mongodb默认最大连接数2000
		MongoClientOptions options = MongoClientOptions.builder()
                .connectionsPerHost(100) //每个主机设置的最大连接数
                .threadsAllowedToBlockForConnectionMultiplier(20) //集乘数的线程数量允许块等待连接。
                .serverSelectionTimeout(3000) //设置服务器选择超时以毫秒为单位
                .build();
		MongoClient mongoClient = new MongoClient(addrs,credentials,options);
//		MongoClient mongoClient = new MongoClient(addrs);  
		MongoDatabase mdb = mongoClient.getDatabase(mongoDbName);
        return mdb;
    }
    
    /**
     * MongoDatabase MongoDb3.0+以上版本，获取DB方法
     * @return
     */
    @SuppressWarnings({ "resource" })
	public MongoClient getMongoClient() {
    	//连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址  
		//ServerAddress()两个参数分别为 服务器地址 和 端口  
    	List<ServerAddress> addrs = new ArrayList<ServerAddress>();
   
    	//仲裁节点不涉及存储数据，可以不添加
    	ServerAddress address1 = new ServerAddress(HOST,slaverPORT);
//    	ServerAddress address2 = new ServerAddress(masterHost,PORT); 
//    	ServerAddress address3 = new ServerAddress(slaverHost,PORT); 
    	addrs.add(address1);  
//    	addrs.add(address2); 
//    	addrs.add(address3);
//    			ServerAddress serverAddress;
//    			serverAddress = new ServerAddress(HOST,PORT);
//    			List<ServerAddress> addrs = new ArrayList<ServerAddress>();  
//    			addrs.add(serverAddress); 
		//MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码  
		MongoCredential credential = MongoCredential.createCredential(loginuser,mongoDefault,loginpas.toCharArray());
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		credentials.add(credential);   
//		//connectionsPerHost 不宜配置过大，官方默认值由原来10改成100,mongodb默认最大连接数2000
		MongoClientOptions options = MongoClientOptions.builder()
                .connectionsPerHost(100) //每个主机设置的最大连接数
                .threadsAllowedToBlockForConnectionMultiplier(20) //集乘数的线程数量允许块等待连接。
                .serverSelectionTimeout(3000) //设置服务器选择超时以毫秒为单位
                .build();
		MongoClient mongoClient = new MongoClient(addrs,credentials,options);
        return mongoClient;
    }
    
    /** 
     * 初始化连接池 
     * @throws UnknownHostException 
     */  
    private static void initDBPrompties() throws UnknownHostException {  
        // 其他参数根据实际情况进行添加  
        try {
//        	gridFSstr = ProMongoUtils.getProperties().getProperty("mongo.gridFSstr").trim()+df.format(new Date());
        	gridFSstr = ProMongoUtils.getProperties().getProperty("mongo.gridFSstr").trim()+"201709";
        	PORT = Integer.parseInt(ProMongoUtils.getProperties().getProperty("mongo.PORT").trim());
        	slaverPORT = Integer.parseInt(ProMongoUtils.getProperties().getProperty("mongo.slaverPORT").trim());
        	HOST = ProMongoUtils.getProperties().getProperty("mongo.HOST").trim();
        	loginuser = ProMongoUtils.getProperties().getProperty("mongo.loginuser").trim();
    		loginpas = ProMongoUtils.getProperties().getProperty("mongo.loginpas").trim();
    		masterHost = ProMongoUtils.getProperties().getProperty("mongo.masterHost").trim();
    		slaverHost = ProMongoUtils.getProperties().getProperty("mongo.slaverHost").trim();
    		arbiterHost = ProMongoUtils.getProperties().getProperty("mongo.arbiterHost").trim();
        	mongoDbName = ProMongoUtils.getProperties().getProperty("mongo.mongoDbName").trim();
        	mongoDefault = ProMongoUtils.getProperties().getProperty("mongo.mongoDefault").trim();
        	MAX_WAIT_TIME = Integer.parseInt(ProMongoUtils.getProperties().getProperty("mongo.maxWaitTime").trim());
        	SOCKET_TIME_OUT = Integer.parseInt(ProMongoUtils.getProperties().getProperty("mongo.socketTimeout").trim());
        	CONNECT_TIMEOUT = Integer.parseInt(ProMongoUtils.getProperties().getProperty("mongo.connectTimeout").trim());
        	POOLSIZE = Integer.parseInt(ProMongoUtils.getProperties().getProperty("mongo.poolsize").trim());
        	BLOCKSIZE = Integer.parseInt(ProMongoUtils.getProperties().getProperty("mongo.blocksize").trim());
        }catch (MongoException e) {
        	logger.error(e.getMessage());
        }
    }  
} 