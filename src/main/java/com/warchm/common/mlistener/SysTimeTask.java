package com.warchm.common.mlistener;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.TimerTask;

public class SysTimeTask extends TimerTask {

	private static boolean isRunning = false;   
    private ServletContext context = null;   
 
    public SysTimeTask(ServletContext context){  
          
        this.context = context ;  
    }  
    @Override  
    public void run() {  
 
       if (! isRunning){  
           //if (C_SCHEDULE_HOUR == c.get(Calendar.HOUR_OF_DAY)) {  
               isRunning = true ;  
               context.log("mongoDB系统任务开始并执行指定任务！") ;  
               //TODO 添加自定义的详细任务，以下只是示例   
               //String fileRoot = "D:\\JavaServer\\apache-tomcat-7.0.75\\webapps\\cache";  
               //第一步：清空目录下所有文件
               String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
               String fileRoot = path.substring(1, path.indexOf("/WEB-INF/classes/"))+"/upload";
               delFolder(fileRoot);  
               
               //第二步：下载mongoDB文件
//               try {
//            	   mongo.mgdownloadFile();
//			   } catch (Exception e) {
//				   // TODO Auto-generated catch block
//				   e.printStackTrace();
//				   context.log("指定下载任务执行失败！"); 
//			   }
               
               //第三步：上传mongoDB文件
//               try {
//            	   mongo.mguploadfile();
//			   } catch (Exception e) {
//				   // TODO Auto-generated catch block
//				   e.printStackTrace();
//				   context.log("指定上传任务执行失败！"); 
//			   }
               
               isRunning = false;   
               context.log("mongoDB系统任务结束！");   
       }else{  
             
           context.log("上一次任务执行还未结束");   
 
       }  
    } 
    
  
//  // 删除完文件后删除文件夹  
//  // param folderPath 文件夹完整绝对路径  
    public static void delFolder(String folderPath) {  
        try {  
            delAllFile(folderPath); // 删除完里面所有内容  
            //不想删除文佳夹隐藏下面  
//            String filePath = folderPath;  
//            filePath = filePath.toString();  
//            java.io.File myFilePath = new java.io.File(filePath);  
//            myFilePath.delete(); // 删除空文件夹  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    // 删除指定文件夹下所有文件  
    // param path 文件夹完整绝对路径  
    public static boolean delAllFile(String path) {  
        boolean flag = false;  
        File file = new File(path);  
        if (!file.exists()) {  
            return flag;  
        }  
        if (!file.isDirectory()) {  
            return flag;  
        }  
        String[] tempList = file.list();  
        File temp = null;  
        for (int i = 0; i < tempList.length; i++) {  
            if (path.endsWith(File.separator)) {  
                //temp = new File(path + tempList[i]); 
            	temp = new File("");
            } else {  
                temp = new File(path + File.separator + tempList[i]);  
            }  
            if (temp.isFile()) {  
                temp.delete();  
            }  
            if (temp.isDirectory()) {  
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件  
                //delFolder(path + "/" + tempList[i]);// 再删除空文件夹  
                temp.delete();
                flag = true;  
            }  
        }  
        return flag;  
    }  

}
