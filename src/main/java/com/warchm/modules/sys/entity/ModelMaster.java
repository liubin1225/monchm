package com.warchm.modules.sys.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ModelMaster {
    private Integer modelId;

    private String modelName;

    private Integer typeId;

    private Integer labelId;

    private Integer previewPicId;

    private BigDecimal modelSize;

    private Integer authorId;

    private Date modelUploadTime;

    private Integer modelStatus;

    private BigDecimal zoom;

    private Integer viewerCount;

    private Integer donwloderCount;

    private Integer privateModel;

    private Integer allowDownload;

    private Integer ageRestricted;

    private String backgroundColor;

    private BigDecimal positionX;

    private BigDecimal positionY;

    private BigDecimal positionZ;

    private BigDecimal rotationX;

    private BigDecimal rotationY;

    private BigDecimal rotationZ;

    private BigDecimal directionalLight;

    private BigDecimal ambientLight;

    private BigDecimal modelPrice;

    private String modelEmbededId;
    
    private String modelDescription;
    
    
    
    private ModelBackgroundCube backgroundCubeId;
    
    private ModelBackgroundPic backgroundPicId;

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName == null ? null : modelName.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getPreviewPicId() {
        return previewPicId;
    }

    public void setPreviewPicId(Integer previewPicId) {
        this.previewPicId = previewPicId;
    }

    public BigDecimal getModelSize() {
        return modelSize;
    }

    public void setModelSize(BigDecimal modelSize) {
        this.modelSize = modelSize;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Date getModelUploadTime() {
        return modelUploadTime;
    }

    public void setModelUploadTime(Date modelUploadTime) {
        this.modelUploadTime = modelUploadTime;
    }

    public Integer getModelStatus() {
        return modelStatus;
    }

    public void setModelStatus(Integer modelStatus) {
        this.modelStatus = modelStatus;
    }

    public BigDecimal getZoom() {
        return zoom;
    }

    public void setZoom(BigDecimal zoom) {
        this.zoom = zoom;
    }

    public Integer getViewerCount() {
        return viewerCount;
    }

    public void setViewerCount(Integer viewerCount) {
        this.viewerCount = viewerCount;
    }

    public Integer getDonwloderCount() {
        return donwloderCount;
    }

    public void setDonwloderCount(Integer donwloderCount) {
        this.donwloderCount = donwloderCount;
    }

    public Integer getPrivateModel() {
        return privateModel;
    }

    public void setPrivateModel(Integer privateModel) {
        this.privateModel = privateModel;
    }

    public Integer getAllowDownload() {
        return allowDownload;
    }

    public void setAllowDownload(Integer allowDownload) {
        this.allowDownload = allowDownload;
    }

    public Integer getAgeRestricted() {
        return ageRestricted;
    }

    public void setAgeRestricted(Integer ageRestricted) {
        this.ageRestricted = ageRestricted;
    }

	public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor == null ? null : backgroundColor.trim();
    }

    public BigDecimal getPositionX() {
        return positionX;
    }

    public void setPositionX(BigDecimal positionX) {
        this.positionX = positionX;
    }

    public BigDecimal getPositionY() {
        return positionY;
    }

    public void setPositionY(BigDecimal positionY) {
        this.positionY = positionY;
    }

    public BigDecimal getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(BigDecimal positionZ) {
        this.positionZ = positionZ;
    }

    public BigDecimal getRotationX() {
        return rotationX;
    }

    public void setRotationX(BigDecimal rotationX) {
        this.rotationX = rotationX;
    }

    public BigDecimal getRotationY() {
        return rotationY;
    }

    public void setRotationY(BigDecimal rotationY) {
        this.rotationY = rotationY;
    }

    public BigDecimal getRotationZ() {
        return rotationZ;
    }

    public void setRotationZ(BigDecimal rotationZ) {
        this.rotationZ = rotationZ;
    }

    public BigDecimal getDirectionalLight() {
        return directionalLight;
    }

    public void setDirectionalLight(BigDecimal directionalLight) {
        this.directionalLight = directionalLight;
    }

    public BigDecimal getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(BigDecimal ambientLight) {
        this.ambientLight = ambientLight;
    }

    public BigDecimal getModelPrice() {
        return modelPrice;
    }

    public void setModelPrice(BigDecimal modelPrice) {
        this.modelPrice = modelPrice;
    }

    public String getModelEmbededId() {
        return modelEmbededId;
    }

    public void setModelEmbededId(String modelEmbededId) {
        this.modelEmbededId = modelEmbededId == null ? null : modelEmbededId.trim();
    }

	public String getModelDescription() {
		return modelDescription;
	}

	public void setModelDescription(String modelDescription) {
		this.modelDescription = modelDescription;
	}

	public ModelBackgroundCube getBackgroundCubeId() {
		return backgroundCubeId;
	}

	public void setBackgroundCubeId(ModelBackgroundCube backgroundCubeId) {
		this.backgroundCubeId = backgroundCubeId;
	}

	public ModelBackgroundPic getBackgroundPicId() {
		return backgroundPicId;
	}

	public void setBackgroundPicId(ModelBackgroundPic backgroundPicId) {
		this.backgroundPicId = backgroundPicId;
	}

	@Override
	public String toString() {
		return "ModelMaster [modelId=" + modelId + ", modelName=" + modelName + ", typeId=" + typeId + ", labelId="
				+ labelId + ", previewPicId=" + previewPicId + ", modelSize=" + modelSize + ", authorId=" + authorId
				+ ", modelUploadTime=" + modelUploadTime + ", modelStatus=" + modelStatus + ", zoom=" + zoom
				+ ", viewerCount=" + viewerCount + ", donwloderCount=" + donwloderCount + ", privateModel="
				+ privateModel + ", allowDownload=" + allowDownload + ", ageRestricted=" + ageRestricted
				+ ", backgroundColor=" + backgroundColor + ", positionX=" + positionX + ", positionY=" + positionY
				+ ", positionZ=" + positionZ + ", rotationX=" + rotationX + ", rotationY=" + rotationY + ", rotationZ="
				+ rotationZ + ", directionalLight=" + directionalLight + ", ambientLight=" + ambientLight
				+ ", modelPrice=" + modelPrice + ", modelEmbededId=" + modelEmbededId + ", modelDescription="
				+ modelDescription + ", backgroundCubeId=" + backgroundCubeId + ", backgroundPicId=" + backgroundPicId
				+ "]";
	}
    
}