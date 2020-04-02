package com.example.video.util;

import com.example.video.model.Video;

public class HTTPStatusResponse {
	
	private int statusCode;
	private String description;
	private Object data;

	public HTTPStatusResponse(int statusCode, String description, Object data) {
		super();
		this.statusCode = statusCode;
		this.description = description;
		this.data = data;
	}

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

	public HTTPStatusResponse(int statusCode, String description) {
		this.statusCode = statusCode;
		this.description = description;
	}
}
