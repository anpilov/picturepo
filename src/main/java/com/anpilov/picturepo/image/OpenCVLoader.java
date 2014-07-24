package com.anpilov.picturepo.image;

import com.anpilov.picturepo.common.ApplicationException;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class OpenCVLoader {

	public static final String TEMP_DIR_NAME = "picturepo";
	public static final String OPENCV_DLL_NAME = "opencv_java249.dll";

	public static void load() {
		File baseDir = new File(System.getProperty("java.io.tmpdir"));
		File tempDir = new File(baseDir, TEMP_DIR_NAME);
		if (!tempDir.exists() && !tempDir.mkdirs()) {
			throw new ApplicationException("Failed to create temp folder for OpenCV dll at " + tempDir.getAbsolutePath());
		}

		File resourceFile = new File(getDllResource().getFile());
		File existingFile = new File(tempDir, OPENCV_DLL_NAME);
		if (!existingFile.exists() || existingFile.length() != resourceFile.length()) {
			try {
				Files.copy(resourceFile, existingFile);
			} catch (IOException e) {
				throw new ApplicationException("Failed to copy OpenCV dll to temp folder at " + tempDir.getAbsolutePath());
			}
		}

		System.load(existingFile.getAbsolutePath());
	}

	static URL getDllResource() {
		String arch = System.getProperty("os.arch").endsWith("64") ? "x64" : "x86";
		return Resources.getResource("opencv/" + arch + "/" + OPENCV_DLL_NAME);
	}

}
