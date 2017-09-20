package com.warchm.common.utils;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import com.warchm.modules.sys.entity.Progress;

/**
* @author liubin
* @version 2017年9月14日上午10:26:35
*/
public class FileUploadProgressListener implements ProgressListener {

	private HttpSession session;
    public void setSession(HttpSession session){
        this.session=session;
        Progress status = new Progress();//保存上传状态
        session.setAttribute("status", status);
    }
    @Override
    public void update(long bytesRead, long contentLength, int items) {
        Progress status = (Progress) session.getAttribute("status");
        status.setBytesRead(bytesRead);
        status.setContentLength(contentLength);
        status.setItems(items);
    }

}
