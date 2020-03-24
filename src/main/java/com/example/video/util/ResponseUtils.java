package com.example.video.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.video.model.Video;

public class ResponseUtils{
	
	public static ResponseEntity<HTTPStatusResponse> prepareSuccessResponse(String responseMessage,
			Video data){
		return new ResponseEntity<>(
				HTTPStatusResponse.setResponse(responseMessage,data,HttpStatus.OK.value()),HttpStatus.OK);
		
	}
	
	
}