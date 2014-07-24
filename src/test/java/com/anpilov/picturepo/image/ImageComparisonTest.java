package com.anpilov.picturepo.image;

import com.google.common.io.Resources;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ImageComparisonTest {

	@Test
	public void testIsDuplicate() {
		assertTrue(ImageComparison.compare(image("1_1"), image("1_2")).areSimilar());
		assertFalse(ImageComparison.compare(image("1_2"), image("2_1")).areSimilar());
		assertTrue(ImageComparison.compare(image("2_1"), image("2_2")).areSimilar());
		assertTrue(ImageComparison.compare(image("2_2"), image("2_3")).areSimilar());
		assertTrue(ImageComparison.compare(image("2_3"), image("3_1")).areSimilar());
		assertTrue(ImageComparison.compare(image("3_2"), image("3_2")).areSimilar());
	}

	private File image(String partial) {
		URL resource = Resources.getResource("images/img" + partial + ".jpeg");
		return new File(resource.getFile());
	}
}
