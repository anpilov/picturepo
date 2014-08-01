package com.anpilov.picturepo.client.gallery;

import com.anpilov.picturepo.client.gallery.dto.GalleryMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/gallery")
public class GalleryResource {

	@Autowired
	private GalleryService galleryService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public GalleryMetadata loadGallery(@RequestParam("path") String path) {
		return galleryService.loadGallery(path);
	}
}
