package com.anpilov.picturepo.image;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;
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

		Mat hist1 = histogram(img1);
		Mat hist2 = histogram(img2);

		double similarity = Imgproc.compareHist(hist1, hist2, Imgproc.CV_COMP_CORREL);
		return new ImageComparisonResult(treshold, similarity);
	}

    private static Mat normalize(Mat img) {
        Mat resized = new Mat();
        double newHeight = 300;
        double newWidth = img.width() / (img.height() / newHeight);
        Imgproc.resize(img, resized, new Size(newWidth, newHeight));
        return resized;
    }

    private static Mat histogram(Mat img) {
        Mat hist = new Mat();
        MatOfInt channels = new MatOfInt(0, 1);
        Mat mask = new Mat();
        MatOfInt histSize = new MatOfInt(50, 60);
        MatOfFloat ranges = new MatOfFloat(0, 180, 0, 256);
        Imgproc.calcHist(Arrays.asList(img), channels, mask, hist, histSize, ranges);
        return hist;
    }
}
