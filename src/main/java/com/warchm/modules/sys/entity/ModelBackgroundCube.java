package com.warchm.modules.sys.entity;

public class ModelBackgroundCube {
    private Integer modelCubeId;

    private String modelCubeName;

    private String modelCubeUrl;

    private String modelCubDescription;

    public Integer getModelCubeId() {
        return modelCubeId;
    }

    public void setModelCubeId(Integer modelCubeId) {
        this.modelCubeId = modelCubeId;
    }

    public String getModelCubeName() {
        return modelCubeName;
    }

    public void setModelCubeName(String modelCubeName) {
        this.modelCubeName = modelCubeName == null ? null : modelCubeName.trim();
    }

    public String getModelCubeUrl() {
        return modelCubeUrl;
    }

    public void setModelCubeUrl(String modelCubeUrl) {
        this.modelCubeUrl = modelCubeUrl == null ? null : modelCubeUrl.trim();
    }

    public String getModelCubDescription() {
        return modelCubDescription;
    }

    public void setModelCubDescription(String modelCubDescription) {
        this.modelCubDescription = modelCubDescription == null ? null : modelCubDescription.trim();
    }
}