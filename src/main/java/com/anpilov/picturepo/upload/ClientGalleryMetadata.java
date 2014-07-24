package com.anpilov.picturepo.upload;

import java.util.ArrayList;
import java.util.List;

public class ClientGalleryMetadata {

    private String id;
    private String name;

    private List<ClientImageMetadata> images = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClientImageMetadata> getImages() {
        return images;
    }

    public void setImages(List<ClientImageMetadata> images) {
        this.images = images;
    }
}
