package com.anpilov.picturepo.upload;

import com.anpilov.picturepo.common.GuidGenerator;
import com.anpilov.picturepo.image.ImageComparison;

import java.io.File;
import java.util.Collection;

public class SimilarityCalculator {

	public void calculate(Collection<ClientImageMetadata> images) {
        String currentGroup = null;
        ClientImageMetadata previousImage = null;

        int i = 1;
        for (ClientImageMetadata currentImage : images) {
            System.out.println("processing image " + (i++) + " / " + images.size());
            if (previousImage == null || !areSimilar(previousImage, currentImage)) {
                currentGroup = null;
            } else {
                if (currentGroup == null) {
                    currentGroup = GuidGenerator.nextId();
                    previousImage.setSimilarityGroup(currentGroup);
                }
                currentImage.setSimilarityGroup(currentGroup);
            }
            previousImage = currentImage;
        }
	}

    private boolean areSimilar(ClientImageMetadata image1, ClientImageMetadata image2) {
        File file1 = new File(image1.getPath());
        File file2 = new File(image2.getPath());
        return ImageComparison.compare(file1, file2).areSimilar();
    }
}
