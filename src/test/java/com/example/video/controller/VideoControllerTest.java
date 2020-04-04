package com.example.video.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.junit.runner.RunWith;
import com.example.video.dao.VideoDAOImpl;
import com.example.video.exception.ServiceException;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.ReferenceArtifact;
import com.example.video.model.ReferenceUrl;
import com.example.video.model.SampleProgram;
import com.example.video.model.Video;
import com.example.video.service.VideoServiceImpl;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RunWith(MockitoJUnitRunner.class)
class VideoControllerTest  {
	

	private MockMvc mockMvc;

	@InjectMocks
	VideoController videoController;
	
	@Mock
	VideoServiceImpl videoService;

	@Spy
	List<Level> levelList = new ArrayList<Level>();

	@Spy
	List<Category> categoryList = new ArrayList<Category>();

	@Spy
	List<Video> videoList=new ArrayList<Video>();

	
	Video video = new Video();
	
	private ObjectMapper objectmapper;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
                .standaloneSetup(videoController)
                .build();
		levelList = getLevelList();
		categoryList = getCategoryList();
		videoList=getVideoList();
		video = getVideo();
		objectmapper=new ObjectMapper();
       
	}
	public List<Level> getLevelList() {

		Level level = new Level();
		level.setId(1);
		level.setName("level 1");
		levelList.add(level);
		return levelList;
	}


	@Test
	void testDoGetAllVideos() throws Exception {
	    when(videoService.getAllVideos()).thenReturn(videoList);
		this.mockMvc.perform(get("/list")).andExpect(status().isOk());
	}
	
	@Test
	void testDoGetAllVideosExpectFailure() throws Exception {
		doThrow(ServiceException.class).when(videoService).getAllVideos();
		this.mockMvc.perform(get("/list")).andExpect(status().isNotFound());
	}

	@Test
	void testDoGetAllLevels() throws Exception {
		
		when(videoService.getAllLevels()).thenReturn(levelList);
		 MvcResult mvcResult=this.mockMvc.perform(get("/listLevels")).andExpect(status().isOk())
		.andDo(print())
		.andReturn();
//		 String s=mvcResult.getResponse().getContentAsString();
//		 System.out.print("string is "+s);
//		 assertEquals(200,mvcResult.getResponse().getStatus());
	}

	
	@Test
	void testDoGetAllLevelsExpectFailure() throws Exception {
		
		doThrow(ServiceException.class).when(videoService).getAllLevels();
		 MvcResult mvcResult=this.mockMvc.perform(get("/listLevels")).andExpect(status().isNotFound())
		.andDo(print())
		.andReturn();
//		 String s=mvcResult.getResponse().getContentAsString();
//		 System.out.print("string is "+s);
//		 assertEquals(200,mvcResult.getResponse().getStatus());
	}

	
	@Test
	void testDoGetAllCategories() throws Exception {
		when(videoService.getAllCategories()).thenReturn(categoryList);
		this.mockMvc.perform(get("/listCategories")).andExpect(status().isOk())
		.andReturn();
	}
	@Test
	void testDoGetAllCategoriesExpectFailure() throws Exception {
		doThrow(ServiceException.class).when(videoService).getAllCategories();
		this.mockMvc.perform(get("/listCategories")).andExpect(status().isNotFound())
		.andReturn();
	}

	@Test
	void testDoGetActivatedVideos() throws ServiceException,Exception {
        when(videoService.getActivatedVideos()).thenReturn(videoList);
		this.mockMvc.perform(get("/listActive")).andExpect(status().isOk());
	}
	@Test
	void testDoGetActivatedVideosExpectFailure() throws ServiceException,Exception {
		doThrow(ServiceException.class).when(videoService).getActivatedVideos();
		this.mockMvc.perform(get("/listActive")).andExpect(status().isNotFound());
	}

	@Test
	void testDoGetDeactivatedVideos() throws Exception {
		when(videoService.getDeactivatedVideos()).thenReturn(videoList);
		this.mockMvc.perform(get("/listDeactive")).andExpect(status().isOk());
	}
	
	@Test
	void testDoGetDeactivatedVideosExpectFailure() throws Exception {
		
		doThrow(ServiceException.class).when(videoService).getDeactivatedVideos();
		this.mockMvc.perform(get("/listDeactive")).andExpect(status().isNotFound());
	}

	@Test
	void testDoGetVideoById() throws Exception {
		int id=1;
		when(videoService.getVideoById(id)).thenReturn(video);
		this.mockMvc.perform(get("/listById/{id}",1)).andExpect(status().isOk()).andDo(print());
		verify(videoService, times(1)).getVideoById(id);
	}
	
	@Test
	void testDoGetVideoByIdExpectFailure() throws Exception {
		int id=1;
		doThrow(ServiceException.class).when(videoService).getVideoById(id);
		this.mockMvc.perform(get("/listById/{id}",id)).andExpect(status().isNotFound()).andDo(print());
		verify(videoService, times(1)).getVideoById(id);
	}

	@Test
	void testDoDeleteVideoById() throws Exception {
		int id=1;
		doNothing().when(videoService).deleteVideoById(id);
		this.mockMvc.perform(delete("/deleteById/{id}",1)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	void testDoDeleteVideoByIdExpectFailure() throws Exception {
		int id=1;
		doThrow(ServiceException.class).when(videoService).deleteVideoById(id);
		this.mockMvc.perform(delete("/deleteById/{id}",1)).andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void testDoDeleteReferenceUrlById() throws Exception {
		int id=1;
		doNothing().when(videoService).deleteReferenceUrlById(id);
		this.mockMvc.perform(delete("/deleteReferenceUrlById/{id}",1)).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	void testDoDeleteReferenceUrlByIdExpectFailure() throws Exception {
		int id=1;
		doThrow(ServiceException.class).when(videoService).deleteReferenceUrlById(id);
		this.mockMvc.perform(delete("/deleteReferenceUrlById/{id}",1)).andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void testDoToggleStatus() throws Exception {
		int id=1;
		doNothing().when(videoService).toggleStatus(id);
		this.mockMvc.perform(get("/toggleStatus/{id}",1)).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	void testDoToggleStatusExpectFailure() throws Exception {
		int id=1;
		doThrow(ServiceException.class).when(videoService).toggleStatus(id);
		this.mockMvc.perform(get("/toggleStatus/{id}",1)).andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void testDoaddVideos() throws Exception {
		Video video=new Video();
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
		video.setTags("java");
		video.setCreatedBy("Subhalakshmi");
		Timestamp createTimestamp = new Timestamp(System.currentTimeMillis());
		video.setCreatedOn(createTimestamp);
		video.setModifiedBy("Subhalakshmi");
		Timestamp modifyTimestamp = new Timestamp(System.currentTimeMillis());
		video.setModifiedOn(modifyTimestamp);
		video.setDescription("This is a java video");
		video.setTranscript("file.txt");
		video.setStatus(true);
		video.setLevel(level);
		video.setCategory(category);
		video.setReferenceArtifact(refArtList);
		video.setSampleProgram(samProgList);
		video.setReferenceUrl(refUrlList);
		
		doNothing().when(videoService).addVideo(video);
		String videoJson=objectmapper.writeValueAsString(video);
		 MvcResult mvcResult=this.mockMvc.perform(post("/add")
				 .contentType(MediaType.APPLICATION_JSON_VALUE)
				 .content(videoJson))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	void testDoAddVideosExpectFailure() throws Exception{
		Video video=new Video();
		String videoJson=objectmapper.writeValueAsString(video);
		doThrow(ServiceException.class).when(videoService).addVideo(video);
		//this.mockMvc.perform(post("/add")).andExpect(status().isBadRequest()).andDo(print());
		this.mockMvc.perform(post("/add")).andExpect(status().isBadRequest());
	}
	
	
	
	@Test
	void testDoEditVideos() throws Exception {
		Video video=new Video();
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
		video.setTags("java");
		video.setCreatedBy("Subhalakshmi");
		Timestamp createTimestamp = new Timestamp(System.currentTimeMillis());
		video.setCreatedOn(createTimestamp);
		video.setModifiedBy("Subhalakshmi");
		Timestamp modifyTimestamp = new Timestamp(System.currentTimeMillis());
		video.setModifiedOn(modifyTimestamp);
		video.setDescription("This is a java video");
		video.setTranscript("file.txt");
		video.setStatus(true);
		video.setLevel(level);
		video.setCategory(category);
		video.setReferenceArtifact(refArtList);
		video.setSampleProgram(samProgList);
		video.setReferenceUrl(refUrlList);
		doNothing().when(videoService).updateVideo(video);
		String videoJson=objectmapper.writeValueAsString(video);
		 MvcResult mvcResult=this.mockMvc.perform(put("/edit")
				 .contentType(MediaType.APPLICATION_JSON_VALUE)
				 .content(videoJson))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	@Test
	void testDoEditVideosExpectFailure() throws Exception{
		doThrow(ServiceException.class).when(videoService).updateVideo(video);
		this.mockMvc.perform(put("/edit")).andExpect(status().isBadRequest());
	}
	

	@Test
	void testUploadToLocalFileSystem() throws Exception {
		 MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", MediaType.TEXT_PLAIN_VALUE, "some file content".getBytes());
		this.mockMvc.perform(multipart("/upload").file(firstFile)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	void testDoDownloadFileFromLocal() throws Exception {
		String fileName="file.txt";
		String encodedFileContent="ZmlsZSBjb250ZW50";
		when(videoService.downloadFileFromLocal(fileName)).thenReturn(encodedFileContent);
		this.mockMvc.perform(get("/download/{fileName}",fileName)).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE))
		.andDo(print());
		verify(videoService, times(1)).downloadFileFromLocal(fileName);
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
		video.setTags("java");
		video.setCreatedBy("Subhalakshmi");
//		Timestamp createTimestamp = new Timestamp(System.currentTimeMillis());
//		video.setCreatedOn(createTimestamp);
		video.setModifiedBy("Subhalakshmi");
//		Timestamp modifyTimestamp = new Timestamp(System.currentTimeMillis());
//		video.setModifiedOn(modifyTimestamp);
		video.setDescription("This is a java video");
		video.setTranscript("file.txt");
		video.setStatus(true);
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
