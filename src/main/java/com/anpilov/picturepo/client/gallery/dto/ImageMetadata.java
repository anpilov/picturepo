package com.anpilov.picturepo.client.gallery.dto;

public class ImageMetadata {

    private String name;

	private String originalPath;
	private String thumbnailPath;

	private ImageStatus imageStatus = ImageStatus.SELECTED;
    private String similarityGroup;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public ImageStatus getImageStatus() {
		return imageStatus;
	}

	public void setImageStatus(ImageStatus imageStatus) {
		this.imageStatus = imageStatus;
	}

	public String getSimilarityGroup() {
        return similarityGroup;
    }

    public void setSimilarityGroup(String similarityGroup) {
        this.similarityGroup = similarityGroup;
    }

	public String getOriginalPath() {
		return originalPath;
	}

	public void setOriginalPath(String originalPath) {
		this.originalPath = originalPath;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
}
