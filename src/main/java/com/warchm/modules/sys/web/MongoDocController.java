package com.warchm.modules.sys.web;

import com.warchm.common.web.BaseController;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * doc常规数据controller
 * @author liubin
 * @version 2017年7月20日 上午11:17:54
 */
@Controller
@RequestMapping(value = "/mongo")
public class MongoDocController extends BaseController{

   private transient final Logger log = Logger.getLogger(getClass());
    
}
