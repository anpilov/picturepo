package com.anpilov.picturepo.client.gallery;

import com.anpilov.picturepo.client.gallery.cache.GalleryCache;
import com.anpilov.picturepo.client.gallery.dto.GalleryMetadata;
import com.google.common.io.Resources;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class GalleryServiceTest {

	@Spy
	private GalleryCache cache = new GalleryCache();

	@InjectMocks
	private GalleryService service = new GalleryService();

	@Test
	public void testLoad() {
		GalleryMetadata gallery = service.loadGallery(Resources.getResource("images").getFile());
		assertEquals("images", gallery.getName());
		assertEquals(7, gallery.getImages().size());
		assertEquals("img1_1.jpg", gallery.getImages().get(0).getName());
		assertTrue(new File(gallery.getImages().get(1).getOriginalPath()).exists());
	}

	@Test
	public void testLoadExisting() {
		GalleryMetadata gallery1 = service.loadGallery(Resources.getResource("images").getFile());
		GalleryMetadata gallery2 = service.loadGallery(Resources.getResource("images").getFile());
		assertEquals(gallery1.getPath(), gallery2.getPath());
	}

}
