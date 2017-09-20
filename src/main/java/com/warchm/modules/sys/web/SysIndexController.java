package com.warchm.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import test.TestS;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页系统controller
 * @author liubin
 * @version 2017年7月20日 上午11:17:54
 */
@Controller
public class SysIndexController {
    
	/**
	 * 首页
	 * @param model
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/index")
    public String index(ModelAndView model, HttpServletRequest request) {

        return "modules/system/index";
    }

    @RequestMapping(value = "/mongoFileList")
    public String mongoList(ModelAndView model, HttpServletRequest request) {
    	TestS ts = new TestS();
    	ts.testStr();
        return "modules/mongo/mongoFileList";
    }

    @RequestMapping(value = "/mongodbtest")
    public String mongodbtest(ModelAndView model, HttpServletRequest request) {
        return "modules/mongodbtest";
    }
    
    @RequestMapping(value = "/userRegister")
    public String register(ModelAndView model, HttpServletRequest request) {
        return "modules/userRegister/userRegister";
    }

    /**
     * @param model
     * @param request
     * @return
     * @author liubin
     */
    @RequestMapping(value = "/mongoFileUploads")
    public String mongoUpload(ModelAndView model, HttpServletRequest request) {

        return "modules/mongo/mongoFileUploads";
    }
    
    /**
     * @param model
     * @param request
     * @return
     * @author liubin
     */
    @RequestMapping(value = "/mongoFileDrag")
    public String mongoDragUpload(ModelAndView model, HttpServletRequest request) {

        return "modules/mongo/mongoFileDrag";
    }
    
    /**
     * @param model
     * @param request
     * @return
     * @author liubin
     */
    @RequestMapping(value = "/scanLog")
    public String scanLog() {
    	
        return "modules/mongo/mongoScanLog";
    }
    
    /**
     * @param model
     * @param request
     * @return
     * @author liubin
     */
    @RequestMapping(value = "/solrSearch")
    public String solrSearch() {

        return "modules/solr/solrSearch";
    }
    
    /**
     * @param model
     * @param request
     * @return
     * @author liubin
     */
    @RequestMapping(value = "/mongoTreeList")
    public String mongoTreeList() {

        return "modules/mongo/mongoTreeList";
    }
    
    /**
     * @return
     * @author liubin
     */
    @RequestMapping(value = "/mongoFileFolder")
    public String mguploadfile() {
        return "modules/mongo/mongoFileFolder";
    }
    
    /**
     * @return
     * @author liubin
     */
    @RequestMapping(value = "/mongoForm")
    public String mongoForm() {
        return "modules/mongo/mongoForm";
    }
    
    /**
     * @return
     * @author liubin
     */
    @RequestMapping(value = "/search")
    public String search() {
        return "search";
    }
    
    /**
     * @return
     * @author liubin
     */
    @RequestMapping(value = "/ddz")
    public String ddz() {
        return "modules/ddz/ddz";
    }
    
    /**
     * @return
     * @author liubin
     */
    @RequestMapping(value = "/mongoVideoUpload")
    public String mongoVideoUpload() {
        return "modules/mongo/mongoVideoUpload";
    }
    
    /**
     * @return
     * @author liubin
     */
    @RequestMapping(value = "/mongoVideoList")
    public String mongoVideoList() {
        return "modules/mongo/mongoVideoList";
    }
    
    /**
     * @return
     * @author liubin
     */
    @RequestMapping(value = "/mongoVideoScan")
    public String mongoVideoScan() {
        return "modules/mongo/mongoVideoScan";
    }
    
    /**
     * @return
     * @author liubin
     */
    @RequestMapping(value = "/appupload")
    public String appupload() {
        return "modules/mongo/appupload";
    }
    /**
     * @return
     * @author liubin
     */
    @RequestMapping(value = "/SiteMesh")
    public String SiteMesh() {
        return "modules/mongo/SiteMesh";
    }
    
    
}
