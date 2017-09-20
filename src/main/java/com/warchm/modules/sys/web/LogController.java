package com.warchm.modules.sys.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.warchm.common.idGen.IdGen;
import com.warchm.common.mongo.MongoManager;
import com.warchm.common.utils.JsonResult;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;

/**
* @author liubin
* @version 2017年8月29日下午3:19:36
*/
@Controller
public class LogController {
	
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
            System.out.println("系统记录mongoDB上传结束时间:" + df.format(new Date()));// new Date()为获取当前系统时间
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonResult.setCode(0);
        return jsonResult;
    }

}
