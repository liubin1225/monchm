package com.warchm.common.mlistener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SysListener implements ServletContextListener {

	private java.util.Timer timer = null ;  
    public void contextDestroyed(ServletContextEvent event) {  
        // TODO Auto-generated method stub  
  
    }  
  
    public void contextInitialized(ServletContextEvent event) {  
  
        timer = new java.util.Timer(true) ;  
        event.getServletContext().log("定时器已启动。") ;  
        //只执行一次
        timer.schedule(new SysTimeTask(event.getServletContext()), 0) ; 
        event.getServletContext().log("已经添加任务调度表。" ) ;  
          
    }  

}
