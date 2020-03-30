package com.example.video.dao;

import java.util.List;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.Video;

interface IVideoDAO {

	List<Video> getAllVideos();

	Video getVideoById(int id);

	void addVideo(Video video);

	void deleteVideoById(int videoId);

	List<Video> getDeactivatedVideos();

	List<Video> getActivatedVideos();

	void updateVideo(Video video);

	List<Video> deleteReferenceArtifactById(int videoId);

	List<Video> deleteSampleProgramById(int videoId);

	List<Video> deleteReferenceUrlById(int videoId);

	List<Video> deactivateVideo(int videoId);

	void toggleStatus(int videoId);

	List<Level> getAllLevels();

	List<Category> getAllCategories();
}
