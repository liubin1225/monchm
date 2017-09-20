package com.warchm.modules.sys.dao.impl;

import com.warchm.modules.sys.dao.MongoToMysqlDao;
import com.warchm.modules.sys.entity.ModelFile;
import com.warchm.modules.sys.entity.ModelMaster;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MongoToMysqlDaoImpl implements MongoToMysqlDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int insert(Map<?,?> params) {
		return this.sqlSessionTemplate.insert("insertUser", params);
	}

	@Override
	public List<ModelFile> findModelFileByModelId(int ModelId) {
		return this.sqlSessionTemplate.selectList("findModelFileByModelId", ModelId);
	}
	
	@Override
	public ModelMaster findModelMasterByModelId(String selectId ,int modelId) {
		return this.sqlSessionTemplate.selectOne(selectId, modelId);
	}


	@Override
	public int insert(String InsertId, Map<?, ?> params) {
		return this.sqlSessionTemplate.insert(InsertId, params);
	}

	@Override
	public Object findByUrl(String InsertId, String picUrl) {
		return this.sqlSessionTemplate.selectOne(InsertId, picUrl);
	}

	@Override
	public int updateById(String updateId, Map<?,?> params) {
		return this.sqlSessionTemplate.update(updateId, params);
	}

	

	@Override
	public ModelMaster getModelMasterByModelId(int modelId) {
		return this.sqlSessionTemplate.selectOne("getModelMasterByModelId", modelId);
	}


}
