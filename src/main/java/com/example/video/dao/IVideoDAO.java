package com.example.video.dao;

import java.util.List;

import com.example.video.exception.DBException;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.Video;

public interface IVideoDAO {
	
	public List<Video> getAllVideos() throws DBException;
	public List<Video> getVideoById(int id) throws DBException ;
	public Video addVideo(Video video) throws DBException;
	List<Video> deleteVideoById(int videoId) throws DBException;
	List<Video> getDeactivatedVideos() throws DBException;
	List<Video> getActivatedVideos() throws DBException;
	Video updateVideo(Video video) throws DBException;
	List<Video> deleteReferenceArtifactById(int videoId) throws DBException;
	List<Video> deleteSampleProgramById(int videoId) throws DBException;
	List<Video> deleteReferenceUrlById(int videoId) throws DBException;
	List<Video> deactivateVideo(int videoId) throws DBException;
	void toggleStatus(int videoId) throws DBException;
	List<Level> getAllLevels() throws DBException;
	List<Category> getAllCategories() throws DBException;
}
