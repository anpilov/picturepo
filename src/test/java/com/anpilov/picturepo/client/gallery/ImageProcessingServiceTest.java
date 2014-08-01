package com.anpilov.picturepo.client.gallery;

import com.anpilov.picturepo.client.gallery.dto.ImageMetadata;
import com.google.common.io.Resources;
import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ImageProcessingServiceTest {

	private ImageProcessingService service = new ImageProcessingService();

	@Test
	public void testProcessSampleImages() throws IOException {
		File tempDir = Files.createTempDirectory("ImageProcessingServiceTest").toFile();
		tempDir.deleteOnExit();

		File picturepoDir = new File(tempDir, ".picturepo");
		File thumbnailsDir = new File(picturepoDir, "thumbnail");
		if (!thumbnailsDir.mkdirs()) {
			fail("failed to create temp dir " + thumbnailsDir.getAbsolutePath());
		}

		List<ImageMetadata> metadatas = new ArrayList<>();
		File imagesDir = new File(Resources.getResource("images").getFile());
		for (File file : imagesDir.listFiles()) {
			File tempImage = new File(tempDir, file.getName());
			Files.copy(Paths.get(file.toURI()), Paths.get(tempImage.toURI()));
			ImageMetadata metadata = new ImageMetadata();
			metadata.setOriginalPath(tempImage.getAbsolutePath());
			metadatas.add(metadata);
		}

		for (ImageMetadata metadata : metadatas) {
			service.process(metadata);
		}

		for (ImageMetadata metadata : metadatas) {
			File thumbnailFile = GalleryUtils.getThumbnailFile(new File(metadata.getOriginalPath()));
			assertTrue(thumbnailFile.exists());
			Mat thumbnail = Highgui.imread(thumbnailFile.getAbsolutePath());
			assertEquals(200, Math.max(thumbnail.size().height, thumbnail.size().width), 0.001);
		}
	}

	@Test
	public void testCalculateSimilarityOnSampleImages() {
		File imagesDir = new File(Resources.getResource("images").getFile());
		List<ProcessedImage> images = new ArrayList<>();
		for (File file : imagesDir.listFiles()) {
			ProcessedImage image = new ProcessedImage(new ImageMetadata());
			Mat imageMat = Highgui.imread(file.getAbsolutePath());
			image.setHistogram(service.histogram(imageMat));
			images.add(image);
		}

		for (int i = 1; i < images.size(); i++) {
			service.calculateSimilarity(images.get(i - 1), images.get(i));
		}

		String group1 = images.get(0).getImageMetadata().getSimilarityGroup();
		String group2 = images.get(2).getImageMetadata().getSimilarityGroup();
		assertNotNull(group1);
		assertNotNull(group2);
		assertNotEquals(group1, group2);

		for (int i = 0; i < 2; i++) {
			assertEquals(group1, images.get(i).getImageMetadata().getSimilarityGroup());
		}
		for (int i = 2; i < 7; i++) {
			assertEquals(group2, images.get(i).getImageMetadata().getSimilarityGroup());
		}
	}
}
