package com.example.video.service;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.video.dao.VideoDAOImpl;
import com.example.video.exception.DBException;
import com.example.video.exception.ServiceException;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.Video;

@Service
public class VideoServiceImpl implements IVideoService {
	@Autowired
	private VideoDAOImpl videodao;
	
	 private static final String UPLOAD_DIRECTORY ="E:/videomodule/files/";

	@Override
	public List<Video> getAllVideos() throws ServiceException {
		List<Video> videos;
		try {
			 System.out.print("service started");
			videos = videodao.getAllVideos();
		} catch (Exception e) {
			throw new ServiceException("Unable to fetch records for video", e);
		}
		return videos;
	}
	
	@Override
	public List<Level> getAllLevels() throws ServiceException {
		List<Level> levels;
		try {
			 System.out.print("service started");
			 levels = videodao.getAllLevels();
		} catch (Exception e) {
			throw new ServiceException("Unable to fetch records for video", e);
		}
		return levels;
	}	
	@Override
	public List<Category> getAllCategories() throws ServiceException {
		List<Category> levels;
		try {
			 System.out.print("service started");
			 levels = videodao.getAllCategories();
		} catch (Exception e) {
			throw new ServiceException("Unable to fetch records for video", e);
		}
		return levels;
	}
	
	@Override
	public List<Video> getVideoById(int videoId) throws ServiceException
	{
		List<Video> video;
		try {
			video=(List<Video>)videodao.getVideoById(videoId);
		}
		catch (DBException e) {
		throw new ServiceException("Unable to fetch records for video", e);
		}
		return video;
	}
	@Override
	public List<Video> getActivatedVideos() throws ServiceException {
		List<Video> videos;
		try {
			videos = (List<Video>) videodao.getActivatedVideos();
		} catch (Exception e) {
			throw new ServiceException("Unable to fetch activated video records", e);
		}
		return videos;
	}
	@Override
	public List<Video> getDeactivatedVideos() throws ServiceException {
		List<Video> videos;
		try {
			videos = (List<Video>) videodao.getDeactivatedVideos();
		} catch (Exception e) {
			throw new ServiceException("Unable to fetch deactivated video records", e);
		}
		return videos;
	}
	
	@Override
	public void toggleStatus(int videoId) throws ServiceException
	{
		try {
			videodao.toggleStatus(videoId);
		} 
		catch (DBException e) {
			throw new ServiceException("Unable to toggle status");
		}
	}
	@Override
	public List<Video> deleteVideoById(int videoId) throws ServiceException
	{
		List<Video> video;
		try {
			video=(List<Video>)videodao.deleteVideoById(videoId);
		}
		catch (DBException e) {
			throw new ServiceException("Unable to delete records for video");
		}
		return video;
	}
	@Override
	public Video addVideo(Video video) throws ServiceException{
		try {
			videodao.addVideo(video);
		} 
//		catch (DBException e) {
//			throw new  DBException("name and url must be unique",e);
//		}
		catch (PersistenceException e) {
			System.out.println("cause is "+e.getCause().toString());
			if(e.getCause().toString().contains("ConstraintViolationException"))
			{
				System.out.println("cause is new");
			  throw new DBException("Video name mustbbb be unique",e);
			}
		} 
		catch(DBException e) {
			throw new DBException("Video name must be unique",e);
		}
		return video;
	}
	@Override
	public Video updateVideo(Video video) throws ServiceException{
		try {
			videodao.updateVideo(video);
		} 
		catch (DBException e) {
			throw new ServiceException("Unable to update records for video");
		}
		return video;
	}
	@Override
	public ResponseEntity<String> downloadFileFromLocal(String fileName)throws ServiceException {
		String contentType="text/plain";
		Path path = Paths.get(UPLOAD_DIRECTORY + fileName);
		System.out.println(path);
		System.out.println(path.toUri());
		Resource resource = null;
		String encodedString;
		String decodedString;
		try {
			//resource = new UrlResource(path.toUri());
			resource=new FileSystemResource(path);
			    
		    byte[] bytes =Files.readAllBytes(path);
		    Base64.Encoder encoder = Base64.getEncoder();
		    byte[] encoded=encoder.encode(bytes);
		    encodedString = new String(encoded);
		    
		    Base64.Decoder decoder = Base64.getDecoder();  
		    byte[] decoded=decoder.decode(encoded);
		    decodedString=new String(decoded);
		    
		} 
		catch (Exception e) {
			throw new ServiceException("Unable to download files for video", e);
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(encodedString);
		//return encodedString;
		
	}

}