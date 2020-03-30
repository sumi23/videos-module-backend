package com.example.video.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.video.dao.VideoDAOImpl;
import com.example.video.exception.ServiceException;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.ReferenceArtifact;
import com.example.video.model.ReferenceUrl;
import com.example.video.model.SampleProgram;
import com.example.video.model.Video;

class VideoServiceImplTest {

	@InjectMocks
	VideoServiceImpl videoService;

	@Mock
	VideoDAOImpl videodao;

	@Spy
	List<Level> levelList = new ArrayList<Level>();

	@Spy
	List<Category> categoryList = new ArrayList<Category>();

	@Spy
	List<Video> videoList = new ArrayList<Video>();

	@Spy
	Video video = new Video();
	
	private int id;
	String fileName;
	
	@Captor
	private ArgumentCaptor<String> fileArg;

	@Captor
	private ArgumentCaptor<Integer> arg;

	@Captor
	private ArgumentCaptor<Video> videoArg;
	// ArgumentCaptor<Video> videoArgu=ArgumentCaptor.forClass(Video.class);

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		levelList = getLevelList();
		categoryList = getCategoryList();
		videoList=getVideoList();
		video = getVideo();
       
	}

	@Test
	void testGetAllVideos() throws ServiceException {
     
		when(videodao.getAllVideos()).thenReturn(videoList);
		assertNotNull(videoList);
		assertEquals(videoService.getAllVideos(), getVideoList());
		verify(videodao, times(1)).getAllVideos();
	}

	@Test
	void testGetAllLevels() throws ServiceException {

		when(videodao.getAllLevels()).thenReturn(levelList);
		assertNotNull(levelList);
		assertEquals(videoService.getAllLevels(), getLevelList());
		verify(videodao, times(1)).getAllLevels();
	}

	@Test
	void testGetAllCategories() throws ServiceException {
		when(videodao.getAllCategories()).thenReturn(categoryList);
		assertNotNull(categoryList);
		assertEquals(videoService.getAllCategories(), getCategoryList());
		verify(videodao, times(1)).getAllCategories();
	}

	@Test
	void testGetVideoById() throws ServiceException {
		when(videodao.getVideoById(id)).thenReturn(video);
		assertNotNull(video);
		assertEquals(videoService.getVideoById(id), getVideo());
		verify(videodao, times(1)).getVideoById(id);
	}

	@Test
	void testGetActivatedVideos() throws ServiceException {
		when(videodao.getActivatedVideos()).thenReturn(videoList);
		assertNotNull(videoList);
		assertEquals(videoService.getActivatedVideos(), getVideoList());
		verify(videodao, times(1)).getActivatedVideos();
	}

	@Test
	void testGetDeactivatedVideos() throws ServiceException {
		when(videodao.getDeactivatedVideos()).thenReturn(videoList);
		assertNotNull(videoList);
		assertEquals(videoService.getDeactivatedVideos(), getVideoList());
		verify(videodao, times(1)).getDeactivatedVideos();
	}

	@Test
	void testToggleStatus() throws ServiceException {
		// fail("Not yet implemented");
		// perform the call
				videoService.toggleStatus(id);
				// verify the mocks
				verify(videodao, times(1)).toggleStatus(arg.capture());
				assertEquals(id, arg.getValue());
	}

	@Test
	void testDeleteVideoById() throws ServiceException {
		// perform the call
		videoService.deleteVideoById(id);
		// verify the mocks
		verify(videodao, times(1)).deleteVideoById(arg.capture());
		assertEquals(id, arg.getValue());
	}

	@Test
	void testAddVideo() throws ServiceException {
		videoService.addVideo(video);
		verify(videodao, times(1)).addVideo(videoArg.capture());
		// assertEquals("java",videoArgu.getValue().getName());
		assertNotNull(videoArg);
	}

	@Test
	void testUpdateVideo() throws ServiceException {
		videoService.updateVideo(video);
		verify(videodao, times(1)).updateVideo(videoArg.capture());
		// assertEquals("java",videoArgu.getValue().getName());
		assertNotNull(videoArg);
	}

//	@Test
//	void testDownloadFileFromLocal() throws ServiceException {
//		// fail("Not yet implemented");
//		
//	}

	public List<Level> getLevelList() {

		Level level = new Level();
		level.setId(1);
		level.setName("level 1");
		levelList.add(level);
		return levelList;
	}

	public List<Category> getCategoryList() {

		Category category = new Category();
		category.setId(1);
		category.setName("java");
		categoryList.add(category);
		return categoryList;
	}

	public Video getVideo() {

		ReferenceArtifact referenceArtifact1 = new ReferenceArtifact();
		referenceArtifact1.setId(1);
		referenceArtifact1.setName("reference artifact example");
		referenceArtifact1.setFile("java.txt");
		referenceArtifact1.setDescription("This is a reference artifact");
		ReferenceArtifact referenceArtifact2 = new ReferenceArtifact();
		SampleProgram sampleProgram1 = new SampleProgram();
		sampleProgram1.setId(1);
		sampleProgram1.setName("sample program example");
		sampleProgram1.setFile("sample.txt");
		sampleProgram1.setDescription("This is a sample program");
		SampleProgram sampleProgram2 = new SampleProgram();
		ReferenceUrl referenceUrl1 = new ReferenceUrl();
		referenceUrl1.setId(1);
		referenceUrl1.setName("reference url example");
		referenceUrl1.setUrl("http://www.javase.com");
		referenceUrl1.setDescription("This is a reference url");
		List<ReferenceArtifact> refArtList = new ArrayList<ReferenceArtifact>();
		refArtList.add(referenceArtifact1);
		List<SampleProgram> samProgList = new ArrayList<SampleProgram>();
		samProgList.add(sampleProgram1);
		List<ReferenceUrl> refUrlList = new ArrayList<ReferenceUrl>();
		refUrlList.add(referenceUrl1);
		Level level = new Level();
		level.setId(1);
		level.setName("Level 1");
		Category category = new Category();
		category.setId(1);
		category.setName("java");
		video.setId(1);
		video.setName("java");
		video.setDisplayName("java");
		video.setUrl("https://www.javase.com");
		Time time = Time.valueOf("01:20:09");
		video.setDuration(time);
		video.setCreatedBy("Subhalakshmi");
		Timestamp createTimestamp = new Timestamp(System.currentTimeMillis());
		video.setCreatedOn(createTimestamp);
		video.setModifiedBy("Subhalakshmi");
		Timestamp modifyTimestamp = new Timestamp(System.currentTimeMillis());
		video.setModifiedOn(modifyTimestamp);
		video.setDescription("This is a java video");
		video.setLevel(level);
		video.setCategory(category);
		video.setReferenceArtifact(refArtList);
		video.setSampleProgram(samProgList);
		video.setReferenceUrl(refUrlList);
		return video;

	}
	
	public List<Video> getVideoList(){
		videoList.add(video);
		return videoList;
	}

}
