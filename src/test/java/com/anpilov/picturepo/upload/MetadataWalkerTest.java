package com.anpilov.picturepo.upload;

import com.google.common.io.Resources;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MetadataWalkerTest {

    @Test
    public void testWalk() {
        MetadataWalker walker = new MetadataWalker();
        ClientGalleryMetadata gallery = walker.walk(new File(Resources.getResource("images").getFile()));
        assertEquals("images", gallery.getName());
        assertEquals(7, gallery.getImages().size());
        assertEquals("img1_1.jpeg", gallery.getImages().get(0).getName());
        assertTrue(new File(gallery.getImages().get(1).getPath()).exists());

    }
}
