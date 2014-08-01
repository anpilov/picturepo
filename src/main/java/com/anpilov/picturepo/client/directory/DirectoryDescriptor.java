package com.anpilov.picturepo.client.directory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryDescriptor {

	private String name;
	private String path;
	private String parent;

	private List<DirectoryDescriptor> galleries = new ArrayList<>();
	private List<DirectoryDescriptor> subdirs = new ArrayList<>();

	private int imageCount;

	public DirectoryDescriptor(File file) {
		name = file.getName();
		path = file.getAbsolutePath();
		parent = file.getParent();
	}

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

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<DirectoryDescriptor> getGalleries() {
		return galleries;
	}

	public void setGalleries(List<DirectoryDescriptor> galleries) {
		this.galleries = galleries;
	}

	public List<DirectoryDescriptor> getSubdirs() {
		return subdirs;
	}

	public void setSubdirs(List<DirectoryDescriptor> subdirs) {
		this.subdirs = subdirs;
	}

	public int getImageCount() {
		return imageCount;
	}

	public void setImageCount(int imageCount) {
		this.imageCount = imageCount;
	}
}
