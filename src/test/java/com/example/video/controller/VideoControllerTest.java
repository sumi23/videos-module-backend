package com.example.video.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.example.video.dao.VideoDAOImpl;
import com.example.video.model.Level;
import com.example.video.service.VideoServiceImpl;

class VideoControllerTest {
	
	@InjectMocks
	VideoController controller;

	@Mock
	VideoDAOImpl videodao;

	@Spy
	List<Level> levelList = new ArrayList<Level>();

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		levelList = getLevelList();
	
       
	}
	public List<Level> getLevelList() {

		Level level = new Level();
		level.setId(1);
		level.setName("level 1");
		levelList.add(level);
		return levelList;
	}


	@Test
	void testDoGetAllVideos() {
		
		
		fail("Not yet implemented");
	}

	@Test
	void testDoGetAllLevels() throws Exception {
		
		when(videodao.getAllLevels()).thenReturn(levelList);
		assertNotNull(levelList);
		assertEquals(controller.doGetAllLevels(), getLevelList());
		verify(videodao, times(1)).getAllLevels();
		
	}

	@Test
	void testDoGetAllCategories() {
		fail("Not yet implemented");
	}

	@Test
	void testDoGetActivatedVideos() {
		fail("Not yet implemented");
	}

	@Test
	void testDoGetDeactivatedVideos() {
		fail("Not yet implemented");
	}

	@Test
	void testDoGetVideoById() {
		fail("Not yet implemented");
	}

	@Test
	void testDoDeleteVideoById() {
		fail("Not yet implemented");
	}

	@Test
	void testDoDeleteReferenceUrlById() {
		fail("Not yet implemented");
	}

	@Test
	void testDoToggleStatus() {
		fail("Not yet implemented");
	}

	@Test
	void testDoaddVideos() {
		fail("Not yet implemented");
	}

	@Test
	void testDoEditVideos() {
		fail("Not yet implemented");
	}

	@Test
	void testUploadToLocalFileSystem() {
		fail("Not yet implemented");
	}

	@Test
	void testDoDownloadFileFromLocal() {
		fail("Not yet implemented");
	}

}
