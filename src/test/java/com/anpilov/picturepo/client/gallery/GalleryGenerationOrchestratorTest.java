package com.anpilov.picturepo.client.gallery;

import com.anpilov.picturepo.client.gallery.cache.GalleryGenerationStatus;
import com.anpilov.picturepo.client.gallery.dto.GalleryMetadata;
import com.anpilov.picturepo.client.gallery.dto.ImageMetadata;
import com.google.common.base.Stopwatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class GalleryGenerationOrchestratorTest {

	@Spy
	private GalleryGenerationStatus galleryGenerationStatus = new GalleryGenerationStatus();
	@Mock
	private ImageProcessingService imageProcessingService;

	@InjectMocks
	private	GalleryGenerationOrchestrator orchestrator = new GalleryGenerationOrchestrator();

	@Test
	public void testCalculationSequence() throws InterruptedException {
		List<ImageMetadata> images = new ArrayList<>();
		int imageCount = 50;
		for (int i = 0; i < imageCount; i++) {
			images.add(image(String.valueOf(i)));
		}
		GalleryMetadata gallery = new GalleryMetadata();
		gallery.setImages(images);

		final Random random = new Random();
		Mockito.when(imageProcessingService.process(Mockito.any(ImageMetadata.class))).then(new Answer<ProcessedImage>() {
			@Override
			public ProcessedImage answer(InvocationOnMock invocation) throws Throwable {
				Thread.sleep(50 + random.nextInt(50));
				return new ProcessedImage((ImageMetadata) invocation.getArguments()[0]);
			}
		});

		orchestrator.generateGallery(gallery);

		Stopwatch stopwatch = Stopwatch.createStarted();
		while (galleryGenerationStatus.isRunning()) {
			Thread.sleep(100);
		}
		long ellapsedMillis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		assertTrue("total time should be less than sum of processing times, since we are multithreaded", ellapsedMillis < imageCount * 50);

		ArgumentCaptor<ProcessedImage> previousImage = ArgumentCaptor.forClass(ProcessedImage.class);
		ArgumentCaptor<ProcessedImage> currentImage = ArgumentCaptor.forClass(ProcessedImage.class);
		Mockito.verify(imageProcessingService, times(imageCount - 1)).calculateSimilarity(previousImage.capture(), currentImage.capture());

		Iterator<ProcessedImage> previousValueIterator = previousImage.getAllValues().iterator();
		Iterator<ProcessedImage> currentValueIterator = currentImage.getAllValues().iterator();
		for (int i = 1; i < imageCount; i++) {
			assertEquals(images.get(i - 1), previousValueIterator.next().getImageMetadata());
			assertEquals(images.get(i), currentValueIterator.next().getImageMetadata());
		}
	}

	private ImageMetadata image(String name) {
		ImageMetadata imageMetadata = new ImageMetadata();
		imageMetadata.setName(name);
		return imageMetadata;
	}
}
