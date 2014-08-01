package com.anpilov.picturepo.common;

import com.google.common.io.Files;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class OpenCVLoader {

	public static final String TEMP_DIR_NAME = "picturepo";

	public static void load() {
		File baseDir = new File(System.getProperty("java.io.tmpdir"));
		File tempDir = new File(baseDir, TEMP_DIR_NAME);
		if (!tempDir.exists() && !tempDir.mkdirs()) {
			throw new ApplicationException("Failed to create temp folder for OpenCV lib at " + tempDir.getAbsolutePath());
		}

		File resourceFile = new File(getLibResource().getFile());
		File existingFile = new File(tempDir, getLibName());
		if (!existingFile.exists() || existingFile.length() != resourceFile.length()) {
			try {
				Files.copy(resourceFile, existingFile);
			} catch (IOException e) {
				throw new ApplicationException("Failed to copy OpenCV lib to temp folder at " + tempDir.getAbsolutePath());
			}
		}

		System.load(existingFile.getAbsolutePath());
	}

	static URL getLibResource() {
		String arch = System.getProperty("os.arch").contains("64") ? "x64" : "x86";
        String path = System.getProperty("os.name").toLowerCase().contains("windows")
                ? "opencv/win/" + arch + "/" + getLibName()
                : "opencv/nix/" + getLibName();
		return Resources.getResource(path);
	}

    static String getLibName() {
        return System.getProperty("os.name").toLowerCase().contains("windows")
                ? "opencv_java249.dll"
                : "libopencv_java249.dylib";
        }

}
