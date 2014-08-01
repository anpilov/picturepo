package com.anpilov.picturepo.client.gallery.cache;

import com.anpilov.picturepo.client.gallery.dto.ImageMetadata;
import com.anpilov.picturepo.common.ApplicationException;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GalleryGenerationStatus {

	private int remainingImageCount = 0;
	private List<ImageMetadata> processedImages = new LinkedList<>();

	public synchronized void start(int count) {
		if (remainingImageCount > 0) {
			throw new ApplicationException("Image processing already running");
		}
		remainingImageCount = count;
		processedImages.clear();
	}

	public synchronized boolean isRunning() {
		return remainingImageCount > 0;
	}

	public synchronized void addProgress(ImageMetadata imageMetadata) {
		if (remainingImageCount == 0) {
			throw new InvalidStateException("remainingImageCount = 0");
		}
		remainingImageCount--;
		processedImages.add(imageMetadata);
	}

	public synchronized List<ImageMetadata> getProgress() {
		ArrayList<ImageMetadata> progress = new ArrayList<>(processedImages);
		processedImages.remove(progress);
		return progress;
	}
}
