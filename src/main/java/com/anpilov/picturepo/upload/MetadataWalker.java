package com.anpilov.picturepo.upload;

import com.anpilov.picturepo.common.GuidGenerator;
import com.anpilov.picturepo.image.ImageComparison;

import java.io.File;

public class MetadataWalker {

    public ClientGalleryMetadata walk(File directory) {
        ClientGalleryMetadata gallery = new ClientGalleryMetadata();
        gallery.setId(GuidGenerator.nextId());
        gallery.setName(directory.getName());

        File[] imageFiles = directory.listFiles(new ImageFilenameFilter());
        for (File imageFile : imageFiles) {
            gallery.getImages().add(createMetadata(imageFile));
        }

        return gallery;
    }

    private ClientImageMetadata createMetadata(File imageFile) {
        ClientImageMetadata image = new ClientImageMetadata();
        image.setId(GuidGenerator.nextId());
        image.setName(imageFile.getName());
        image.setPath(imageFile.getAbsolutePath());
        return image;
    }
}
