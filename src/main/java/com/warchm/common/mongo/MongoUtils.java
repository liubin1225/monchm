package com.warchm.common.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.warchm.common.utils.BsonTool;
import com.warchm.common.utils.Zip4JUtils;
import com.warchm.common.mysql.MysqlJdbcManager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;


public class MongoUtils {
	
//	private static String cmdNStr = "C:\\Program Files\\WinRAR\\winrar  a -ep";
	
	private static Logger logger = Logger.getLogger(MongoUtils.class);
	
	/**
	 * 上传
	 * 1:上传成功
	 * 2:重命名文件不存在
	 * 99:MySQL已经存有该modelid的记录
	 */
	public static int mguploadfile() {

		// 记录时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMM");// 设置日期格式
		GridFS gridFS = null;
		//String filepathv = "F:\\prod_obj";
		String filepathv = "";
		if(!"".equalsIgnoreCase(filepathv) && checkMsql(filepathv) == 99){
			return 99;
		}else{
			filepathv = "F:\\test\\211";
			//清空带有.vrkb后缀的文件
			removefile(filepathv);
			
			String gspath = "model" + df2.format(new Date());
			DB db = MongoManager.getInstance().getDB();
			File file = new File(filepathv);
			if (file.exists()) {
				LinkedList<File> list = new LinkedList<File>();
				File[] files = file.listFiles();
				for (File file2 : files) {
					if (file2.isDirectory()) {
						gridFS = new GridFS(db, gspath);
						GridFSFile gridfile = null;
						list.add(file2);
						File temp_file;
						if (!list.isEmpty()) {
							for (int i = 0; i < list.size(); i++) {
								temp_file = list.removeFirst();
								files = temp_file.listFiles();
								for (File file3 : files) {
									if (file3.isDirectory()) {
										// System.out.println("文件夹:" +
										// file2.getAbsolutePath());
										list.add(file3);
										// folderNum++;
									} else {
										InputStream in = null;
										int num = file3.getParent().lastIndexOf("\\");
										int num2 = num + 1;
										String strp = file3.getParent().substring(num2, file3.getParent().length());
										
										File file4 = new File(file3.getAbsolutePath());
										
										//判断z
										int pot = file3.getName().lastIndexOf(".");
										if (file3.getName().substring(pot).equalsIgnoreCase(".zip")) {
											continue;
										}
										if (file3.getName().substring(pot).equalsIgnoreCase(".rar")) {
											continue;
										}
										if (file3.getName().substring(pot).equalsIgnoreCase(".db")) {
											continue;
										}
										
										if (file3.getName().substring(pot).indexOf(".obj") != -1) {
											logger.info("系统记录mongoDB==" + file3.getName() + "上传开始时间====:" + df.format(new Date()));
											int num23 = file3.getAbsolutePath().lastIndexOf(".");
											int num231 = num23 + 1;
											String name23 = file3.getAbsolutePath().substring(0, num231);
											String zipname = name23 + "zip";
											try {
//												ZipUtils.zip(zipname, new File(file3.getAbsolutePath()));
												long size = 0;
												size = Zip4JUtils.zip(file3.getAbsolutePath(), zipname, false, "");
//												String cmdur = ""+cmdNStr+" \""+zipname+"\" \""+file3.getAbsolutePath()+"\"";
//												System.out.println("压缩执行中..."+cmdur);
//												ZipUtils.zipU(cmdur);
												String rename = name23 + "vrkb";
												// 在进行重命名
												File oldfile = new File(zipname);
												File newfile = new File(rename);
												if (!oldfile.exists()) {
													return 2;// 重命名文件不存在
												}
												/// if (newfile.exists())//
												// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
												// System.out.println(rename +
												// "已经存在！");
												//// else {
												oldfile.renameTo(newfile);
												// }
												// 根据文件创建文件的输入流
												in = new FileInputStream(newfile);
												// 创建字节数组
												gridfile = gridFS.createFile(in);// 创建gridfs文件


//												String renameS = zipname.substring(zipname.lastIndexOf("\\")+1, zipname.length());
//												if(StringUtils.isEmpty(renameS)){
//													renameS = zipname;
//												}

												String renameS = rename.substring(rename.lastIndexOf("\\")+1, rename.length());
												if(StringUtils.isEmpty(renameS)){
													renameS = rename;
												}

												String s = UUID.randomUUID().toString();// 用来生成数据库的主键id非常不错。
												s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
														+ s.substring(19, 23) + s.substring(24);
												gridfile.put("reid", s);
												gridfile.put("filename", renameS);
												gridfile.put("modelId", strp);
												gridfile.put("userId", 1);
												gridfile.put("aliases", "3DModel");
												gridfile.put("uploadDate", new Date());
												gridfile.put("uploadSystemDate", df.format(new Date()));
												gridfile.put("contentType", ".vrkb");
												gridfile.put("contentSize", size);
												gridfile.save();

												// mysql
												Connection con = null;
												Statement st = null;
												try {
													con = MysqlJdbcManager.getInstance().getConnection();
													st = con.createStatement();
													String sql = "insert into model_file(Model_id,Collection,UUid,filename,compress_size) values("
																	+ strp + ",'model" + df2.format(new Date()) + "','" + s + "','"
																	+ renameS + "',"+size+")";
													st.executeUpdate(sql);
													st.close();
												} catch (SQLException ex) {
													logger.error(ex.getMessage());
												} finally {
													if (st != null){
														try {
															st.close();
														} catch (Exception ex) {
															ex.printStackTrace();
														}
													}
													if (con != null){
														try {
															con.close();
														} catch (Exception ex) {
															ex.printStackTrace();
														}
													}
												}
											} catch (Exception e) {
												// TODO Auto-generated catch block
												logger.error(e.getMessage());
											}
											logger.info("系统记录mongoDB==" + file3.getName() + "上传结束时间====:" + df.format(new Date()));
										} else {
											logger.info("系统记录mongoDB==" + file3.getName() + "上传开始时间====:" + df.format(new Date()));
											try {
												// 根据文件创建文件的输入流
												in = new FileInputStream(file4);
												// 创建字节数组
												gridfile = gridFS.createFile(in);// 创建gridfs文件

												String s = UUID.randomUUID().toString();// 用来生成数据库的主键id非常不错。
												s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
														+ s.substring(19, 23) + s.substring(24);
												gridfile.put("reid", s);
												gridfile.put("filename", file3.getName());
												gridfile.put("modelId", strp);
												gridfile.put("userId", 1);
												gridfile.put("aliases", "3DModel");
												gridfile.put("uploadDate", new Date());
												gridfile.put("uploadSystemDate", df.format(new Date()));
												gridfile.put("contentType", file3.getName().substring(pot));
												gridfile.save();

												// mysql
												Connection con = null;
												Statement st = null;
												try {
													con = MysqlJdbcManager.getInstance().getConnection();
													st = con.createStatement();
													String sql = "insert into model_file(Model_id,Collection,UUid,filename) values("
															+ strp + ",'model" + df2.format(new Date()) + "','" + s + "','"
															+ file3.getName() + "')";
													st.executeUpdate(sql);
													st.close();
												} catch (SQLException ex) {
													System.out.println(ex.toString());
												} finally {
													if (st != null){
														try {
															st.close();
														} catch (Exception ex) {
															ex.printStackTrace();
														}
													}
													if (con != null){
														try {
															con.close();
														} catch (Exception ex) {
															ex.printStackTrace();
														}
													}
												}
											} catch (Exception e) {
												logger.error(e.getMessage());
											}
											logger.info("系统记录mongoDB==" + file2.getName() + "上传结束时间====:" + df.format(new Date()));
										}
									}
								}
							}
						}
					} else {
						int num = file2.getParent().lastIndexOf("\\");
						int num2 = num + 1;
						String strp = file2.getParent().substring(num2, file2.getParent().length());
						// Date()为获取当前系统时间
						gridFS = new GridFS(db, gspath);
						GridFSFile gridfile = null;
						list.add(file2);
						if (file2 != null) {
							InputStream in = null;

							int pot = file2.getName().lastIndexOf(".");
							if (file2.getName().substring(pot).equalsIgnoreCase(".zip")) {
								continue;
							}
							if (file2.getName().substring(pot).equalsIgnoreCase(".rar")) {
								continue;
							}
							if (file2.getName().substring(pot).equalsIgnoreCase(".db")) {
								continue;
							}

							if (file2.getName().substring(pot).indexOf(".obj") != -1) {
								logger.info("系统记录mongoDB==" + file2.getName() + "上传开始时间====:" + df.format(new Date()));
								int num23 = file2.getAbsolutePath().lastIndexOf(".");
								int num231 = num23 + 1;
								String name23 = file2.getAbsolutePath().substring(0, num231);
								String zipname = name23 + "zip";
								try {
//									ZipUtils.zip(zipname, new File(file2.getAbsolutePath()));

									long size = 0;
									size = Zip4JUtils.zip(file2.getAbsolutePath(), zipname, false, "");
//									String cmdur = ""+cmdNStr+" \""+zipname+"\" \""+file2.getAbsolutePath()+"\"";
//									System.out.println("压缩执行中..."+cmdur);
//									ZipUtils.zipU(cmdur);
									String rename = name23 + "vrkb";
									// 在进行重命名
									File oldfile = new File(zipname);
									File newfile = new File(rename);
									if (!oldfile.exists()) {
										 return 2;// 重命名文件不存在
									}
									/// if (newfile.exists())//
									// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
									// System.out.println(rename + "已经存在！");
									//// else {
									oldfile.renameTo(newfile);
									// }
									// 根据文件创建文件的输入流
									in = new FileInputStream(newfile);
									// 创建字节数组
									gridfile = gridFS.createFile(in);// 创建gridfs文件

									String renameS = rename.substring(rename.lastIndexOf("\\")+1, rename.length());
									if(StringUtils.isEmpty(renameS)){
										renameS = rename;
									}

//									String renameS = zipname.substring(zipname.lastIndexOf("\\")+1, zipname.length());
//									if(StringUtils.isEmpty(renameS)){
//										renameS = zipname;
//									}
									String s = UUID.randomUUID().toString();// 用来生成数据库的主键id非常不错。
									s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23)
											+ s.substring(24);
									gridfile.put("reid", s);
									gridfile.put("filename", renameS);
									gridfile.put("modelId", strp);
									gridfile.put("userId", 1);
									gridfile.put("aliases", "3DModel");
									gridfile.put("uploadDate", new Date());
									gridfile.put("uploadSystemDate", df.format(new Date()));
									gridfile.put("contentType", ".vrkb");
//									gridfile.put("contentType", ".zip");
									gridfile.put("contentSize", size);
									gridfile.save();


									// mysql
									Connection con = null;
									Statement st = null;
									try {
										con = MysqlJdbcManager.getInstance().getConnection();
										st = con.createStatement();
										String sql = "insert into model_file(Model_id,Collection,UUid,filename,compress_size) values("
												+ strp + ",'model" + df2.format(new Date()) + "','" + s + "','"
												+ renameS + "',"+size+")";
										st.executeUpdate(sql);
										st.close();
									} catch (SQLException ex) {
										logger.error(ex.getMessage());
									} finally {
										if (st != null){
											try {
												st.close();
											} catch (Exception ex) {
												ex.printStackTrace();
											}
										}
										if (con != null){
											try {
												con.close();
											} catch (Exception ex) {
												ex.printStackTrace();
											}
										}
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									logger.error(e.getMessage());
								}
								logger.info("系统记录mongoDB==" + file2.getName() + "上传结束时间====:" + df.format(new Date()));
							} else {
								logger.info("系统记录mongoDB==" + file2.getName() + "上传开始时间====:" + df.format(new Date()));
								try {
									// 根据文件创建文件的输入流
									in = new FileInputStream(file2);
									// 创建字节数组
									gridfile = gridFS.createFile(in);// 创建gridfs文件

									String s = UUID.randomUUID().toString();// 用来生成数据库的主键id非常不错。
									s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23)
											+ s.substring(24);

									gridfile.put("reid", s);
									gridfile.put("filename", file2.getName());
									gridfile.put("modelId", strp);
									gridfile.put("userId", 1);
									gridfile.put("aliases", "3DModel");
									gridfile.put("uploadDate", new Date());
									gridfile.put("uploadSystemDate", df.format(new Date()));
									gridfile.put("contentType", file2.getName().substring(pot));
									gridfile.save();

									// mysql
									Connection con = null;
									Statement st = null;
									try {
										con = MysqlJdbcManager.getInstance().getConnection();
										st = con.createStatement();
										String sql = "insert into model_file(Model_id,Collection,UUid,filename) values("
												+ strp + ",'model" + df2.format(new Date()) + "','" + s + "','"
												+ file2.getName() + "')";
										st.executeUpdate(sql);
										st.close();
									} catch (SQLException ex) {
										logger.error(ex.getMessage());
									} finally {
										if (st != null){
											try {
												st.close();
											} catch (Exception ex) {
												ex.printStackTrace();
											}
										}
										if (con != null){
											try {
												con.close();
											} catch (Exception ex) {
												ex.printStackTrace();
											}
										}
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
								logger.info("系统记录mongoDB==" + file2.getName() + "上传结束时间====:" + df.format(new Date()));
							}

						}
					}
				}
			} else {
				logger.error("系统需要上传的文件夹不存在！！");
			}
			logger.info("系统记录mongoDB上传任务已全部完成==完成时间:" + df.format(new Date()));
			return 1;
		}
		
	}


	/**
	 * 下载文件
	 */
	public static void mgdownloadFile() {
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMM");// 设置日期格式
		String gspath = "model" + df2.format(new Date());
//		String gspath = "model222";
		try {
			String fileId = "5926bc20b2c94817a7931a5893e47b0b";
			DB db = MongoManager.getInstance().getDB();
//			db.getReadPreference().secondaryPreferred();
			//在复制集中优先读secondary，如果secondary访问不了的时候就从master中读
			db.setReadPreference(ReadPreference.secondaryPreferred());
			//只从secondary中读，如果secondary访问不了的时候就不能进行查询
//			db.setReadPreference(ReadPreference.secondary());
			GridFS gridFS = new GridFS(db, gspath);
			DBObject dbs = new BasicDBObject();
			dbs.put("reid", fileId);
			GridFSDBFile gridFSDBFile = (GridFSDBFile) gridFS.findOne(dbs);
			FileOutputStream out2 = null;
			OutputStream os2 = null;
			String fileName = null;
			// String mogodbFilePath =
			// request.getServletContext().getRealPath("/upload/");
			String mogodbFilePath = "F:\\test";
			try {
				if (gridFSDBFile != null) {
					// 获取原文件名
					fileName = (String) gridFSDBFile.get("filename");
					if (StringUtils.isNotBlank(fileName)) {
						int lin = fileName.lastIndexOf(".");
						if (lin < 0) {
							fileName += gridFSDBFile.getContentType().lastIndexOf(".") < 0
									? "." + gridFSDBFile.getContentType() : gridFSDBFile.getContentType();
						}
					}
					// fileName = new String(name1.getBytes("GBK"),"ISO8859-1");
					// 将mogodb中的文件写到指定文件夹
					gridFSDBFile.writeTo(mogodbFilePath + "/" + fileName);
					out2 = new FileOutputStream(mogodbFilePath + "/" + fileName);
					os2 = new BufferedOutputStream(out2);
					gridFSDBFile.writeTo(os2);
				}
			} catch (Exception e) {

			} finally {
				os2.flush();
				os2.close();
				out2.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * mysql同步mongodb删除验
	 */
	public static void mongoRemoveMysql(String modelId){
		if(("").equalsIgnoreCase(modelId)){
			//第一步：删除MongoDB数据
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			SimpleDateFormat df2 = new SimpleDateFormat("yyyyMM");// 设置日期格式
			logger.info("mongodb删除任务开始============开始时间："+df.format(new Date())+"");
			DB db = MongoManager.getInstance().getDB();
			String gspath = "model" + df2.format(new Date());
			db.getCollection(""+gspath+".files").drop();
			db.getCollection(""+gspath+".chunks").drop();
			logger.info("mongodb删除任务结束============结束时间："+df.format(new Date())+"");
			//第二步：mysql删除开始
			Connection con = null;
			Statement st = null;
			logger.info("mysql删除任务开始============开始时间："+df.format(new Date())+"");
			try {
				con = MysqlJdbcManager.getInstance().getConnection();
				st = con.createStatement();
				String sqlDel = "delete from model_file where Collection = '"+gspath+"'";
				st.executeUpdate(sqlDel);
			} catch (SQLException ex) {
				logger.error(ex.getMessage());
			} finally {
				if (st != null){
					try {
						st.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				if (con != null){
					try {
						con.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			logger.info("mysql删除任务结束============结束时间："+df.format(new Date())+"");
		}else{
			//第一步：删除MongoDB数据
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			SimpleDateFormat df2 = new SimpleDateFormat("yyyyMM");// 设置日期格式
			String gspath = "model" + df2.format(new Date());
			logger.info("mongodb删除任务开始============开始时间："+df.format(new Date())+"");
			DB db = MongoManager.getInstance().getDB();
			GridFS gridFS = new GridFS(db, gspath);
			DBObject dbs = new BasicDBObject();
			dbs.put("modelId", modelId);
			gridFS.remove(dbs);
			logger.info("mongodb删除任务结束============结束时间："+df.format(new Date())+"");
			//第二步：mysql删除开始
			Connection con = null;
			Statement st = null;
			logger.info("mysql删除任务开始============开始时间："+df.format(new Date())+"");
			try {
				con = MysqlJdbcManager.getInstance().getConnection();
				st = con.createStatement();
				String sqlDel = "delete from model_file where Model_id = "+modelId+"";
				st.executeUpdate(sqlDel);
			} catch (SQLException ex) {
				logger.error(ex.getMessage());
			} finally {
				if (st != null){
					try {
						st.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				if (con != null){
					try {
						con.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			logger.info("mysql删除任务结束============结束时间："+df.format(new Date())+"");
		}
		
		
	}
	
	/**
	 * mysql数据进行校
	 */
	public static int checkMsql(String path){
		
		File folder = new File(path);
		String modelId = "";
		File[] files = folder.listFiles();
		// mysql校验开始
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		LinkedList<File> list = new LinkedList<File>();
		for(File file:files){
			list.add(file);
			if (file.isDirectory()) {
				modelId = file.getName().substring(file.getName().lastIndexOf("\\")+1, file.getName().length());
				if(StringUtils.isEmpty(modelId)){
					modelId = "0";
				}
				try {
					con = MysqlJdbcManager.getInstance().getConnection();
					st = con.createStatement();
					int count = 0;
					String sqlYz = "select count(*) from model_file where Model_id = "+modelId+"";
					rs = st.executeQuery(sqlYz);
					if(rs.next()) {
						count=rs.getInt(1);
						if(count>0){
							logger.info("mysql数据库中已经存在model为"+modelId+"的数据!!!");
						}
					}
					return 99;
				} catch (SQLException ex) {
					System.out.println(ex.toString());
				} finally {
					if (rs != null){
						try {
							rs.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					if (st != null){
						try {
							st.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					if (con != null){
						try {
							con.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}else{
				modelId = file.getParent().substring(file.getParent().lastIndexOf("\\")+1, file.getParent().length());
				if(StringUtils.isEmpty(modelId)){
					modelId = "0";
				}
				try {
					con = MysqlJdbcManager.getInstance().getConnection();
					st = con.createStatement();
					int count = 0;
					String sqlYz = "select count(*) from model_file where Model_id = "+modelId+"";
					rs = st.executeQuery(sqlYz);
					if(rs.next()) {
						count=rs.getInt(1);
						if(count>0){
							logger.info("mysql数据库中已经存在model为"+modelId+"的数据!!!");
						}
					}
					return 99;
				} catch (SQLException ex) {
					System.out.println(ex.toString());
				} finally {
					if (rs != null){
						try {
							rs.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					if (st != null){
						try {
							st.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					if (con != null){
						try {
							con.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
		return 90;
	}
	
  
    public static void removefile(String path)  
    {  
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		logger.info("删除指定文件任务开始============开始时间："+df.format(new Date())+"");
    	File folder = new File(path);
		File[] files = folder.listFiles();
		LinkedList<File> list = new LinkedList<File>();
		for(File file:files){
			list.add(file);
			if (file.isDirectory()) {
				File temp_file;
				temp_file = list.removeFirst();
				files = temp_file.listFiles();
				for (File file3 : files) {
					if(file3.getName().contains(".vrkb") == true){
						file3.delete();
					}
				}
			}else{
				if(file.getName().contains(".vrkb") == true){
					file.delete();
				}
			}
		}
		logger.info("删除指定文件结束============结束时间："+df.format(new Date())+"");
    }
    
    /**
     * 日志插入
     *
     * @param request
     * @param response
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void loginsert(Map<String, Object> params) {
        //记录时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        try {
            MongoDatabase database = MongoManager.getInstance().getMongoDatabase();
            MongoCollection mc = database.getCollection("log");
            Document d = new Document();
            d.append("op", params.get("op"));
            d.append("status", params.get("status"));
            d.append("msg", params.get("msg"));
            d.append("ip", BsonTool.objectToBsonValue(params.get("ip")));
            d.append("uploadSystemDate", df.format(new Date()));
            d.append("reid", params.get("reid"));
            mc.insertOne(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) {  
		mguploadfile();
//		removefile();
//		mgdownloadFile();
//		mongoRemoveMysql("");
//		checkMsql("");
	}
	
	
	

}
