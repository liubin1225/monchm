package com.warchm.modules.sys.service;

import com.warchm.modules.sys.entity.ModelFile;
import com.warchm.modules.sys.entity.ModelMaster;

import java.util.List;
import java.util.Map;

public interface MongoToMysqlService {
	
	public int insert(Map<?, ?> params);

	public int insert(String InsertId, Map<?, ?> params);

	public List<ModelFile> findModelFileByModelId(int ModelId);

	public ModelMaster findModelMasterByModelId(String selectId, int ModelId);

	public Object findByUrl(String InsertId, String picUrl);

	public int updateById(String updateId, Map<?, ?> params);
	

	public ModelMaster getModelMasterByModelId(int ModelId);
}
