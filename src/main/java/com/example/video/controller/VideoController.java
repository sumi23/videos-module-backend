package com.example.video.controller;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("video")
public class  VideoController
{
	@Autowired
	private VideoServiceImpl videoService;
	
	 private static final String UPLOAD_DIRECTORY ="E:\\videomodule\\files";  
	 
	
	@GetMapping(value="/list")
	public List<Video> doGetAllVideos() throws Exception{
		List<Video> videos;
		try {
			videos = (List<Video>)videoService.getAllVideos();
		} catch (ServiceException e) {
			throw new ServiceException("Error in fetching video records", e);
		}
		return videos;	
	}
	@GetMapping(value="/listLevels")
	public List<Level> doGetAllLevels() throws Exception{
		List<Level> videos;
		try {
			videos = (List<Level>)videoService.getAllLevels();
		} catch (ServiceException e) {
			throw new ServiceException("Error in fetching video records", e);
		}
		
		return videos;	
		
	}
	
	@GetMapping(value="/listCategories")
	public List<Category> doGetAllCategories() throws Exception{
		List<Category> videos;
		try {
			videos = (List<Category>)videoService.getAllCategories();
		} catch (ServiceException e) {
			throw new ServiceException("Error in fetching video records", e);
		}
		return videos;	
	}
	
	
	@GetMapping(value="/listActive")
	public List<Video> doGetActivatedVideos() throws Exception{
		List<Video> videos;
		try {
			videos = (List<Video>)videoService.getActivatedVideos();
		} catch (ServiceException e) {
			throw new ServiceException("Error in fetching activated video records", e);
		}
		return videos;	
	}
	@GetMapping(value="/listDeactive")
	public List<Video> doGetDeactivatedVideos() throws Exception{
		List<Video> videos;
		try {
			videos = (List<Video>)videoService.getDeactivatedVideos();
		} catch (ServiceException e) {
			throw new ServiceException("Error in fetching deactivated video records", e);
		}
		return videos;	
	}
	@GetMapping(value="/listById/{videoId}")
   public List<Video> doGetVideoById(@PathVariable int videoId) throws Exception{
		
		List<Video> video;
		try {
			video=(List<Video>)videoService.getVideoById(videoId);
		}
		catch (ServiceException e) {
			throw new ServiceException("Error in fetching video records");
		}
		return video;
	}
	@DeleteMapping(value="/deleteById/{videoId}")
	   public List<Video> doDeleteVideoById(@PathVariable int videoId) throws Exception{
			
			List<Video> video;
			try {
				video=(List<Video>)videoService.deleteVideoById(videoId);
			}
			catch (ServiceException e) {
				throw new ServiceException("Error in deleting video records",e);
			}
			return video;
		}
	
	@GetMapping(value="/toggleStatus/{videoId}")
	   public void doToggleStatus(@PathVariable int videoId) throws Exception{
			
			
			try {
				videoService.toggleStatus(videoId);
			}
			catch (ServiceException e) {
				throw new ServiceException("Error in changing status records",e);
			}
			
		}
	@PostMapping(value="/add",produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public 	ResponseEntity<?> doaddVideos(@RequestBody Video video)throws Exception
	{
//		videoService.addVideo(video);	
//		return ResponseUtils.prepareSuccessResponse(RESTUriConstant.DATA_INSERT_SUCCESS,video);
		
		Video videos=null;
		try {
			
			videos =videoService.addVideo(video);
		} 
		catch (DBException e) {
			throw new ServiceException("Video already exists",e);
			
		}
		catch (ServiceException e) {
			throw new ServiceException("Error in adding video records",e);
			
		}
		
			 return new ResponseEntity<>(videos, HttpStatus.OK);
	
		}
	@PutMapping(value="/edit",produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public 	ResponseEntity<?> doEditVideos(@RequestBody Video video) throws Exception
	{
		
         try {
			
		videoService.updateVideo(video);
		} 
		
		catch (ServiceException e) {
			throw new ServiceException("Error in updating video records",e);
			
		}
		
			 return new ResponseEntity<>(video, HttpStatus.OK);
		
	}
	//produces = MediaType.MULTIPART_FORM_DATA_VALUE
	@PostMapping(value="/upload",headers = "Content-Type= multipart/form-data",produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) throws IOException,ServiceException {
		
		 
		try {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename()); 
		 
		byte[] bytes = file.getBytes();  
		BufferedOutputStream stream =new BufferedOutputStream(new FileOutputStream(  
		         new File(UPLOAD_DIRECTORY + File.separator + fileName)));  
		System.out.println(UPLOAD_DIRECTORY+File.separator+ fileName);
		stream.write(bytes);  
		stream.flush();  
		stream.close();
		} catch (IOException e) {
			throw new  ServiceException("Error in uploading files");
		}	
		return ResponseEntity.ok("File uploaded");
	}
	@GetMapping(value="/download/{fileName:.+}",produces="text/plain")
	public ResponseEntity<String> doDownloadFileFromLocal(@PathVariable String fileName) throws Exception
		{
		 ResponseEntity<String> response;
			try {
			 response=videoService.downloadFileFromLocal(fileName);
			}
			catch(ServiceException e)
			{
				throw new ServiceException("Error in downloading files",e);
			}
			return response;
	}
		
}	