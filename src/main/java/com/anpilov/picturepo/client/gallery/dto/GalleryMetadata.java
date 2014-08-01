package com.anpilov.picturepo.client.gallery.dto;

import java.util.ArrayList;
import java.util.List;

public class GalleryMetadata {

    private String name;
	private String path;
	private boolean uploaded;

    private List<ImageMetadata> images = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isUploaded() {
		return uploaded;
	}

	public void setUploaded(boolean uploaded) {
		this.uploaded = uploaded;
	}

	public List<ImageMetadata> getImages() {
        return images;
    }

    public void setImages(List<ImageMetadata> images) {
        this.images = images;
    }
}
