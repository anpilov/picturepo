package com.anpilov.picturepo.client.gallery;

import com.anpilov.picturepo.client.gallery.dto.ImageMetadata;
import com.anpilov.picturepo.common.GuidGenerator;
import com.anpilov.picturepo.common.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;

@Component
public class ImageProcessingService {

	static {
		OpenCVLoader.load();
	}

	private static final double SIMILARITY_TRESHOLD = 0.9;

	public ProcessedImage process(ImageMetadata imageMetadata) {
		File originalFile = new File(imageMetadata.getOriginalPath());

		Mat originalImage = Highgui.imread(originalFile.getAbsolutePath());
		Mat thumbnailImage = resize(originalImage, 200);

		File thumbnailFile = GalleryUtils.getThumbnailFile(originalFile);
		Highgui.imwrite(thumbnailFile.getAbsolutePath(), thumbnailImage);
		imageMetadata.setThumbnailPath(thumbnailFile.getAbsolutePath());

		ProcessedImage processedImage = new ProcessedImage(imageMetadata);
		processedImage.setHistogram(histogram(originalImage));

		return processedImage;
	}

	Mat resize(Mat originalImage, int maxDimension) {
		Size originalSize = originalImage.size();
		Size newSize = new Size();
		if (originalSize.height > originalSize.width) {
			newSize.height = maxDimension;
			newSize.width = originalSize.width * maxDimension / originalSize.height;
		} else {
			newSize.height = originalSize.height * maxDimension / originalSize.width;
			newSize.width = maxDimension;
		}

		Mat resizedImage = new Mat();
		Imgproc.resize(originalImage, resizedImage, newSize);
		return resizedImage;
	}

	Mat histogram(Mat img) {
		Mat hist = new Mat();
		MatOfInt channels = new MatOfInt(0, 1);
		Mat mask = new Mat();
		MatOfInt histSize = new MatOfInt(50, 60);
		MatOfFloat ranges = new MatOfFloat(0, 180, 0, 256);
		Imgproc.calcHist(Arrays.asList(img), channels, mask, hist, histSize, ranges);
		return hist;
	}

	public void calculateSimilarity(ProcessedImage previous, ProcessedImage current) {
		Mat hist1 = previous.getHistogram();
		Mat hist2 = current.getHistogram();

		boolean similar = hist1 != null && hist2 != null
				&& Imgproc.compareHist(hist1, hist2, Imgproc.CV_COMP_CORREL) >= SIMILARITY_TRESHOLD;

		if (similar) {
			String similarityGroup = previous.getImageMetadata().getSimilarityGroup();
			if (similarityGroup == null) {
				similarityGroup = GuidGenerator.nextId();
			}

			previous.getImageMetadata().setSimilarityGroup(similarityGroup);
			current.getImageMetadata().setSimilarityGroup(similarityGroup);
		}
	}
}
