package com.example.video.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import com.example.video.model.Level;
import com.example.video.model.Video;


class VideoServiceImplTest {
	
	@InjectMocks
	VideoServiceImpl videoService;
	
	@Mock
	VideoDAOImpl videodao;
	
	@Spy
	List<Level> levelList=new ArrayList<Level>();
	
	@Captor
    private ArgumentCaptor<Integer> arg;
	
	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        levelList=getLevelList();  
        
	}
	

	@Test
	void testGetAllVideos() {
		
		
		//fail("Not yet implemented");
	}

	@Test
	void testGetAllLevels() throws ServiceException {
//		List<Level> levelList=new ArrayList<Level>();
//		Level level1=new Level(1,"level 1");
//		Level level2=new Level(2,"level 2");
//		levelList.add(level1);
//		levelList.add(level2);
//		when(videodao.getAllLevels()).thenReturn(levelList);
//        List<Level> levList = videoService.getAllLevels();
//        assertEquals(2,levList.size());
//        verify(videodao,times(1)).getAllLevels();
		
        when(videodao.getAllLevels()).thenReturn(levelList);
       // assertNull(levelList);
        assertEquals(videoService.getAllLevels(),getLevelList());
        verify(videodao,times(1)).getAllLevels();
	}

	@Test
	void testGetAllCategories() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetVideoById() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetActivatedVideos() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetDeactivatedVideos() {
		//fail("Not yet implemented");
	}

	@Test
	void testToggleStatus() {
		//fail("Not yet implemented");
	}

	@Test
	void testDeleteVideoById() throws ServiceException {
		Integer videoId=42;

        // perform the call
        videoService.deleteVideoById(videoId);

        // verify the mocks
        verify(videodao, times(1)).deleteVideoById(arg.capture());
        assertEquals(videoId, arg.getValue());
	}

	@Test
	void testAddVideo() {
		//fail("Not yet implemented");
	}

	@Test
	void testUpdateVideo() {
		//fail("Not yet implemented");
	}

	@Test
	void testDownloadFileFromLocal() {
		//fail("Not yet implemented");
	}
	public List<Level> getLevelList()
	{
//		Level l1= new Level();
//	    l1.setId( 1);
//		l1.setName("level 1");
//		Level l2= new Level();
//		l2.setId( 2);
//		l2.setName("level 2");
//
//		levelList.add(l1);
//		levelList.add(l2);
		
		Level level1=new Level(1,"level 1");
    	//Level level2=new Level(2,"level 2");
		levelList.add(level1);
		//levelList.add(level2);

		return levelList;
	}
}
