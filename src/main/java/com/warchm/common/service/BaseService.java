package com.warchm.common.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * service 基类，可以加入公用方法
 * @author LiShuai
 * @date 2017/7/19 0019 08:21
 * @last_modified_date 2017/7/19 0019 08:21
 * @description
 */
@Transactional(readOnly = true)
public abstract class BaseService {

}
