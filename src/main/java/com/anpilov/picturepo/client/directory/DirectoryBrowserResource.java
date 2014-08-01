package com.anpilov.picturepo.client.directory;

import com.anpilov.picturepo.common.ApplicationException;
import com.anpilov.picturepo.common.ImageFilenameFilter;
import com.google.common.base.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.File;
import java.io.FileFilter;

@Controller
@RequestMapping("/directory")
public class DirectoryBrowserResource {

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DirectoryDescriptor getDirectory(@RequestParam(value = "path", required = false) String path) {
		File directory = Strings.isNullOrEmpty(path) ? new File(System.getProperty("user.dir")) : new File(path);
		if (!directory.isDirectory()) {
			throw new ApplicationException("path not directory: " + path);
		}

		DirectoryDescriptor descriptor = new DirectoryDescriptor(directory);
		descriptor.setImageCount(directory.listFiles(new ImageFilenameFilter()).length);

		for (File subdir : directory.listFiles(new DirectoryFilter())) {
			if (isGallery(subdir)) {
				descriptor.getGalleries().add(new DirectoryDescriptor(subdir));
			} else {
				descriptor.getSubdirs().add(new DirectoryDescriptor(subdir));
			}
		}

		return descriptor;
	}

	private boolean isGallery(File dir) {
		return false;
	}

	static class DirectoryFilter implements FileFilter {

		@Override
		public boolean accept(File file) {
			return file.isDirectory();
		}

	}

}
