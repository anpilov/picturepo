package com.anpilov.picturepo.image;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.Arrays;

public class ImageComparison {

	static {
		OpenCVLoader.load();
	}

	public static final double DEFAULT_TRESHOLD = 0.9;

	public static ImageComparisonResult compare(File image1, File image2) {
		return compare(image1, image2, DEFAULT_TRESHOLD);
	}

	public static ImageComparisonResult compare(File image1, File image2, double treshold) {
		Mat img1 = Highgui.imread(image1.getAbsolutePath());
		Mat img2 = Highgui.imread(image2.getAbsolutePath());

		Mat hist1 = new Mat();
		Mat hist2 = new Mat();

		Imgproc.calcHist(Arrays.asList(img1), new MatOfInt(0, 1), new Mat(), hist1, new MatOfInt(50, 60), new MatOfFloat(0, 180, 0, 256));
		Imgproc.calcHist(Arrays.asList(img2), new MatOfInt(0, 1), new Mat(), hist2, new MatOfInt(50, 60), new MatOfFloat(0, 180, 0, 256));

		double similarity = Imgproc.compareHist(hist1, hist2, Imgproc.CV_COMP_CORREL);
		return new ImageComparisonResult(treshold, similarity);
	}
}
