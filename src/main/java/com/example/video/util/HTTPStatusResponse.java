package com.example.video.util;

import com.example.video.model.Video;

public class HTTPStatusResponse {
	
	private String message;
	private Video data;
	private int value;
	
	
	public HTTPStatusResponse(String message, Video video, int value) {
		super();
		this.message = message;
		this.data = video;
		this.value = value;
	}


	public static HTTPStatusResponse setResponse(String msg,Video data,int val) {
		return new HTTPStatusResponse(msg,data,val);
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Video getVideo() {
		return data;
	}


	public void setVideo(Video video) {
		this.data = video;
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}
}
