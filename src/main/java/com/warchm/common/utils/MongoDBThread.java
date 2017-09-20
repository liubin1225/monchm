package com.warchm.common.utils;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSFile;
import com.warchm.common.mongo.MongoManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MongoDBThread implements Runnable {
	
	public MongoDBThread(String name) {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
//		for (int i = 0; i < 9; i++) {  
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式 
			SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			try{
				//mongodb testing
				//System.out.println("子线程"+name + "运行 : " + i);
	            System.out.println("mongodb upload begin:"+df.format(new Date()));// new Date()为获取当前系统时间
//	            List<ServerAddress> addresses = new ArrayList<ServerAddress>();
//
//
//                ServerAddress address1 = new ServerAddress("192.168.0.199" , 27017);
//                ServerAddress address2 = new ServerAddress("192.168.0.198" , 27017);
//                ServerAddress address3 = new ServerAddress("192.168.0.197" , 27017);
//                addresses.add(address1);
//                addresses.add(address2);
//                addresses.add(address3);
//        		//MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
//        		MongoCredential credential = MongoCredential.createCredential("vrkb","admin","vrkb0516".toCharArray());
//
//        		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
//        		credentials.add(credential);
//
//        		//参数设置
//        		MongoClientOptions options = MongoClientOptions.builder().serverSelectionTimeout(3000).build();
//        		MongoClient mongoClient = new MongoClient(addresses,credentials,options);
//	            DB db = mongoClient.getDB("mongotest");
//	            MongoDatabase mongoDatabase = mongoClient.getDatabase("mongotest");
				//获取数据库所有集合
//			 	MongoCredential credential = MongoCredential.createCredential("vrkb","admin","vrkb0516".toCharArray());
//			 	ServerAddress serverAddress;
//		        serverAddress = new ServerAddress("192.168.0.162",27017);
//		        java.util.List<ServerAddress> addrs = new ArrayList<ServerAddress>();
//		        addrs.add(serverAddress);
//		        java.util.List<MongoCredential> credentials = new ArrayList<MongoCredential>();
//		        credentials.add(credential);
//		        MongoClient mongoClient = new MongoClient(addrs, credentials);
	            //db.setReadPreference(readPreference);
	            DB db = MongoManager.getInstance().getDB();
				GridFS gridFS= new GridFS(db,"fs"+df2.format(new Date())+"");
				String filename="E:\\Pictures\\VRVis-Logo.png";
//				String filename="E:\\Pictures\\VRkb-Logo.png";
	        	GridFSFile file2 = gridFS.createFile(new File(filename));
	        	int pot1 = filename.lastIndexOf("\\");
	        	file2.put("filename", filename.substring(pot1+1));
				file2.put("userId", 1);
				file2.put("uploadDate", new Date());
				file2.put("uploadSystemDate", df.format(new Date()));
				file2.put("aliases", "3DModel");
				int pot = filename.lastIndexOf(".");
				file2.put("contentType", filename.substring(pot));
		        file2.save();
		        System.out.println("mongodb upload end:"+ df.format(new Date()));// new Date()为获取当前系统时间
			}catch(Exception e){
				e.printStackTrace();
			}
//            try {  
//                Thread.sleep((int) Math.random() * 10);  
//            } catch (InterruptedException e) {  
//                e.printStackTrace();  
//            }  
//        }  

	}

}
