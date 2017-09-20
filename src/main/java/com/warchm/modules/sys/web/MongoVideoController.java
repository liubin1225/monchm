package com.warchm.modules.sys.web;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.client.MongoDatabase;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 视频模块相关controller
 * @author liubin
 * @version 2017年7月20日 上午11:17:54
 */
@Controller
@RequestMapping(value = "/mongoVideo")
public class MongoVideoController extends BaseController{

    private transient final Logger log = Logger.getLogger(getClass());
    
    /**
     * mongodb视频列表展示
     *
     * @param model
     * @param request
     * @return
     * @author LiuBin
     * @date 2017年5月8日 下午4:44:20
     */
    @RequestMapping(value = "/mongoVideoList")
    @ResponseBody
    public JsonResult mongoVideoList(ModelAndView model, HttpServletRequest request) {
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
            GridFS gridFS = new GridFS(db, "gridVideo");
            // $or (查询id等于1或者id等于2的数据)
//	        Map<String, Object> map = new HashMap<String, Object>();
//	        map.put("metadata.userId", 1);
            BasicDBObject queryObject = new BasicDBObject();
	        queryObject.append("metadata.userId",2);//userID==2代表video
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
            	//map.put("modelId", gfs.getMetaData().get("modelId"));
            	map.put("filename", gfs.getFilename());
            	map.put("contentType", gfs.getMetaData().get("contentType"));
            	map.put("uploadSystemDate", gfs.getMetaData().get("uploadSystemDate"));
            	map.put("length", gfs.getLength());
            	list.add(map);
			}
            //插入日志
            params.put("op", Constants.opvideoupload);
            params.put("status", Constants.STATUS_SUCCESS);
            params.put("msg", Constants.msgSuccess);
            params.put("ip", AddressUtils.ipUtils());
            params.put("reid", IdGen.uuid());
            MongoUtils.loginsert(params);
        } catch (Exception e) {
            e.printStackTrace();
            params.put("op", "视频浏览");
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
     * 视频文件上传
     *
     * @param request
     * @param response
     */
	@RequestMapping(value = "/videoUpload")
    private String videoUpload(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
		//JsonResult jsonResult = new JsonResult();
        //记录时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("系统记录mongoDB上传开始时间====:" + df.format(new Date()));// new Date()为获取当前系统时间
        try {
            MongoDatabase database = MongoManager.getInstance().getMongoDatabase();
            GridFSBucket gridFSBucket = GridFSBuckets.create(database, "gridVideo");
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
                    
//                    String path = "D:\\mongofiles";  
//                    System.out.println(path);  
//                    File targetFile = new File(path, filename);  
//                    if (!targetFile.exists()) {  
//                        targetFile.mkdirs();  
//                    }  
//                    // 保存  
//                    try {  
//                    	mFile.transferTo(targetFile);  
//                    } catch (Exception e) {  
//                        e.printStackTrace();  
//                    } 
                    
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
                    d.append("reid", IdGen.uuid());
                    d.append("contentType", filename.substring(pot));
                    d.append("aliases", "video");
                    d.append("userId", 2);
                    d.append("uploadSystemDate", df.format(new Date()));
                    options.metadata(d);
                    gridFSBucket.uploadFromStream(filename.trim(), mFile.getInputStream(), options);
                }
            }
            
            //插入日志
            Map<String, Object> params = new HashMap<>();
            params.put("op", Constants.opvideoupload);
            params.put("status", Constants.STATUS_SUCCESS);
            params.put("msg", Constants.msgSuccess);
            params.put("ip", AddressUtils.ipUtils());
            params.put("reid", IdGen.uuid());
            MongoUtils.loginsert(params);
            System.out.println("系统记录mongoDB上传结束时间:" + df.format(new Date()));// new Date()为获取当前系统时间
        } catch (Exception e) {
        	//插入日志
            Map<String, Object> params = new HashMap<>();
            params.put("op", Constants.opvideoupload);
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
            addMessage(redirectAttributes, "视频上传失败!");
        }
        addMessage(redirectAttributes, "视频上传成功!");
        return "redirect:/mongoVideoUpload";
    }
	
	/**
     * 查看视频文件
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/downloadVideoFile")
    private String downloadVideoFile(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
    	//JsonResult jsonresult = new JsonResult();
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("系统记录mongoDB==下载开始时间====:" + df.format(new Date()));// new Date()为获取当前系统时间
        String fileName = "";
        try {
            String fileId = request.getParameter("reid");
//            String fileId = "94ef3490bdc6432e943dd74175dd8ef8";
            DB db = MongoManager.getInstance().getDB();
            GridFS gridFS = new GridFS(db, "gridVideo");
//			ObjectId objId = new ObjectId(fileId);
            DBObject query = new BasicDBObject();
            query.put("metadata.reid", fileId);
//            query.put("uploadSystemDate", "2017-06-28 16:08:50");
            GridFSDBFile gridFSDBFile = (GridFSDBFile) gridFS.findOne(query);
            FileOutputStream out2 = null;
            OutputStream os2 = null;
            String mogodbFilePath = request.getServletContext().getRealPath("/cache/");
//            String mogodbFilePath = "D://mongofiles";//默认磁盘地址
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
                    
                    File file3 = new File(mogodbFilePath + "/" + fileName.trim());
                    if (file3.exists()) {
                    	addMessage(redirectAttributes, fileName.trim());
                    	return "redirect:/mongoVideoScan";
                    }
                    // fileName = new String(name1.getBytes("GBK"),"ISO8859-1");
                    // 将mogodb中的文件写到指定文件夹
                    //gridFSDBFile.writeTo(mogodbFilePath + "/" + fileName);
                    out2 = new FileOutputStream(mogodbFilePath + "/" + fileName.trim());
                    os2 = new BufferedOutputStream(out2);
                    gridFSDBFile.writeTo(os2);
                }else {
                	addMessage(redirectAttributes, "");
                	return "redirect:/mongoVideoScan";
                }
            } catch (Exception e) {
            	log.error(e.getMessage());
            	addMessage(redirectAttributes, "");
            	return "redirect:/mongoVideoScan";
            } finally {
            	if (os2 != null){
					try {
						os2.flush();
		                os2.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
            	if (out2 != null){
					try {
						out2.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
            }

        } catch (Exception e) {
        	log.error(e.getMessage());
        	addMessage(redirectAttributes, "");
        	return "redirect:/mongoVideoScan";
        }
        System.out.println("系统记录mongoDB==下载结束时间====:" + df.format(new Date()));// new Date()为获取当前系统时间
        addMessage(redirectAttributes, fileName.trim());
        return "redirect:/mongoVideoScan";
//        return jsonresult;
    }
    
    /**
     * 视频删除
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/deleteVideo")
    @ResponseBody
    private JsonResult deleteVideo(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
    	JsonResult jsonResult = new JsonResult(); 
    	try {
            String fileId = (String) request.getParameter("reid");
            MongoDatabase database = MongoManager.getInstance().getMongoDatabase();
//			GridFS gridFS= new GridFS(db,gridFSstr);
            GridFSBucket bucket = GridFSBuckets.create(database, "gridVideo");

//			BsonObjectId objId = new BsonObjectId().asObjectId();
//			bucket.delete(BsonValue.class.cast(fileId));
            ObjectId objectId = new ObjectId(fileId);
            bucket.delete(objectId);
//			gridFS.remove(objId);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setCode(1);
            return jsonResult;
        }
        jsonResult.setCode(0);
        return jsonResult;
    }
    
}
