package com.anpilov.picturepo.upload;

import com.anpilov.picturepo.image.ImageComparison;

import java.io.File;

public class SimilarityWalker {

	public SimilarityContainer walk(File directory) {
		SimilarityContainer container = new SimilarityContainer();

		SimilarityGroup currentGroup = null;
		File lastImage = null;
		File[] files = directory.listFiles();
		for (File image : files) {
			if (isImage(image)) {
				if (lastImage == null || !ImageComparison.compare(lastImage, image).areSimilar()) {
					currentGroup = new SimilarityGroup();
					container.getSimilarityGroups().add(currentGroup);
					currentGroup.getImages().add(image);
				} else {
					currentGroup.getImages().add(image);
				}
				lastImage = image;
			}
		}

		return container;
	}

	// TODO implement
	private boolean isImage(File file) {
		return file.getName().toLowerCase().endsWith(".jpeg");
	}
}
