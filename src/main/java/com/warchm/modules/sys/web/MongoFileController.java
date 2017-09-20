package com.warchm.modules.sys.web;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryOperators;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

import net.sf.json.JSONArray;

import com.warchm.common.idGen.IdGen;
import com.warchm.common.mongo.MongoManager;
import com.warchm.common.mongo.MongoUtils;
import com.warchm.common.utils.AddressUtils;
import com.warchm.common.utils.Constants;
import com.warchm.common.utils.JsonResult;
import com.warchm.common.utils.StringUtils;
import com.warchm.common.utils.Zip4JUtils;
import com.warchm.common.web.BaseController;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 文件模块controller
 * @author liubin
 * @version 2017年7月20日 上午11:17:54
 */
@Controller
@RequestMapping(value = "/mongoFile")
public class MongoFileController extends BaseController{

    private transient final Logger log = Logger.getLogger(getClass());
    
    /**
     * mongodb列表展示
     *
     * @param model
     * @param request
     * @return
     * @author LiuBin
     * @date 2017年5月8日 下午4:44:20
     */
    @RequestMapping(value = "/mongoFileList")
    @ResponseBody
    public JsonResult mongoFileList(ModelAndView model, HttpServletRequest request) {
//        int fromIndex = 0;
//        int toIndex = 10;
        JsonResult jsonResult = new JsonResult();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> map = null;
        List<GridFSDBFile> gridFSFileList = null;
        List<Object> list = new ArrayList<>();
        String limit = request.getParameter("limit");
        String page = request.getParameter("page");
        String demoName = request.getParameter("name");
        String filesearch = request.getParameter("filesearch");
        int pageNum = 0;
        int limitNum = 0;
        if(StringUtils.isEmpty(page)) {
        	page = "1";
        }
        if(StringUtils.isEmpty(limit)) {
        	limit = "10";
        }
        pageNum = Integer.parseInt(page);
        limitNum = Integer.parseInt(limit);
        int count = 0;
        
    	try {
            DB db = MongoManager.getInstance().getDB();//默认测试数据库mongotest
            GridFS gridFS = new GridFS(db, MongoManager.gridFSstr);
            // $or (查询id等于1或者id等于2的数据)
//	        Map<String, Object> map = new HashMap<String, Object>();
//	        map.put("metadata.userId", 1);
            BasicDBObject queryObject = new BasicDBObject();
	        queryObject.append("metadata.userId",1);
	        if(!StringUtils.isEmpty(demoName)) {
	        	queryObject.append("metadata.modelId",demoName);
	        }
	        if(!StringUtils.isEmpty(filesearch)) {
	        	Pattern pattern = Pattern.compile("^.*"+filesearch+".*$", Pattern.CASE_INSENSITIVE);
	        	queryObject.append("filename",pattern);
	        }
//            queryObject.append("userId", 1);
            //
            //获取单个文件
//	        GridFSFindIterable gridFSFindIterable = bucket.find(Filters.eq("_id", new ObjectId(objectId)));
//	        MongoDatabase mongoDatabase = MongoManager.getInstance().getMongoDatabase(); 
//	        GridFSBucket gridFSBucket = GridFSBuckets.create(mongoDatabase, gridFSstr);
//	        List<com.mongodb.client.gridfs.model.GridFSFile> gridFSFileList = new ArrayList<com.mongodb.client.gridfs.model.GridFSFile>();
//	        MongoCursor<com.mongodb.client.gridfs.model.GridFSFile> mongoCursor = gridFSBucket.find(queryObject).iterator();
	        count = gridFS.find(queryObject).size();
	        if(limitNum*pageNum > count) {
	        	gridFSFileList = gridFS.find(queryObject).subList(limitNum*(pageNum-1), count);
            }else {
            	gridFSFileList = gridFS.find(queryObject).subList(limitNum*(pageNum-1), limitNum*pageNum);
            }
	        
            
//	        while(mongoCursor.hasNext()){  
//	        	gridFSFileList.add(mongoCursor.next());
//		    } 
//	        System.out.println(gridFSFileList.toString());
            /**
             * 1. 获取迭代器FindIterable<Document>
             * 2. 获取游标MongoCursor<Document>
             * 3. 通过游标遍历检索出的文档集合
             * */
            //使用group 查询每年（year）中age最大的人    mongodb可以使用脚本
//	        BasicDBObject key = new BasicDBObject("year",true);     
//	        BasicDBObject cond = new BasicDBObject("id",new BasicDBObject(QueryOperators.GT,0));     
//	        BasicDBObject initial = new BasicDBObject("age",0);     
//	        String reduce = "function (doc,prev){"  
//	                            +"if(doc.age>prev.age){"  
//	                                +"prev.age=doc.age;"  
//	                                +"prev.name=doc.name"  
//	                            +"}"  
//	                        +"}";  
//	        BasicDBList group =  (BasicDBList) userCollection.group(key,cond,initial,reduce); 
            for (GridFSDBFile gfs : gridFSFileList) {
            	map = new HashMap<>();
//            	List<Map<String, Object>> list = new ArrayList<>();
            	map.put("objectid", String.valueOf(gfs.getId()));
            	map.put("id", gfs.getMetaData().get("reid"));
            	map.put("modelId", gfs.getMetaData().get("modelId"));
            	map.put("filename", gfs.getFilename());
            	map.put("contentType", gfs.getMetaData().get("contentType"));
            	map.put("uploadSystemDate", gfs.getMetaData().get("uploadSystemDate"));
            	map.put("length", gfs.getLength());
            	list.add(map);
			}
            //插入日志
            params.put("op", Constants.opscan);
            params.put("status", Constants.STATUS_SUCCESS);
            params.put("msg", Constants.msgSuccess);
            params.put("ip", AddressUtils.ipUtils());
            params.put("reid", IdGen.uuid());
            MongoUtils.loginsert(params);
        } catch (Exception e) {
            e.printStackTrace();
            params.put("op", "文件浏览");
            params.put("status", Constants.STATUS_ERROR);
            params.put("msg", e.getMessage());
            params.put("reid", IdGen.uuid());
            try {
				params.put("ip", AddressUtils.ipUtils());
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				params.put("msg", e1.getMessage());
			}
            MongoUtils.loginsert(params);
        }
    	
    	jsonResult.setCode(0);
    	jsonResult.setMsg("");
    	jsonResult.setCount(count);
    	if(list.size() != 0) {
    		jsonResult.setData(list);
    	}else {
    		jsonResult.setData(new JSONArray());
    	}
        return jsonResult;
    }
    
    /**
     * 文件上传
     *
     * @param request
     * @param response
     */
	@RequestMapping(value = "/upload")
	@ResponseBody
    private JsonResult upload(HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
        //记录时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("系统记录mongoDB上传开始时间====:" + df.format(new Date()));// new Date()为获取当前系统时间
        try {
            MongoDatabase database = MongoManager.getInstance().getMongoDatabase();
            GridFSBucket gridFSBucket = GridFSBuckets.create(database, MongoManager.gridFSstr);
            //创建一个通用的多部分解析器
            MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            GridFSUploadOptions options = null;
            if (resolver.isMultipart(request)) {
                //转换成多部分request
                MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
                Map<String, MultipartFile> fileMap = mRequest.getFileMap();
                Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, MultipartFile> entry = it.next();
                    MultipartFile mFile = entry.getValue();//获取文件
                    String filename = mFile.getOriginalFilename();
//					filename = new String(filename.getBytes("iso8859-1"),"utf-8");
                    
                    /** 同步mysql
					Map map = new HashMap<>();
                    map.put("Model_id", 11);
                    map.put("Collection", "model");
                    map.put("UUid", s);
                    mongoservice.insert(map);
                    */
                    options = new GridFSUploadOptions();
                    int pot = filename.lastIndexOf(".");
                    Document d = new Document();
//                    file.put("reid", s);
//                    file.put("filename", filename);
//                    file.put("userId", 1);
//                    file.put("aliases", "3DModel");
//                    file.put("uploadDate", new Date());
//                    file.put("uploadSystemDate", df.format(new Date()));
//                    file.put("contentType", filename.substring(pot));
//                    file.save();
                    d.append("reid", IdGen.uuid());
                    d.append("contentType", filename.substring(pot));
                    d.append("aliases", "3DModel");
                    d.append("userId", 1);
                    d.append("uploadSystemDate", df.format(new Date()));
                    options.metadata(d);
                    gridFSBucket.uploadFromStream(filename, mFile.getInputStream(), options);
                }
            }
            
            //插入日志
            Map<String, Object> params = new HashMap<>();
            params.put("op", Constants.opupload);
            params.put("status", Constants.STATUS_SUCCESS);
            params.put("msg", Constants.msgSuccess);
            params.put("ip", AddressUtils.ipUtils());
            params.put("reid", IdGen.uuid());
            MongoUtils.loginsert(params);
            System.out.println("系统记录mongoDB上传结束时间:" + df.format(new Date()));// new Date()为获取当前系统时间
        } catch (Exception e) {
        	//插入日志
            Map<String, Object> params = new HashMap<>();
            params.put("op", Constants.opupload);
            params.put("status", Constants.STATUS_ERROR);
            params.put("msg", e.getMessage());
            params.put("reid", IdGen.uuid());
            try {
				params.put("ip", AddressUtils.ipUtils());
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				params.put("msg", e1.getMessage());
			}
            MongoUtils.loginsert(params);
            jsonResult.setCode(1);
        }
        jsonResult.setCode(0);
        return jsonResult;
    }
	
	
    /**
     * 文件删除
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/deleteFile")
    @ResponseBody
    private JsonResult deleteFile(HttpServletRequest request, HttpServletResponse response) {
    	JsonResult jsonResult = new JsonResult();
        try {
            String fileId = (String) request.getParameter("reid");
            MongoDatabase database = MongoManager.getInstance().getMongoDatabase();
//			GridFS gridFS= new GridFS(db,gridFSstr);
            GridFSBucket bucket = GridFSBuckets.create(database,MongoManager.gridFSstr);

//			BsonObjectId objId = new BsonObjectId().asObjectId();
//			bucket.delete(BsonValue.class.cast(fileId));
            ObjectId objectId = new ObjectId(fileId);
            bucket.delete(objectId);
//			gridFS.remove(objId);
        } catch (Exception e) {
        	jsonResult.setCode(1);
            e.printStackTrace();
            return jsonResult;
        }
        jsonResult.setCode(0);
        return jsonResult;
    }

    /**
     * 查看单个文件、下载文件
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/downloadFile")
    @ResponseBody
    private JsonResult downloadFile(HttpServletRequest request, HttpServletResponse response) {
    	JsonResult jsonresult = new JsonResult();
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("系统记录mongoDB==下载开始时间====:" + df.format(new Date()));// new Date()为获取当前系统时间
        try {
            String fileId = request.getParameter("reid");
//            String fileId = "94ef3490bdc6432e943dd74175dd8ef8";
            DB db = MongoManager.getInstance().getDB();
            GridFS gridFS = new GridFS(db, MongoManager.gridFSstr);
//			ObjectId objId = new ObjectId(fileId);
            DBObject query = new BasicDBObject();
            query.put("metadata.reid", fileId);
//            query.put("uploadSystemDate", "2017-06-28 16:08:50");
            GridFSDBFile gridFSDBFile = (GridFSDBFile) gridFS.findOne(query);
            FileOutputStream out2 = null;
            OutputStream os2 = null;
            String fileName = null;
//            String mogodbFilePath = request.getServletContext().getRealPath("/down/");
            String mogodbFilePath = "D://mongofiles";//默认磁盘地址
            File file2 = new File(mogodbFilePath);
            if (!file2.exists()) {
                file2.mkdir();
            }
            try {
                if (gridFSDBFile != null) {
                    // 获取原文件名
                    fileName = (String) gridFSDBFile.get("filename");
                    if (StringUtils.isNotBlank(fileName)) {
                        int lin = fileName.lastIndexOf(".");
                        if (lin < 0) {
                            fileName += gridFSDBFile.getContentType().lastIndexOf(".") < 0 ? "."
                                    + gridFSDBFile.getContentType()
                                    : gridFSDBFile.getContentType();
                        }
                    }
                    // fileName = new String(name1.getBytes("GBK"),"ISO8859-1");
                    // 将mogodb中的文件写到指定文件夹
                    //gridFSDBFile.writeTo(mogodbFilePath + "/" + fileName);
                    out2 = new FileOutputStream(mogodbFilePath + "/" + fileName);
                    os2 = new BufferedOutputStream(out2);
                    gridFSDBFile.writeTo(os2);
                }else {
                	jsonresult.setCode(0);
                	return jsonresult;
                }
            } catch (Exception e) {
            	log.error(e.getMessage());
            	jsonresult.setCode(0);
            	return jsonresult;
            } finally {
                os2.flush();
                os2.close();
                out2.close();
            }

        } catch (Exception e) {
        	log.error(e.getMessage());
            jsonresult.setCode(0);
            return jsonresult;
        }
        System.out.println("系统记录mongoDB==下载结束时间====:" + df.format(new Date()));// new Date()为获取当前系统时间
        jsonresult.setCode(1);
        return jsonresult;
    }
    
    
    /**
     * 根据文件夹路径进行上传
     * @return
     */
    @SuppressWarnings("unused")
	@RequestMapping(value = "/mongoFileFolder")
    @ResponseBody
	public JsonResult mongoFileFolder(HttpServletRequest request, HttpServletResponse response) {
    	JsonResult jsonResult = new JsonResult();
		// 记录时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMM");// 设置日期格式
//		GridFS gridFS = null;
		//String filepathv = "F:\\prod_obj";
		String filepathv = "";
//		filepathv = "F:\\testpic";
		filepathv = request.getParameter("filepathv");
		if(StringUtils.isEmpty(filepathv)) {
			filepathv = "";
		}
		//清空符合条件的文件
		MongoUtils.removefile(filepathv);
		
		String gspath = "model" + df2.format(new Date());
		MongoDatabase md = MongoManager.getInstance().getMongoDatabase();
        GridFSUploadOptions options = null;
        GridFSBucket gridFSBucket = GridFSBuckets.create(md, gspath);
		File file = new File(filepathv);
		if (file.exists()) {
			LinkedList<File> list = new LinkedList<File>();
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isDirectory()) {
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
									String renameS = "";
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
										log.info("系统记录mongoDB==" + file3.getName() + "上传开始时间====:" + df.format(new Date()));
										int num23 = file3.getAbsolutePath().lastIndexOf(".");
										int num231 = num23 + 1;
										String name23 = file3.getAbsolutePath().substring(0, num231);
										String zipname = name23 + "zip";
										try {
//												ZipUtils.zip(zipname, new File(file3.getAbsolutePath()));
											long size = Zip4JUtils.zip(file3.getAbsolutePath(), zipname, false, "");
//												String cmdur = ""+cmdNStr+" \""+zipname+"\" \""+file3.getAbsolutePath()+"\"";
//												System.out.println("压缩执行中..."+cmdur);
//												ZipUtils.zipU(cmdur);
											String rename = name23 + "vrkb";
											// 在进行重命名
											File oldfile = new File(zipname);
											File newfile = new File(rename);
											if (!oldfile.exists()) {
												jsonResult.setCode(2);
												return jsonResult;// 重命名文件不存在
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

//												String renameS = zipname.substring(zipname.lastIndexOf("\\")+1, zipname.length());
//												if(StringUtils.isEmpty(renameS)){
//													renameS = zipname;
//												}

											renameS = rename.substring(rename.lastIndexOf("\\")+1, rename.length());
											if(StringUtils.isEmpty(renameS)){
												renameS = rename;
											}

											String s = IdGen.uuid();
											options = new GridFSUploadOptions();
						                    Document d = new Document();
						                    d.append("reid", s);
						                    d.append("contentType", renameS.substring(pot));
						                    d.append("aliases", "3DModel");
						                    d.append("modelId", strp);
						                    d.append("userId", 1);
						                    d.append("uploadSystemDate", df.format(new Date()));
						                    options.metadata(d);
						                    gridFSBucket.uploadFromStream(renameS, in, options);

											// mysql
//											Connection con = null;
//											Statement st = null;
//											try {
//												con = MysqlJdbcManager.getInstance().getConnection();
//												st = con.createStatement();
//												String sql = "insert into model_file(Model_id,Collection,UUid,filename,compress_size) values("
//																+ strp + ",'model" + df2.format(new Date()) + "','" + s + "','"
//																+ renameS + "',"+size+")";
//												st.executeUpdate(sql);
//												st.close();
//											} catch (SQLException ex) {
//												log.error(ex.getMessage());
//											} finally {
//												if (st != null){
//													try {
//														st.close();
//													} catch (Exception ex) {
//														ex.printStackTrace();
//													}
//												}
//												if (con != null){
//													try {
//														con.close();
//													} catch (Exception ex) {
//														ex.printStackTrace();
//													}
//												}
//											}
										} catch (Exception e) {
											// TODO Auto-generated catch block
											log.error(e.getMessage());
										}
										log.info("系统记录mongoDB==" + file3.getName() + "上传结束时间====:" + df.format(new Date()));
									} else {
										log.info("系统记录mongoDB==" + file3.getName() + "上传开始时间====:" + df.format(new Date()));
										try {
											// 根据文件创建文件的输入流
											in = new FileInputStream(file4);
										
											String s = IdGen.uuid();
											options = new GridFSUploadOptions();
						                    Document d = new Document();
						                    d.append("reid", s);
						                    d.append("contentType", file3.getName().substring(pot));
						                    d.append("aliases", "3DModel");
						                    d.append("modelId", strp);
						                    d.append("userId", 1);
						                    d.append("uploadSystemDate", df.format(new Date()));
						                    options.metadata(d);
						                    gridFSBucket.uploadFromStream(file3.getName(), in, options);

											// mysql
//											Connection con = null;
//											Statement st = null;
//											try {
//												con = MysqlJdbcManager.getInstance().getConnection();
//												st = con.createStatement();
//												String sql = "insert into model_file(Model_id,Collection,UUid,filename) values("
//														+ strp + ",'model" + df2.format(new Date()) + "','" + s + "','"
//														+ file3.getName() + "')";
//												st.executeUpdate(sql);
//												st.close();
//											} catch (SQLException ex) {
//												System.out.println(ex.toString());
//											} finally {
//												if (st != null){
//													try {
//														st.close();
//													} catch (Exception ex) {
//														ex.printStackTrace();
//													}
//												}
//												if (con != null){
//													try {
//														con.close();
//													} catch (Exception ex) {
//														ex.printStackTrace();
//													}
//												}
//											}
										} catch (Exception e) {
											log.error(e.getMessage());
										}
										log.info("系统记录mongoDB==" + file2.getName() + "上传结束时间====:" + df.format(new Date()));
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
							log.info("系统记录mongoDB==" + file2.getName() + "上传开始时间====:" + df.format(new Date()));
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
									jsonResult.setCode(2);
									return jsonResult;// 重命名文件不存在
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

								String renameS = rename.substring(rename.lastIndexOf("\\")+1, rename.length());
								if(StringUtils.isEmpty(renameS)){
									renameS = rename;
								}

//									String renameS = zipname.substring(zipname.lastIndexOf("\\")+1, zipname.length());
//									if(StringUtils.isEmpty(renameS)){
//										renameS = zipname;
//									}
								String s = IdGen.uuid();
								options = new GridFSUploadOptions();
			                    Document d = new Document();
			                    d.append("reid", s);
			                    d.append("contentType", renameS.substring(pot));
			                    d.append("aliases", "3DModel");
			                    d.append("modelId", strp);
			                    d.append("userId", 1);
			                    d.append("uploadSystemDate", df.format(new Date()));
			                    options.metadata(d);
			                    gridFSBucket.uploadFromStream(renameS, in, options);


								// mysql
//								Connection con = null;
//								Statement st = null;
//								try {
//									con = MysqlJdbcManager.getInstance().getConnection();
//									st = con.createStatement();
//									String sql = "insert into model_file(Model_id,Collection,UUid,filename,compress_size) values("
//											+ strp + ",'model" + df2.format(new Date()) + "','" + s + "','"
//											+ renameS + "',"+size+")";
//									st.executeUpdate(sql);
//									st.close();
//								} catch (SQLException ex) {
//									log.error(ex.getMessage());
//								} finally {
//									if (st != null){
//										try {
//											st.close();
//										} catch (Exception ex) {
//											ex.printStackTrace();
//										}
//									}
//									if (con != null){
//										try {
//											con.close();
//										} catch (Exception ex) {
//											ex.printStackTrace();
//										}
//									}
//								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								log.error(e.getMessage());
							}
							log.info("系统记录mongoDB==" + file2.getName() + "上传结束时间====:" + df.format(new Date()));
						} else {
							log.info("系统记录mongoDB==" + file2.getName() + "上传开始时间====:" + df.format(new Date()));
							try {
								// 根据文件创建文件的输入流
								in = new FileInputStream(file2);
								// 创建字节数组

								String s = IdGen.uuid();
								options = new GridFSUploadOptions();
			                    Document d = new Document();
			                    d.append("reid", s);
			                    d.append("contentType", file2.getName().substring(pot));
			                    d.append("aliases", "3DModel");
			                    d.append("modelId", strp);
			                    d.append("userId", 1);
			                    d.append("uploadSystemDate", df.format(new Date()));
			                    options.metadata(d);
			                    gridFSBucket.uploadFromStream(file2.getName(), in, options);

								// mysql
//								Connection con = null;
//								Statement st = null;
//								try {
//									con = MysqlJdbcManager.getInstance().getConnection();
//									st = con.createStatement();
//									String sql = "insert into model_file(Model_id,Collection,UUid,filename) values("
//											+ strp + ",'model" + df2.format(new Date()) + "','" + s + "','"
//											+ file2.getName() + "')";
//									st.executeUpdate(sql);
//									st.close();
//								} catch (SQLException ex) {
//									log.error(ex.getMessage());
//								} finally {
//									if (st != null){
//										try {
//											st.close();
//										} catch (Exception ex) {
//											ex.printStackTrace();
//										}
//									}
//									if (con != null){
//										try {
//											con.close();
//										} catch (Exception ex) {
//											ex.printStackTrace();
//										}
//									}
//								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							log.info("系统记录mongoDB==" + file2.getName() + "上传结束时间====:" + df.format(new Date()));
						}

					}
				}
			}
		} else {
			log.error("系统需要上传的文件夹不存在！！");
		}
		log.info("系统记录mongoDB上传任务已全部完成==完成时间:" + df.format(new Date()));
		jsonResult.setCode(0);
		return jsonResult;
		
	}
    
    /**
     * 浏览链接下所有的数据库名称
     *
     * @param request
     * @param response
     */
    @SuppressWarnings({ "deprecation" })
	@RequestMapping(value = "/scanDatabases")
    @ResponseBody
    private JsonResult scanDatabases(HttpServletRequest request, HttpServletResponse response) {
    	JsonResult jsonResult = new JsonResult();
        try {
        	MongoClient mc = MongoManager.getInstance().getMongoClient();
        	List<String> list2 = mc.getDatabaseNames();
        	Map<String, Object> params = null;
        	List<Object> listData = new ArrayList<>();
        	List<Object> listData2 = new ArrayList<>();
        	
        	for (int i = 0; i < list2.size(); i++) {
        		if("local".equals(list2.get(i)) || "admin".equals(list2.get(i))) {
        			continue;
        		}
        		params = new HashMap<>();
        		params.put("id", list2.get(i));
        		params.put("name", list2.get(i));
        		params.put("children", getCollectionNames(list2.get(i)));
        		listData.add(params);
			}
        	
        	Map<String, Object> m = new HashMap<>();
        	m.put("id", 1);
        	m.put("name", "数据库名称");
        	m.put("alias", "changyong");
        	m.put("children",listData);
        	listData2.add(m);
        	jsonResult.setCode(0);
        	jsonResult.setData(listData2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
    
    /**
     * 获取collectionlist
     * @param db
     * @return
     */
    @SuppressWarnings("rawtypes")
	public List getCollectionNames(String db) {
    	MongoClient mc = MongoManager.getInstance().getMongoClient();
    	MongoDatabase md = mc.getDatabase(db);
    	List<Object> list = new ArrayList<>();
    	Map<String, Object> params = null;
    	for (String collectionName : md.listCollectionNames()) {
    		params = new HashMap<>();
    		if (collectionName.indexOf(".chunks") != -1) {
                continue;
            }
            if (collectionName.indexOf(".profile") != -1) {
                continue;
            }
            if (collectionName.indexOf(".files") != -1) {
                int pot = collectionName.lastIndexOf(".");
                collectionName = collectionName.substring(0, pot);
            }
    		params.put("id", collectionName);
    		params.put("name", collectionName);
    		
    		list.add(params);
		}
    	return list;
    }
    
    /**
     * mongodb列表展示
     *
     * @param model
     * @param request
     * @return
     * @author LiuBin
     * @date 2017年5月8日 下午4:44:20
     */
    @RequestMapping(value = "/mongodbTreeList")
    @ResponseBody
    public JsonResult mongodbTreeList(ModelAndView model, HttpServletRequest request) {
//        int fromIndex = 0;
//        int toIndex = 10;
        JsonResult jsonResult = new JsonResult();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> map = null;
        List<GridFSDBFile> gridFSFileList = null;
        List<Object> list = new ArrayList<>();
        String limit = request.getParameter("limit");
        String page = request.getParameter("page");
        String demoName = request.getParameter("id");
        String collection = request.getParameter("pid");
        int pageNum = 0;
        int limitNum = 0;
        if(StringUtils.isEmpty(page)) {
        	page = "1";
        }
        if(StringUtils.isEmpty(limit)) {
        	limit = "10";
        }
        pageNum = Integer.parseInt(page);
        limitNum = Integer.parseInt(limit);
        int count = 0;
        
    	try {
            DB db = MongoManager.getInstance().getDB();//默认测试数据库mongotest
            GridFS gridFS = new GridFS(db, collection);
            // $or (查询id等于1或者id等于2的数据)
//	        Map<String, Object> map = new HashMap<String, Object>();
//	        map.put("metadata.userId", 1);
            BasicDBObject queryObject = new BasicDBObject();
	        queryObject.append("metadata.userId",1);
	        if(!StringUtils.isEmpty(demoName)) {
	        	queryObject.append("metadata.modelId",demoName);
	        }
//            queryObject.append("userId", 1);
            //
            //获取单个文件
//	        GridFSFindIterable gridFSFindIterable = bucket.find(Filters.eq("_id", new ObjectId(objectId)));
//	        MongoDatabase mongoDatabase = MongoManager.getInstance().getMongoDatabase(); 
//	        GridFSBucket gridFSBucket = GridFSBuckets.create(mongoDatabase, gridFSstr);
//	        List<com.mongodb.client.gridfs.model.GridFSFile> gridFSFileList = new ArrayList<com.mongodb.client.gridfs.model.GridFSFile>();
//	        MongoCursor<com.mongodb.client.gridfs.model.GridFSFile> mongoCursor = gridFSBucket.find(queryObject).iterator();
	        count = gridFS.find(queryObject).size();
	        if(limitNum*pageNum > count) {
	        	gridFSFileList = gridFS.find(queryObject).subList(limitNum*(pageNum-1), count);
            }else {
            	gridFSFileList = gridFS.find(queryObject).subList(limitNum*(pageNum-1), limitNum*pageNum);
            }
	        
            
//	        while(mongoCursor.hasNext()){  
//	        	gridFSFileList.add(mongoCursor.next());
//		    } 
//	        System.out.println(gridFSFileList.toString());
            /**
             * 1. 获取迭代器FindIterable<Document>
             * 2. 获取游标MongoCursor<Document>
             * 3. 通过游标遍历检索出的文档集合
             * */
            //使用group 查询每年（year）中age最大的人    mongodb可以使用脚本
//	        BasicDBObject key = new BasicDBObject("year",true);     
//	        BasicDBObject cond = new BasicDBObject("id",new BasicDBObject(QueryOperators.GT,0));     
//	        BasicDBObject initial = new BasicDBObject("age",0);     
//	        String reduce = "function (doc,prev){"  
//	                            +"if(doc.age>prev.age){"  
//	                                +"prev.age=doc.age;"  
//	                                +"prev.name=doc.name"  
//	                            +"}"  
//	                        +"}";  
//	        BasicDBList group =  (BasicDBList) userCollection.group(key,cond,initial,reduce); 
            for (GridFSDBFile gfs : gridFSFileList) {
            	map = new HashMap<>();
//            	List<Map<String, Object>> list = new ArrayList<>();
            	map.put("id", gfs.getMetaData().get("reid"));
            	map.put("filename", gfs.getFilename());
            	map.put("contentType", gfs.getMetaData().get("contentType"));
            	map.put("uploadSystemDate", gfs.getMetaData().get("uploadSystemDate"));
            	map.put("length", gfs.getLength());
            	list.add(map);
			}
            //插入日志
            params.put("op", Constants.opscan);
            params.put("status", Constants.STATUS_SUCCESS);
            params.put("msg", Constants.msgSuccess);
            params.put("ip", AddressUtils.ipUtils());
            params.put("reid", IdGen.uuid());
            MongoUtils.loginsert(params);
        } catch (Exception e) {
            e.printStackTrace();
            params.put("op", "文件浏览");
            params.put("status", Constants.STATUS_ERROR);
            params.put("msg", e.getMessage());
            params.put("reid", IdGen.uuid());
            try {
				params.put("ip", AddressUtils.ipUtils());
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				params.put("msg", e1.getMessage());
			}
            MongoUtils.loginsert(params);
        }
    	jsonResult.setCode(0);
    	jsonResult.setMsg("");
    	jsonResult.setCount(count);
    	jsonResult.setData(list);
        return jsonResult;
    }
    /**
     * mongo日志管理
     *
     * @param request
     * @param response
     */
	@RequestMapping(value = "/scanLog")
    @ResponseBody
    private JsonResult scanLog(HttpServletRequest request, HttpServletResponse response) {
    	JsonResult jsonResult = new JsonResult();
    	String reid = "";
        try {
        	MongoDatabase md = MongoManager.getInstance().getMongoDatabase();
        	MongoCollection<Document> mc = md.getCollection("log");
        	List<Document> list = new ArrayList<>();
        	String limit = request.getParameter("limit");
            String page = request.getParameter("page");
            int pageNum = 0;
            int limitNum = 0;
            if(StringUtils.isEmpty(page)) {
            	page = "1";
            }
            if(StringUtils.isEmpty(limit)) {
            	limit = "10";
            }
            pageNum = Integer.parseInt(page);
            limitNum = Integer.parseInt(limit);
            int count = (int) mc.count();
            String reidq = request.getParameter("reid");
            if(StringUtils.isEmpty(reidq)) {
            	reidq = "65f8bf471b85445ab499f4bd00b2bf6c";
            }
//            BasicDBObject bdb = new BasicDBObject();
//            (int) mc.count(bdb);
        	MongoCursor<Document> cursor = (MongoCursor<Document>) mc.find().skip((pageNum - 1) * 10).sort(new BasicDBObject()).limit(limitNum).iterator();
//            MongoCursor<Document> cursor = null;
//            if (pageNum == 1) {  
//        		cursor = (MongoCursor<Document>) mc.find().limit(limitNum).iterator();  
//            } else {  
//            	cursor = (MongoCursor<Document>) mc.find(new BasicDBObject("reid", new BasicDBObject(QueryOperators.GT, reidq))).limit(limitNum).iterator();  
//            }
        	while (cursor.hasNext()) {
        		list.add(cursor.next());
			}
        	
        	for(int i = 0;i < list.size();i++) {
        		reid = (String) list.get(list.size()-1).get("reid");
        	}
        	jsonResult.setData(list);
        	jsonResult.setCount(count);
        	jsonResult.setCode(0);
        	jsonResult.setMsg(reid);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setCode(1);
            jsonResult.setMsg(reid);
        }
        return jsonResult;
    }


    /**
     * 查看单个文件
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/scanloadFile")
    private void scanloadFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            String fileId = request.getParameter("id");
            DB db = MongoManager.getInstance().getDB();
            GridFS gridFS = new GridFS(db, MongoManager.gridFSstr);
            ObjectId objId = new ObjectId(fileId);
            GridFSDBFile gridFSDBFile = (GridFSDBFile) gridFS.findOne(objId);
            OutputStream os2 = null;
            String fileName = null;
            try {
                if (gridFSDBFile != null) {
                    // 获取原文件名
                    fileName = (String) gridFSDBFile.get("filename");
                    if (StringUtils.isNotBlank(fileName)) {
                        int lin = fileName.lastIndexOf(".");
                        if (lin < 0) {
                            fileName += gridFSDBFile.getContentType().lastIndexOf(".") < 0 ? "."
                                    + gridFSDBFile.getContentType()
                                    : gridFSDBFile.getContentType();
                        }
                    }
                    os2 = response.getOutputStream();
                    //response.setContentType("image/png");
                    response.setContentType("application/octet-stream");
                    // 获取原文件名
                    String name = (String) gridFSDBFile.get("filename");
                    fileName = new String(name.getBytes("GBK"), "ISO8859-1");
                    // 设置下载文件名
                    response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                    gridFSDBFile.writeTo(os2);
                    os2.flush();
                    os2.close();
                }
            } catch (Exception e) {
            	log.error(e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载所有文件
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @author Liubin
     * @date 2017年5月9日 下午3:59:55
     */
    @RequestMapping(value = "/downLoadZip")
    private void downLoadZip(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String FilePath = request.getServletContext().getRealPath("/upload/");
        //下载的文件的临时保存位置
        String mogodbFilePath = "";
        //获取数据库（老版本mongoDB驱动写法）
        DB db = MongoManager.getInstance().getDB();
        MongoDatabase mongoDB = MongoManager.getInstance().getMongoDatabase();
        MongoIterable<String> Collections = mongoDB.listCollectionNames();
        int pot = 0;
        //记录时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("系统记录mongoDB下载开始时间====mongodb download begin:" + df.format(new Date()));// new Date()为获取当前系统时间

        for (String collectionName : Collections) {
            //System.out.println(collectionName);
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
//		 		String s = UUID.randomUUID().toString();//用来生成数据库的主键id非常不错。
//		 		mogodbFilePath = FilePath + "/" +collectionName+s;
            mogodbFilePath = FilePath + "/" + collectionName;
            File file2 = new File(mogodbFilePath);
            if (!file2.exists()) {
                file2.mkdir();
            }
//            GridFSBucket bucket = GridFSBuckets.create(mongoDB);
            GridFS gridFS = new GridFS(db, collectionName);
            try {
                List<GridFSDBFile> gridFSDBFileList = new ArrayList<GridFSDBFile>();
//					DBObject query=new BasicDBObject("userId", 1);
                // $or (查询id等于1或者id等于2的数据)
                BasicDBObject queryObject = new BasicDBObject();
//			        queryObject.append("metadata.userId",1);
                queryObject.append("userId", 1);
//			        queryObject.append(QueryOperators.AND, new BasicDBObject[] { new BasicDBObject("reid", "241050d6-2e6c-49a2-bf0a-cf3bee9216df"),
//                        new BasicDBObject("filename", "草皮.zip") });
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
                                fileName += nb.getContentType().lastIndexOf(".") < 0 ? "."
                                        + nb.getContentType()
                                        : nb.getContentType();
                            }
                        }
                        // fileName = new String(name1.getBytes("GBK"),"ISO8859-1");
                        // 将mogodb中的文件写到指定文件夹
                        nb.writeTo(mogodbFilePath + "/" + fileName);
                        out2 = new FileOutputStream(mogodbFilePath + "/" + fileName);
                        os2 = new BufferedOutputStream(out2);
                        nb.writeTo(os2);
//							bucket.downloadToStream(fileName, nb.get);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println("系统记录mongoDB下载结束时间====mongodb download end:" + df.format(new Date()));// new Date()为获取当前系统时间

    }
    
    

    public void query() {
        // $or (查询id等于1或者id等于2的数据)  
        BasicDBObject queryObject = new BasicDBObject().append(
                QueryOperators.OR,
                new BasicDBObject[]{new BasicDBObject("id", 1),
                        new BasicDBObject("id", 2)});
        find(queryObject, "(查询id等于1或者id等于2的数据)");

        // $and(查询id等于10并且name等于10的数据)  
        queryObject = new BasicDBObject().append(QueryOperators.AND,
                new BasicDBObject[]{new BasicDBObject("id", 10),
                        new BasicDBObject("name", "10")});
        find(queryObject, "(查询id等于10并且name等于10的数据)");

        // $gt（查询id大于10的数据）  
        queryObject = new BasicDBObject().append("id",
                new BasicDBObject().append(QueryOperators.GT, 10));
        find(queryObject, "（查询id大于10的数据）");
        // $gte （查询id大于等于10的数据）  
        queryObject = new BasicDBObject().append("id",
                new BasicDBObject().append(QueryOperators.GTE, 11));
        find(queryObject, "（查询id大于等于11的数据）");
        // $lt  
        queryObject = new BasicDBObject().append("id",
                new BasicDBObject().append(QueryOperators.LT, 2));
        find(queryObject, "（查询id小于2的数据）");
        // $lte  
        queryObject = new BasicDBObject().append("id",
                new BasicDBObject().append(QueryOperators.LTE, 2));
        find(queryObject, "（查询id小于等于2的数据）");

        // $in  
        queryObject = new BasicDBObject().append("id", new BasicDBObject(
                QueryOperators.IN, new int[]{1, 2}));
        find(queryObject, "（查询id为1和2的数据）");
        // $nin  
        queryObject = new BasicDBObject().append("id", new BasicDBObject(
                QueryOperators.NIN, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
        find(queryObject, "（查询id不为1,2,3,4,5,6,7,8,9的数据）");

        // 还有很多其他的高级查询方式可以参见QueryOperators类  
    }

    public void find(BasicDBObject condition, String str) {
        System.out.println("================" + str + "==================");
//        DB db = MongoManager.getDB(Constants.DB);  
//        DBCollection collection = db.getCollection(Constants.COLLECTION_USER);  
//        DBCursor find = collection.find(condition);  
//        while (find.hasNext()) {  
//            User user = new User();  
//            user.parse(find.next());  
//            System.out.println(user);  
//        }  

    }
}
