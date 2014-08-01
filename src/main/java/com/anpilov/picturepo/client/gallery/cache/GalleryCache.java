package com.anpilov.picturepo.client.gallery.cache;

import com.anpilov.picturepo.client.gallery.dto.GalleryMetadata;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
public class GalleryCache {

	private Map<String, Entry> cache = new HashMap<>();

	public void add(GalleryMetadata metadata) {
		cache.put(metadata.getPath(), new Entry(metadata, new GalleryGenerationStatus()));
	}

	public boolean contains(String path) {
		return cache.containsKey(path);
	}

	public GalleryMetadata getMetadata(String path) {
		return cache.get(path).getMetadata();
	}

	public GalleryGenerationStatus getStatus(String path) {
		return cache.get(path).getStatus();
	}

	static class Entry {

		private GalleryMetadata metadata;
		private GalleryGenerationStatus status;

		Entry(GalleryMetadata metadata, GalleryGenerationStatus status) {
			this.metadata = metadata;
			this.status = status;
		}

		GalleryMetadata getMetadata() {
			return metadata;
		}

		GalleryGenerationStatus getStatus() {
			return status;
		}
	}
}


