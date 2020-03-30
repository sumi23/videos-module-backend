package com.example.video.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.example.video.exception.DBException;
import com.example.video.exception.ServiceException;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.Video;

public interface IVideoService {
	
	List<Video> getAllVideos() throws ServiceException;

	Video getVideoById(int videoId) throws ServiceException;

	void addVideo(Video video) throws ServiceException, DBException;

	void deleteVideoById(int videoId) throws ServiceException;

	List<Video> getActivatedVideos() throws ServiceException;

	List<Video> getDeactivatedVideos() throws ServiceException;

	void updateVideo(Video video) throws ServiceException;

	String downloadFileFromLocal(String fileName) throws ServiceException;

	void toggleStatus(int videoId) throws ServiceException;

	List<Level> getAllLevels() throws ServiceException;

	List<Category> getAllCategories() throws ServiceException;

	void deleteReferenceUrlById(int videoId) throws ServiceException;
}
