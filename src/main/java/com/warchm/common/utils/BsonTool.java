package com.warchm.common.utils;

import java.util.Date;

import org.bson.BsonDateTime;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.BsonNull;
import org.bson.BsonString;
import org.bson.BsonValue;

/**
* @author liubin
* @version 2017年8月29日下午4:00:48
*/
public class BsonTool {
	
	/** 
     * Java对象转BsonValue对象 
     * @param obj 
     * @return 
     */  
    public static BsonValue objectToBsonValue(Object obj){  
        if (obj instanceof Integer){  
            return new BsonInt32((Integer) obj);  
        }  
  
        if (obj instanceof String){  
            return new BsonString((String) obj);  
        }  
  
        if (obj instanceof Long){  
            return new BsonInt64((Long) obj);  
        }  
  
        if (obj instanceof Date){  
            return new BsonDateTime(((Date) obj).getTime());  
        }  
        return new BsonNull();  
    }

}
