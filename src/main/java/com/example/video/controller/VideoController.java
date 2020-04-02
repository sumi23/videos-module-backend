package com.example.video.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.video.constants.RESTUriConstant;
import com.example.video.exception.DBException;
import com.example.video.exception.ServiceException;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.Video;
import com.example.video.service.VideoServiceImpl;
import com.example.video.util.HTTPStatusResponse;
import com.example.video.util.ResponseUtils;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class VideoController {
	@Autowired
	private VideoServiceImpl videoService;

	private static final String UPLOAD_DIRECTORY = "E:\\videomodule\\files";

	@GetMapping(value = "/list")
	public ResponseEntity<HTTPStatusResponse> doGetAllVideos() throws Exception {
		List<Video> videos;
		try {
			videos = videoService.getAllVideos();
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), "Data retrival success", videos),
				HttpStatus.OK);
	}

	@GetMapping(value = "/listLevels")
	public ResponseEntity<HTTPStatusResponse> doGetAllLevels() throws Exception {
		List<Level> videos;
		try {
			videos = videoService.getAllLevels();
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), "Data retrival success", videos),
				HttpStatus.OK);

	}

	@GetMapping(value = "/listCategories")
	public ResponseEntity<HTTPStatusResponse> doGetAllCategories() throws Exception {
		List<Category> categories;
		try {
			categories = videoService.getAllCategories();
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), "Data retrival success", categories),
				HttpStatus.OK);
	}

	@GetMapping(value = "/listActive")
	public ResponseEntity<HTTPStatusResponse> doGetActivatedVideos() throws Exception {
		List<Video> videos;
		try {
			videos = (List<Video>) videoService.getActivatedVideos();
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), "Data retrival success", videos),
				HttpStatus.OK);
	}

	@GetMapping(value = "/listDeactive")
	public ResponseEntity<HTTPStatusResponse> doGetDeactivatedVideos() throws Exception {
		List<Video> videos;
		try {
			videos = videoService.getDeactivatedVideos();
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), "Data retrival success", videos),
				HttpStatus.OK);
	}

	@GetMapping(value = "/listById/{videoId}")
	public ResponseEntity<HTTPStatusResponse> doGetVideoById(@PathVariable int videoId) throws Exception {

		Video video;
		try {
			video = videoService.getVideoById(videoId);
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), "Data retrival success", video),
				HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteById/{videoId}")
	public ResponseEntity<HTTPStatusResponse> doDeleteVideoById(@PathVariable int videoId) throws Exception {

		try {
			videoService.deleteVideoById(videoId);
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), "Data deletion success"),
				HttpStatus.OK);

	}

	@DeleteMapping(value = "/deleteReferenceUrlById/{videoId}")
	public ResponseEntity<HTTPStatusResponse> doDeleteReferenceUrlById(@PathVariable int videoId) throws Exception {

		try {
			videoService.deleteReferenceUrlById(videoId);
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), "Data deletion success"),
				HttpStatus.OK);

	}

	@GetMapping(value = "/toggleStatus/{videoId}")
	public void doToggleStatus(@PathVariable int videoId) throws Exception {

		try {
			videoService.toggleStatus(videoId);
		} catch (ServiceException e) {
			throw new ServiceException("Error in changing status records", e);
		}

	}

	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> doaddVideos(@RequestBody Video video) throws Exception {

		try {

			videoService.addVideo(video);
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
					HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), "Data creation success"),
				HttpStatus.OK);

	}

	@PutMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> doEditVideos(@RequestBody Video video) throws Exception {

		try {

			videoService.updateVideo(video);
		}

		catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
					HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), "Data creation success"),
				HttpStatus.OK);

	}

	// produces = MediaType.MULTIPART_FORM_DATA_VALUE
	@PostMapping(value = "/upload", headers = "Content-Type= multipart/form-data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity uploadToLocalFileSystem(@RequestParam("file") MultipartFile file)
			throws IOException, ServiceException {

		try {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			byte[] bytes = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(UPLOAD_DIRECTORY + File.separator + fileName)));
			System.out.println(UPLOAD_DIRECTORY + File.separator + fileName);
			stream.write(bytes);
			stream.flush();
			stream.close();
		} catch (IOException e) {
			throw new ServiceException("Error in uploading files");
		}
		return ResponseEntity.ok("File uploaded");
	}

	@GetMapping(value = "/download/{fileName:.+}", produces = "text/plain")
	public ResponseEntity<String> doDownloadFileFromLocal(@PathVariable String fileName) throws Exception {
		String response;
		String contentType = "text/plain";
		try {
			response = videoService.downloadFileFromLocal(fileName);

		} catch (Exception e) {
			throw new ServiceException("Unable to download files for video", e);
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(response);
		// return encodedString;
	}

}