package com.olx.models;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadModel {
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
