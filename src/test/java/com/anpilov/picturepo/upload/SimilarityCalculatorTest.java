package com.anpilov.picturepo.upload;

import com.google.common.io.Resources;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class SimilarityCalculatorTest {

	@Test
	public void testCalculateSampleImages() {
        List<ClientImageMetadata> images = new ArrayList<>();
        File imagesDir = new File(Resources.getResource("images").getFile());
        for (File file : imagesDir.listFiles()) {
            ClientImageMetadata image = new ClientImageMetadata();
            image.setPath(file.getAbsolutePath());
            images.add(image);
        }

		SimilarityCalculator calculator = new SimilarityCalculator();
        calculator.calculate(images);
        String group1 = images.get(0).getSimilarityGroup();
        String group2 = images.get(2).getSimilarityGroup();
        assertNotNull(group1);
        assertNotNull(group2);
        assertNotEquals(group1, group2);

        for (int i = 0; i < 2; i++) {
            assertEquals(group1, images.get(i).getSimilarityGroup());
        }
        for (int i = 2; i < 7; i++) {
            assertEquals(group2, images.get(i).getSimilarityGroup());
        }
	}
}
