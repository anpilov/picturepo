package com.anpilov.picturepo.common;

import com.google.common.io.Files;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class ImageFilenameFilter implements FilenameFilter {

    private final static List<String> IMAGE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp");

    @Override
    public boolean accept(File dir, String name) {
        return IMAGE_EXTENSIONS.contains(Files.getFileExtension(name.toLowerCase()));
    }
}