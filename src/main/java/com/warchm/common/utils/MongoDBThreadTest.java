package com.warchm.common.utils;

public class MongoDBThreadTest {
	
	public static void main(String[] args) {  
        new Thread(new MongoDBThread("C")).start();
        new Thread(new MongoDBThread("C1")).start(); 
        new Thread(new MongoDBThread("C2")).start(); 
        new Thread(new MongoDBThread("C3")).start(); 
//        new Thread(new MongoDBThread("C4")).start(); 
//        new Thread(new MongoDBThread("C5")).start(); 
//        new Thread(new MongoDBThread("C6")).start(); 
//        new Thread(new MongoDBThread("C7")).start(); 
//        new Thread(new MongoDBThread("C8")).start(); 
//        new Thread(new MongoDBThread("C9")).start(); 
//        new Thread(new MongoDBThread("C10")).start();
//        new Thread(new MongoDBThread("C11")).start(); 
//        new Thread(new MongoDBThread("C12")).start(); 
//        new Thread(new MongoDBThread("C13")).start(); 
//        new Thread(new MongoDBThread("C14")).start(); 
//        new Thread(new MongoDBThread("C15")).start(); 
//        new Thread(new MongoDBThread("C16")).start(); 
//        new Thread(new MongoDBThread("C17")).start(); 
//        new Thread(new MongoDBThread("C18")).start(); 
//        new Thread(new MongoDBThread("C19")).start();
		
		//获取数据库所有集合
//	 	MongoCredential credential = MongoCredential.createCredential("root","admin","root".toCharArray());
//	 	ServerAddress serverAddress;
//        serverAddress = new ServerAddress("192.168.0.61",27017);
//        java.util.List<ServerAddress> addrs = new ArrayList<ServerAddress>();
//        addrs.add(serverAddress);
//        java.util.List<MongoCredential> credentials = new ArrayList<MongoCredential>();
//        credentials.add(credential);
//        MongoClient mongoClient = new MongoClient(addrs, credentials);
//    	MongoDatabase mongoDB = mongoClient.getDatabase("test");
//		
//         System.out.println("Connect to database successfully");
//         
//         MongoCollection<Document> collection = mongoDB.getCollection("fs");
//         System.out.println("集合 test 选择成功");
//         //插入文档  
//         /** 
//         * 1. 创建文档 org.bson.Document 参数为key-value的格式 
//         * 2. 创建文档集合List<Document> 
//         * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document) 
//         * */
//         Document document = new Document("title", "MongoDB").  
//         append("description", "database").  
//         append("likes", 100).  
//         append("by", "Fly");  
//         List<Document> documents = new ArrayList<Document>();  
//         documents.add(document);  
//         collection.insertMany(documents);  
//         System.out.println("文档插入成功");  
    } 

}
