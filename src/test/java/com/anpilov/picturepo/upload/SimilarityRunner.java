package com.anpilov.picturepo.upload;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class SimilarityRunner {

    public static void main(String[] args) throws IOException {
        MetadataWalker metadataWalker = new MetadataWalker();
        ClientGalleryMetadata gallery = metadataWalker.walk(new File("/Users/andem/amsterdam_photo"));

        SimilarityCalculator calculator = new SimilarityCalculator();
        calculator.calculate(gallery.getImages());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(gallery);
        System.out.println(json);
        Files.write(json, new File("metadata.json"), Charset.defaultCharset());
    }

}
