package com.warchm.modules.sys.entity;

import java.util.List;

public class ModelFile {
	private int Model_file_id;
	private int Model_id;
	private String Collection;
	private String UUid;
	private String filename;
	private Long compressSize;
	
	
	private List<ModelMaster> modelMasterList;
	
	public ModelFile(){}
	
	public int getModel_file_id() {
		return Model_file_id;
	}

	public void setModel_file_id(int model_file_id) {
		Model_file_id = model_file_id;
	}

	public int getModel_id() {
		return Model_id;
	}
	public void setModel_id(int model_id) {
		Model_id = model_id;
	}
	public String getCollection() {
		return Collection;
	}
	public void setCollection(String collection) {
		Collection = collection;
	}
	public String getUUid() {
		return UUid;
	}
	public void setUUid(String uUid) {
		UUid = uUid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<ModelMaster> getModelMasterList() {
		return modelMasterList;
	}

	public void setModelMasterList(List<ModelMaster> modelMasterList) {
		this.modelMasterList = modelMasterList;
	}

	public Long getCompressSize() {
		return compressSize;
	}

	public void setCompressSize(Long compressSize) {
		this.compressSize = compressSize;
	}


	
}
