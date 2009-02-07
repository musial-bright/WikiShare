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

	public boolean getIsGraphics() {
		String type = fileName.substring(fileName.indexOf(".") +1, fileName.length());
		if ( type.toLowerCase().matches("jpg|png|gif|jpeg") ) {
			return true;
		}
		return false;
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