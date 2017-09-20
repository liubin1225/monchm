package com.warchm.common.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.warchm.common.mongo.MongoManager;
import com.warchm.common.mysql.MysqlJdbcManager;
import com.warchm.modules.sys.entity.ModelFile;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class MongoDBUtils {

	private static Logger logger = Logger.getLogger(MongoDBUtils.class);

	/**
	 * 下载所有文件
	 * 
	 * @author Liubin
	 * @date 2017年5月9日 下午3:59:55
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void mgdownloadFiles(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String FilePath = request.getServletContext().getRealPath("/upload/");
		// 下载的文件的临时保存位置
		String mogodbFilePath = "";
		// 获取数据库（老版本mongoDB驱动写法）
		DB db = MongoManager.getInstance().getDB();
		MongoDatabase mongoDB = MongoManager.getInstance().getMongoDatabase();
		MongoIterable<String> Collections = mongoDB.listCollectionNames();
		int pot = 0;
		// 记录时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println("系统记录mongoDB下载开始时间====mongodb download begin:" + df.format(new Date()));// new
																												// Date()为获取当前系统时间

		for (String collectionName : Collections) {
			if (collectionName.indexOf(".chunks") != -1) {
				continue;
			}
			if (collectionName.indexOf(".profile") != -1) {
				continue;
			}
			if (collectionName.indexOf(".files") != -1) {
				pot = collectionName.lastIndexOf(".");
				collectionName = collectionName.substring(0, pot);
			}
			// String s = UUID.randomUUID().toString();//用来生成数据库的主键id非常不错。
			// mogodbFilePath = FilePath + "/" +collectionName+s;
			mogodbFilePath = FilePath + "/" + collectionName;
			File file2 = new File(mogodbFilePath);
			if (!file2.exists()) {
				file2.mkdir();
			}
//			GridFSBucket bucket = GridFSBuckets.create(mongoDB);
			GridFS gridFS = new GridFS(db, collectionName);
			try {
				List<GridFSDBFile> gridFSDBFileList = new ArrayList<GridFSDBFile>();
				// DBObject query=new BasicDBObject("userId", 1);
				// $or (查询id等于1或者id等于2的数据)
				BasicDBObject queryObject = new BasicDBObject();
				// queryObject.append("metadata.userId",1);
				queryObject.append("userId", 1);
				// queryObject.append(QueryOperators.AND, new BasicDBObject[] {
				// new BasicDBObject("reid",
				// "241050d6-2e6c-49a2-bf0a-cf3bee9216df"),
				// new BasicDBObject("filename", "草皮.zip") });
				gridFSDBFileList = gridFS.find(queryObject);

				FileOutputStream out2 = null;
				OutputStream os2 = null;
				String fileName = null;
				// 循环所有文件
				if (gridFSDBFileList != null && gridFSDBFileList.size() > 0) {
					for (GridFSDBFile nb : gridFSDBFileList) {
						// 获取原文件名
						fileName = (String) nb.get("filename");
						if (StringUtils.isNotBlank(fileName)) {
							int lin = fileName.lastIndexOf(".");
							if (lin < 0) {
								fileName += nb.getContentType().lastIndexOf(".") < 0 ? "." + nb.getContentType()
										: nb.getContentType();
							}
						}
						// fileName = new
						// String(name1.getBytes("GBK"),"ISO8859-1");
						// 将mogodb中的文件写到指定文件夹
						nb.writeTo(mogodbFilePath + "/" + fileName);
						out2 = new FileOutputStream(mogodbFilePath + "/" + fileName);
						os2 = new BufferedOutputStream(out2);
						nb.writeTo(os2);
						// bucket.downloadToStream(fileName, nb.get);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		System.out.println("系统记录mongoDB下载结束时间====mongodb download end:" + df.format(new Date()));// new
																											// Date()为获取当前系统时间
	}

	/**
	 * 上传
	 */
	public void mguploadfile() {

		// 记录时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMM");// 设置日期格式
		// String filepathv = "F:\\testpic\\212";
		String gspath = "model" + df2.format(new Date());
		DB db = MongoManager.getInstance().getDB();
		GridFS gridFS = null;
//		String filepathv = "F:\\testpic";
		String filepathv = "F:\\prod_obj";
		File file = new File(filepathv);
		if (file.exists()) {
			LinkedList<File> list = new LinkedList<File>();
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isDirectory()) {
					logger.info("系统记录mongoDB==" + file2.getName() + "上传开始时间====:" + df.format(new Date()));
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
									if (file3.getName().indexOf(".obj") != -1) {
										int num23 = file3.getAbsolutePath().lastIndexOf(".");
										int num231 = num23 + 1;
										String name23 = file3.getAbsolutePath().substring(0, num231);
										String zipname = name23 + "zip";
										try {
											ZipUtils.zip(zipname, new File(file3.getAbsolutePath()));
											String rename = name23 + "vrkb";
											// 在进行重命名
											File oldfile = new File(zipname);
											File newfile = new File(rename);
											// if (!oldfile.exists()) {
											// return;// 重命名文件不存在
											// }
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

											int pot = file3.getName().lastIndexOf(".");
											int pot2 = rename.lastIndexOf(".");
											if (file3.getName().substring(pot).equalsIgnoreCase(".zip")) {
												continue;
											}
											String s = UUID.randomUUID().toString();// 用来生成数据库的主键id非常不错。
											s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
													+ s.substring(19, 23) + s.substring(24);
											gridfile.put("reid", s);
											gridfile.put("filename", rename);
											gridfile.put("userId", 1);
											gridfile.put("aliases", "3DModel");
											gridfile.put("uploadDate", new Date());
											gridfile.put("uploadSystemDate", df.format(new Date()));
											gridfile.put("contentType", rename.substring(pot2));
											gridfile.save();

											// mysql
											Connection con = null;
											try {
												con = MysqlJdbcManager.getInstance().getConnection();
												Statement st = con.createStatement();
												String sql = "insert into model_file(Model_id,Collection,UUid,filename) values("
														+ strp + ",'model" + df2.format(new Date()) + "','" + s + "','"
														+ file3.getName() + "')";
												st.executeUpdate(sql);
												st.close();
											} catch (SQLException ex) {
												System.out.println(ex.toString());
											} finally {
												if (con != null)
													try {
														con.close();
													} catch (Exception ex) {
														ex.printStackTrace();
													}
											}
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									} else {
										try {
											// 根据文件创建文件的输入流
											in = new FileInputStream(file4);
											// 创建字节数组
											gridfile = gridFS.createFile(in);// 创建gridfs文件

											int pot = file3.getName().lastIndexOf(".");
											if (file3.getName().substring(pot).equalsIgnoreCase(".zip")) {
												continue;
											}
											String s = UUID.randomUUID().toString();// 用来生成数据库的主键id非常不错。
											s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
													+ s.substring(19, 23) + s.substring(24);
											gridfile.put("reid", s);
											gridfile.put("filename", file3.getName());
											gridfile.put("userId", 1);
											gridfile.put("aliases", "3DModel");
											gridfile.put("uploadDate", new Date());
											gridfile.put("uploadSystemDate", df.format(new Date()));
											gridfile.put("contentType", file3.getName().substring(pot));
											gridfile.save();

											// mysql
											Connection con = null;
											try {
												con = MysqlJdbcManager.getInstance().getConnection();
												Statement st = con.createStatement();
												String sql = "insert into model_file(Model_id,Collection,UUid,filename) values("
														+ strp + ",'model" + df2.format(new Date()) + "','" + s + "','"
														+ file3.getName() + "')";
												st.executeUpdate(sql);
												st.close();
											} catch (SQLException ex) {
												System.out.println(ex.toString());
											} finally {
												if (con != null)
													try {
														con.close();
													} catch (Exception ex) {
														ex.printStackTrace();
													}
											}
										} catch (Exception e) {

										}
									}
								}
							}
						}
					}
					logger.info("系统记录mongoDB==" + file2.getName() + "上传结束时间====:" + df.format(new Date()));
				} else {
					System.out.println("文件夹:" + file2.getParent());
					int num = file2.getParent().lastIndexOf("\\");
					int num2 = num + 1;
					String strp = file2.getParent().substring(num2, file2.getParent().length());
					// Date()为获取当前系统时间
					logger.info("系统记录mongoDB==" + file2.getName() + "上传开始时间====:" + df.format(new Date()));
					gridFS = new GridFS(db, gspath);
					GridFSFile gridfile = null;
					list.add(file2);
					if (file2 != null) {
						InputStream in = null;
						if (file2.getName().indexOf(".obj") != -1) {
							int num23 = file2.getAbsolutePath().lastIndexOf(".");
							int num231 = num23 + 1;
							String name23 = file2.getAbsolutePath().substring(0, num231);
							String zipname = name23 + "zip";
							try {
								ZipUtils.zip(zipname, new File(file2.getAbsolutePath()));
								String rename = name23 + "vrkb";
								// 在进行重命名
								File oldfile = new File(zipname);
								File newfile = new File(rename);
								// if (!oldfile.exists()) {
								// return;// 重命名文件不存在
								// }
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

								int pot = file2.getName().lastIndexOf(".");
								if (file2.getName().substring(pot).equalsIgnoreCase(".zip")) {
									continue;
								}
								int pot2 = rename.lastIndexOf(".");
								String s = UUID.randomUUID().toString();// 用来生成数据库的主键id非常不错。
								s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23)
										+ s.substring(24);
								gridfile.put("reid", s);
								gridfile.put("filename", rename);
								gridfile.put("userId", 1);
								gridfile.put("aliases", "3DModel");
								gridfile.put("uploadDate", new Date());
								gridfile.put("uploadSystemDate", df.format(new Date()));
								gridfile.put("contentType", rename.substring(pot2));
								gridfile.save();

								// mysql
								Connection con = null;
								try {
									con = MysqlJdbcManager.getInstance().getConnection();
									Statement st = con.createStatement();
									String sql = "insert into model_file(Model_id,Collection,UUid,filename) values("
											+ strp + ",'model" + df2.format(new Date()) + "','" + s + "','"
											+ file2.getName() + "')";
									st.executeUpdate(sql);
									st.close();
								} catch (SQLException ex) {
									System.out.println(ex.toString());
								} finally {
									if (con != null)
										try {
											con.close();
										} catch (Exception ex) {
											ex.printStackTrace();
										}
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							try {
								// 根据文件创建文件的输入流
								in = new FileInputStream(file2);
								// 创建字节数组
								gridfile = gridFS.createFile(in);// 创建gridfs文件

								int pot = file2.getName().lastIndexOf(".");
								if (file2.getName().substring(pot).equalsIgnoreCase(".zip")) {
									continue;
								}
								String s = UUID.randomUUID().toString();// 用来生成数据库的主键id非常不错。
								s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23)
										+ s.substring(24);

								gridfile.put("reid", s);
								gridfile.put("filename", file2.getName());
								gridfile.put("userId", 1);
								gridfile.put("aliases", "3DModel");
								gridfile.put("uploadDate", new Date());
								gridfile.put("uploadSystemDate", df.format(new Date()));
								gridfile.put("contentType", file2.getName().substring(pot));
								gridfile.save();

								// mysql
								Connection con = null;
								try {
									con = MysqlJdbcManager.getInstance().getConnection();
									Statement st = con.createStatement();
									String sql = "insert into model_file(Model_id,Collection,UUid,filename) values("
											+ strp + ",'model" + df2.format(new Date()) + "','" + s + "','"
											+ file2.getName() + "')";
									st.executeUpdate(sql);
									st.close();
								} catch (SQLException ex) {
									System.out.println(ex.toString());
								} finally {
									if (con != null)
										try {
											con.close();
										} catch (Exception ex) {
											ex.printStackTrace();
										}
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					}
					logger.info("系统记录mongoDB==" + file2.getName() + "上传结束时间====:" + df.format(new Date()));
				}
			}
		} else {
			System.out.println("文件不存在!");
		}
	}

	/**
	 * 下载单个文件
	 *
	 */
	public void mgdownloadFile(List<ModelFile> modelFileList) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println("下载开始时间----------"+df.format(new Date()));
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMM");// 设置日期格式
		String gspath = "model" + df2.format(new Date());
		String fileId;
		int ModelId;
		for(ModelFile temp : modelFileList){
			fileId = temp.getUUid();
			ModelId = temp.getModel_id();
			try {
				DB db = MongoManager.getInstance().getDB();
//				db.getReadPreference().secondaryPreferred();
				//在复制集中优先读secondary，如果secondary访问不了的时候就从master中读
//				db.setReadPreference(ReadPreference.secondaryPreferred());
				//只从secondary中读，如果secondary访问不了的时候就不能进行查询
//				db.setReadPreference(ReadPreference.secondary());
				GridFS gridFS = new GridFS(db, gspath);
				DBObject dbs = new BasicDBObject();
				dbs.put("reid", fileId);
				GridFSDBFile gridFSDBFile = (GridFSDBFile) gridFS.findOne(dbs);
				FileOutputStream out2 = null;
				OutputStream os2 = null;
				String fileName = null;
				// String mogodbFilePath =
				// request.getServletContext().getRealPath("/upload/");

				String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	            String mogodbFilePath = path.substring(1, path.indexOf("/WEB-INF/classes/"))+"/cache/"+ModelId;
				File file=new File(mogodbFilePath);
				if(!file.mkdir()){
					file.mkdir();
				}
				System.out.println(mogodbFilePath);
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
						/*if(fileName.contains(".zip")){
							fileName = fileName.replaceAll(".zip", ".vrkb");
							gridFSDBFile.writeTo(mogodbFilePath + "/" + fileName);
						}
						else*/
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
		System.out.println("下载结束时间----------"+df.format(new Date()));
	}
}
