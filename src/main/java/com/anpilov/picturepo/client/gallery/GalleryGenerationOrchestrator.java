package com.anpilov.picturepo.client.gallery;

import com.anpilov.picturepo.client.gallery.cache.GalleryCache;
import com.anpilov.picturepo.client.gallery.cache.GalleryGenerationStatus;
import com.anpilov.picturepo.client.gallery.dto.GalleryMetadata;
import com.anpilov.picturepo.client.gallery.dto.ImageMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class GalleryGenerationOrchestrator {

	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	@Autowired
	private GalleryCache galleryCache;
	@Autowired
	private ImageProcessingService imageProcessingService;

	public void generateGallery(GalleryMetadata galleryMetadata) {
		GalleryGenerationStatus status = galleryCache.getStatus(galleryMetadata.getPath());
		status.start(galleryMetadata.getImages().size());
		Future<ProcessedImage> previousImage = null;
		for (ImageMetadata imageMetadata : galleryMetadata.getImages()) {
			Future<ProcessedImage> image = executorService.submit(new ImageProcessingTask(previousImage, imageMetadata, status));
			previousImage = image;
		}
	}

	private class ImageProcessingTask implements Callable<ProcessedImage> {

		private Future<ProcessedImage> previousImage;
		private ImageMetadata imageMetadata;
		private GalleryGenerationStatus status;

		private ImageProcessingTask(Future<ProcessedImage> previousImage, ImageMetadata imageMetadata, GalleryGenerationStatus status) {
			this.previousImage = previousImage;
			this.imageMetadata = imageMetadata;
			this.status = status;
		}

		@Override
		public ProcessedImage call() throws Exception {
			ProcessedImage processedImage = imageProcessingService.process(imageMetadata);
			if (previousImage != null) {
				imageProcessingService.calculateSimilarity(previousImage.get(), processedImage);
			}
			status.addProgress(imageMetadata);
			return processedImage;
		}
	}

}
