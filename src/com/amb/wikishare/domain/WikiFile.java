package com.amb.wikishare.domain;

import org.springframework.web.multipart.MultipartFile;


public class WikiFile {

	// MultipartFile represents files name, size and byte[] data 
	private MultipartFile multipartFile;
	private String fileName = "";
	private String filePath = "";

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
		
}