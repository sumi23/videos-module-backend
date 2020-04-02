package com.example.video.service;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

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
public class VideoServiceImpl implements IVideoService,Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private VideoDAOImpl videodao;
	
	 private static final String UPLOAD_DIRECTORY ="E:/videomodule/files/";

	@Override
	public List<Video> getAllVideos() throws ServiceException {
		List<Video> videos;
		try {
			videos = videodao.getAllVideos();
			if(videos.isEmpty()) {
				throw new ServiceException("No records found");
			}
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}
		return videos;
	}
	
	@Override
	public List<Level> getAllLevels() throws ServiceException {
		List<Level> levels;
		try {
			 levels = videodao.getAllLevels();
		} catch (Exception e) {
			throw new ServiceException("Unable to fetch levels", e);
		}
		return levels;
	}	
	@Override
	public List<Category> getAllCategories() throws ServiceException {
		List<Category> levels;
		try {
			 levels = videodao.getAllCategories();
		} catch (Exception e) {
			throw new ServiceException("Unable to fetch categories", e);
		}
		return levels;
	}
	
	@Override
	public Video getVideoById(int videoId) throws ServiceException
	{
	    Video video;
		try {
			video=videodao.getVideoById(videoId);
			if(video==null)
			{
				throw new ServiceException("No video records found");
			}
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
			if(videos==null)
			{
				throw new ServiceException("No video records found");
			}
		} catch (DBException e) {
			throw new ServiceException("Unable to fetch activated video records", e);
		}
		return videos;
	}
	@Override
	public List<Video> getDeactivatedVideos() throws ServiceException {
		List<Video> videos;
		try {
			videos = (List<Video>) videodao.getDeactivatedVideos();
			if(videos==null)
			{
				throw new ServiceException("No video records found");
			}
		} catch (DBException e) {
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
	public void deleteVideoById(int videoId) throws ServiceException
	{
		List<Video> video;
		try {
			
			videodao.deleteVideoById(videoId);
//			if(video.isEmpty())
//			{
//				throw new ServiceException("No video records found");
//			}
		}
		catch (DBException e) {
			throw new ServiceException("Unable to delete records for video");
		}
		
	}
	@Override
	public void deleteReferenceUrlById(int videoId) throws ServiceException
	{
		
		try {	
			videodao.deleteReferenceUrlById(videoId);
		}
		catch (DBException e) {
			throw new ServiceException("Unable to delete reference url records for video");
		}
		
	}
	@Override
	public void addVideo(Video video) throws ServiceException{
		try {
			videodao.addVideo(video);	
		} 
		catch(DBException e) {
			throw new ServiceException("Video already exists");
		}
		
	}
	@Override
	public void updateVideo(Video video) throws ServiceException{
		try {
			videodao.updateVideo(video);
		} 
		catch (DBException e) {
			throw new ServiceException("Unable to update records for video");
		}
		
	}
	@Override
	public String downloadFileFromLocal(String fileName)throws ServiceException {
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
		catch(IOException e)
		{
		       throw new ServiceException("Error in downloading files");	
		
		}
		 return encodedString;
		}
}