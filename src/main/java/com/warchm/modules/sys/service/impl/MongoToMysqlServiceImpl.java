package com.warchm.modules.sys.service.impl;

import com.warchm.modules.sys.dao.MongoToMysqlDao;
import com.warchm.modules.sys.entity.ModelFile;
import com.warchm.modules.sys.entity.ModelMaster;
import com.warchm.modules.sys.service.MongoToMysqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MongoToMysqlServiceImpl implements MongoToMysqlService {
	
	@Autowired
	private MongoToMysqlDao mongoToMysqlDao;

	@Override
	public int insert(Map<?, ?> params) {
		return mongoToMysqlDao.insert(params);
	}

	@Override
	public List<ModelFile> findModelFileByModelId(int ModelId) {
		return mongoToMysqlDao.findModelFileByModelId(ModelId);
	}
	
	@Override
	public ModelMaster findModelMasterByModelId(String selectId ,int ModelId) {
		return mongoToMysqlDao.findModelMasterByModelId(selectId,ModelId);
	}

	@Override
	public int insert(String InsertId, Map<?, ?> params) {
		return mongoToMysqlDao.insert(InsertId,params);
	}

	@Override
	public Object findByUrl(String InsertId, String picUrl) {
		return mongoToMysqlDao.findByUrl(InsertId, picUrl);
	}

	@Override
	public int updateById(String updateId,Map<?,?> params) {
		return mongoToMysqlDao.updateById(updateId, params);
	}

	@Override
	public ModelMaster getModelMasterByModelId(int ModelId) {
		return mongoToMysqlDao.getModelMasterByModelId(ModelId);
	}

}
