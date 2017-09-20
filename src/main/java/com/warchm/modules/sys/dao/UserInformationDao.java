package com.warchm.modules.sys.dao;

import com.warchm.common.annotation.MyBatisDao;
import com.warchm.common.dao.BaseDao;
import com.warchm.modules.sys.entity.UserInformation;

@MyBatisDao
@SuppressWarnings("all")
public interface UserInformationDao extends BaseDao<UserInformation> {

    UserInformation getUserByEmail(String userName);

    UserInformation getUserByMobile(String userName);

    UserInformation getUserByUserName(String userName);
}