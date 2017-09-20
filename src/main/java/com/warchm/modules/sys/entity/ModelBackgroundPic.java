package com.warchm.modules.sys.entity;

public class ModelBackgroundPic {
    private Integer modelPicId;

    private String modelPicName;

    private String modelPicUrl;

    private String modelPicDescription;

    public Integer getModelPicId() {
        return modelPicId;
    }

    public void setModelPicId(Integer modelPicId) {
        this.modelPicId = modelPicId;
    }

    public String getModelPicName() {
        return modelPicName;
    }

    public void setModelPicName(String modelPicName) {
        this.modelPicName = modelPicName == null ? null : modelPicName.trim();
    }

    public String getModelPicUrl() {
        return modelPicUrl;
    }

    public void setModelPicUrl(String modelPicUrl) {
        this.modelPicUrl = modelPicUrl == null ? null : modelPicUrl.trim();
    }

    public String getModelPicDescription() {
        return modelPicDescription;
    }

    public void setModelPicDescription(String modelPicDescription) {
        this.modelPicDescription = modelPicDescription == null ? null : modelPicDescription.trim();
    }
}