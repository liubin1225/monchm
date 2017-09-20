package com.warchm.modules.solr.web;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 文件检索controller
 * @author liubin
 * @version 2017年7月20日 上午11:17:54
 */
@Controller
@RequestMapping(value = "/solr")
public class SolrController {

    private transient final Logger log = Logger.getLogger(getClass());
    
    @RequestMapping(value="/getdoclist",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getdoclist() throws Exception{
		String url = "http://192.168.0.195:8080/solr/mycode";
		SolrClient solr = new HttpSolrClient(url);
		SolrQuery query2 = new SolrQuery();
		query2.set("name", "李三");
		QueryResponse response = solr.query(query2);
		SolrDocumentList list = response.getResults();
		
		SolrQuery query = new SolrQuery();
		QueryResponse response2 = solr.query(query);
		SolrDocumentList list2 = response2.getResults();
//		return JSON.toJSONString(grid);
		return "";
	}
	
	@RequestMapping("/search")
	String search(){
		return "search";
	}
	
	@RequestMapping("/search2")
	String search2(){
		return "search2";
	}
    
    
}
