package com.anpilov.picturepo.client.gallery;

import com.anpilov.picturepo.client.gallery.cache.GalleryCache;
import com.anpilov.picturepo.client.gallery.dto.GalleryMetadata;
import com.anpilov.picturepo.client.gallery.dto.ImageMetadata;
import com.anpilov.picturepo.client.gallery.dto.ImageStatus;
import com.anpilov.picturepo.common.ApplicationException;
import com.anpilov.picturepo.common.ImageFilenameFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class GalleryService {

	@Autowired
	private GalleryCache galleryCache;

	public GalleryMetadata loadGallery(String path) {
		if (galleryCache.contains(path)) {
			return galleryCache.getMetadata(path);
		}

		File directory = new File(path);
		if (!directory.exists()) {
			throw new ApplicationException("Directory " + path + " doesn't exist or is not a directory.");
		}

		GalleryMetadata gallery = createGallery(directory);
		galleryCache.add(gallery);
		return gallery;
	}


	public GalleryMetadata createGallery(File directory) {
		GalleryMetadata gallery = new GalleryMetadata();
		gallery.setName(directory.getName());
		gallery.setPath(directory.getAbsolutePath());

		File[] imageFiles = directory.listFiles(new ImageFilenameFilter());
		for (File imageFile : imageFiles) {
			gallery.getImages().add(createImage(imageFile));
		}

		return gallery;
	}

	private ImageMetadata createImage(File imageFile) {
		ImageMetadata image = new ImageMetadata();
		image.setName(imageFile.getName());

		image.setOriginalPath(imageFile.getAbsolutePath());
		image.setThumbnailPath(getProcessedPath(GalleryUtils.getThumbnailFile(imageFile)));

		image.setImageStatus(ImageStatus.SELECTED);

		return image;
	}

	private String getProcessedPath(File processedFile) {
		return processedFile.exists() ? processedFile.getAbsolutePath() : null;
	}
}