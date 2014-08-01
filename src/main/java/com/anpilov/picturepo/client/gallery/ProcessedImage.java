package com.anpilov.picturepo.client.gallery;

import com.anpilov.picturepo.client.gallery.dto.ImageMetadata;
import org.opencv.core.Mat;

public class ProcessedImage {

	private ImageMetadata imageMetadata;
	private Mat histogram;

	public ProcessedImage(ImageMetadata imageMetadata) {
		this.imageMetadata = imageMetadata;
	}

	public ImageMetadata getImageMetadata() {
		return imageMetadata;
	}

	public Mat getHistogram() {
		return histogram;
	}

	public void setHistogram(Mat histogram) {
		this.histogram = histogram;
	}
}
