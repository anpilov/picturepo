package com.anpilov.picturepo.client.gallery;

import com.google.common.io.Files;

import java.io.File;

public class GalleryUtils {

	public static File getResizedFile(File originalFile) {
		return getProcessedFile(originalFile, "resized");
	}

	public static File getThumbnailFile(File originalFile) {
		return getProcessedFile(originalFile, "thumbnail");
	}

	private static File getProcessedFile(File original, String modifier) {
		String originalDir = original.getParent();
		File picturepoDir = new File(originalDir, ".picturepo");
		File modifierDir = new File(picturepoDir, modifier);
		String name = Files.getNameWithoutExtension(original.getName());
		String extension = Files.getFileExtension(original.getName());
		String processedName = name + "_" + modifier + "." + extension;
		return new File(modifierDir, processedName);
	}
}
