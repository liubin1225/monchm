package com.warchm.common.utils;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
* @author liubin
* @version 2017年9月14日上午10:27:42
*/
public class CustomMultipartResolver extends CommonsMultipartResolver {
	
	// 注入第二步写的FileUploadProgressListener 
    private FileUploadProgressListener progressListener = new FileUploadProgressListener();
    @SuppressWarnings("unused")
	private HttpServletRequest request;

    public CustomMultipartResolver(ServletContext servletContext) {
		// TODO Auto-generated constructor stub
	}
    
    public MultipartHttpServletRequest resolveMultipart(  
            HttpServletRequest request) throws MultipartException {  
        this.request = request;// 获取到request,要用到session  
        return super.resolveMultipart(request);  
    } 

	public void setFileUploadProgressListener(FileUploadProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    public MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
        String encoding = determineEncoding(request);
        FileUpload fileUpload = prepareFileUpload(encoding);
        progressListener.setSession(request.getSession());
        fileUpload.setProgressListener(progressListener);
        try {
            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
            return parseFileItems(fileItems, encoding);
        } catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
        } catch (FileUploadException ex) {
            throw new MultipartException("Could not parse multipart servlet request", ex);
        }
    }

}
