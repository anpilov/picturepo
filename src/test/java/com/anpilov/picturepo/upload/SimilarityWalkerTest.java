package com.anpilov.picturepo.upload;

import com.google.common.io.Resources;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class SimilarityWalkerTest {

	@Test
	public void testWalkSampleImages() {
		SimilarityWalker walker = new SimilarityWalker();
		SimilarityContainer container = walker.walk(new File(Resources.getResource("images").getFile()));
		assertEquals(2, container.getSimilarityGroups().size());
	}
}
