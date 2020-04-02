package com.example.video.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.video.model.Video;

public class ResponseUtils{
	
	private int statusCode;
	private String description;
	private Object data;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public ResponseUtils(int statusCode, String description, Object data) {
		super();
		this.statusCode = statusCode;
		this.description = description;
		this.data = data;
	}
	
	
}