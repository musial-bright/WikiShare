package com.amb.wikishare.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amb.wikishare.domain.WikiFile;

/**
 * This class always you to access and write files on the file system.
 * @author Adam Musial-Bright
 *
 */
public class FileDAO {

	protected final Log logger = LogFactory.getLog(getClass()); 
	
	private String filePath = "";
	private String fileTypes = "";
	
	public ArrayList<WikiFile> getFiles() {
		
		ArrayList<WikiFile> fileList = new ArrayList<WikiFile>();
		try{
			File dir = new File(this.filePath);
			String[] children = dir.list(new WikiFileTypes(fileTypes));
		    for (String fileName : children) {
		    	WikiFile wikiFile = new WikiFile();
				wikiFile.setFileName(fileName);
				wikiFile.setFilePath(filePath + File.separator + fileName);
		    	fileList.add(wikiFile);
		    }
		} catch (Exception e) {
			logger.error("getFiles error: "+e);
		}
		return fileList;
	}
	
	public void storeFile(String fileName, byte[] fileData) {
		logger.debug("File ("+fileName+") size " + fileData.length + " byte");
		try {
			logger.debug("File-details: "+this.filePath + 
					File.separator + 
					fileName);
			FileOutputStream fos = new FileOutputStream(
					this.filePath + 
					File.separator + 
					fileName);
			fos.write(fileData);
			fos.close();
		} catch (Exception e) {
			logger.error("storeFile error:" + e);
		}
		logger.debug("File with filename '"+fileName+"' stored.");
		
	}
	
	public void removeFile(String fileName) {
		File file = new File(filePath + File.separator + fileName);
		boolean exists = file.exists();
	    if (exists) {
	        file.delete();
	    }
	}
	
	public void setFilePath(String path) {
		this.filePath = path;
	}
	
	public String getFileStoragePath() {
		return this.filePath;
	}
	
	public void setFileTypes(String fileTypes) {
		this.fileTypes = fileTypes;
	}
	
	public String getFileTypes() {
		return this.fileTypes;
	}
	
	public class WikiFileTypes implements FilenameFilter {
		String[] allowedFileTypes;
		public WikiFileTypes(String types) {
			this.allowedFileTypes = types.split(",");
		}
		public boolean accept(File dir, String name) {
			for(String type : allowedFileTypes) {
				if( name.toLowerCase().endsWith(type) ) {
					return true;
				}
			}
			return false;
		}
	}
}