package com.anpilov.picturepo.image;

public class ImageComparisonResult {

	private double treshold;
	private double similarity;

	public ImageComparisonResult(double treshold, double similarity) {
		this.treshold = treshold;
		this.similarity = similarity;
	}

	public double getTreshold() {
		return treshold;
	}

	public double getSimilarity() {
		return similarity;
	}

	public boolean areSimilar() {
		return similarity >= treshold;
	}
}
