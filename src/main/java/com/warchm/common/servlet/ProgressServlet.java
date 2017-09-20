package com.warchm.common.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.warchm.common.utils.FileUploadListener;
import com.warchm.modules.sys.web.MongoFileController;


/**
* @author liubin
* @version 2017年9月14日下午2:10:58
*/
public class ProgressServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		//PrintWriter out = response.getWriter();
		String str = "";
	
		HttpSession session = request.getSession(true);
		if (session == null) {
			str = "Sorry, session is null"; // just to be safe
			renderData(response,str);
		return;
		}
	
		FileUploadListener uploadProgressListener = (FileUploadListener) session.getAttribute("uploadProgressListener");
		if (uploadProgressListener == null) {
			str = "Progress listener is null";
			renderData(response,str);
		return;
		}
		int ret=uploadProgressListener.getPercentDone();
		System.out.println("perc ->:"+String.valueOf(ret));
		//out.println(ret);
		renderData(response,String.valueOf(ret));

	}
	
	/**
	   * 通过PrintWriter将响应数据写入response，ajax可以接受到这个数据
	   * 
	   * @param response
	   * @param data 
	   */
    private void renderData(HttpServletResponse response, String data) {
  	    response.setContentType("text/html;charset=UTF-8");
	    PrintWriter printWriter = null;
	    try {
	      printWriter = response.getWriter();
	      printWriter.print(data);
	    } catch (IOException ex) {
	      Logger.getLogger(MongoFileController.class.getName()).log(Level.ERROR, null, ex);
	    } finally {
	      if (null != printWriter) {
	        printWriter.flush();
	        printWriter.close();
	     }
	    }
	}

}
