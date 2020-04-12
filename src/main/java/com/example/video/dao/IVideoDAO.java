package com.example.video.dao;

import java.util.List;

import com.example.video.exception.DBException;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.ReferenceArtifact;
import com.example.video.model.ReferenceUrl;
import com.example.video.model.SampleProgram;
import com.example.video.model.Video;

interface IVideoDAO {

	List<Video> getAllVideos();

	Video getVideoById(int id);

	void addVideo(Video video);

	void deleteVideoById(int videoId);

	List<Video> getDeactivatedVideos();

	List<Video> getActivatedVideos();

	void updateVideo(Video video);

	void deleteReferenceArtifactById(int videoId);

	void deleteSampleProgramById(int videoId);

	void deleteReferenceUrlById(int videoId);

	void toggleStatus(int videoId);

	List<Level> getAllLevels();

	List<Category> getAllCategories();

	ReferenceArtifact getReferenceArtifactById(int videoId) ;

	ReferenceUrl getReferenceUrlById(int id);

	SampleProgram getSampleProgramById(int id);
}
