package com.anpilov.picturepo.upload;

import com.google.common.io.Files;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MetadataRenderer {

    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        ClientGalleryMetadata gallery = gson.fromJson(new FileReader(new File("metadata.json")), ClientGalleryMetadata.class);

        List<SimilarityGroup> groups = new ArrayList<>();
        SimilarityGroup currentGroup = null;
        for (ClientImageMetadata image : gallery.getImages()) {
            if (currentGroup == null
                    || image.getSimilarityGroup() == null
                    || !image.getSimilarityGroup().equals(currentGroup.getId())) {
                currentGroup = new SimilarityGroup(image.getSimilarityGroup());
                groups.add(currentGroup);
            }
            currentGroup.getImages().add(image);
        }

        StringBuilder html = new StringBuilder();
        html.append("<html>\n");
        html.append("<head></head>\n");
        html.append("<body>\n");

        int i = 0;
        for (SimilarityGroup group : groups) {
            if (i < 20) {
                for (ClientImageMetadata image : group.getImages()) {
                    html.append("<img src=\"" + image.getPath() + "\" height=\"100px\" width=\"100px\" >");
                }
                i++;
            } else {
                html.append("images: " + group.getImages().size());
            }
            html.append("<br />\n");
        }

        html.append("</body>\n");
        html.append("</html>\n");

        Files.write(html.toString(), new File("similarities.html"), Charset.defaultCharset());
    }

    static class SimilarityGroup {

        private String id;
        private List<ClientImageMetadata> images = new ArrayList<>();

        SimilarityGroup(String id) {
            this.id = id;
        }

        String getId() {
            return id;
        }

        void setId(String id) {
            this.id = id;
        }

        List<ClientImageMetadata> getImages() {
            return images;
        }

        void setImages(List<ClientImageMetadata> images) {
            this.images = images;
        }
    }
}
