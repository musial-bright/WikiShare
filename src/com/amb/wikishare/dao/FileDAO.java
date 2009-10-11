package com.amb.wikishare.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;
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

    private final String PUBLIC_FILDER = "/public/files";
    private final String PROTECTED_FILDER = "/protected/files";

    private String wikiShareRootPath = "";
    private String fileTypes = "";

    protected ArrayList<WikiFile> getFiles(boolean publicFolder) {

        String filePath = getFolderPath(publicFolder);

        logger.debug("[getFiles(" + publicFolder +")] path=" + filePath);

        ArrayList<WikiFile> fileList = new ArrayList<WikiFile>();
        try{
            File dir = new File(filePath);
            String[] fileNames ;
            if (fileTypes != "") {
                fileNames = dir.list(new WikiFileTypes(fileTypes));
            } else {
                fileNames = dir.list();
            }
            for (String fileName : fileNames) {

                File file = new File(filePath + File.separator + fileName);

                WikiFile wikiFile = new WikiFile();
                wikiFile.setFileName(fileName);
                wikiFile.setFilePath(filePath + File.separator + fileName);
                if (file.isFile()) {
                    wikiFile.setSize(file.length());
                }
                fileList.add(wikiFile);
            }
        } catch (Exception e) {
            logger.error("[getFiles] error: ", e);
        }
        return fileList;
    }

    public ArrayList<WikiFile> getProtectedFiles() {
        return getFiles(false);
    }

    public ArrayList<WikiFile> getPublicFiles() {
        return getFiles(true);
    }

    public void storeFile(boolean publicFolder, String fileName, byte[] fileData) {

        String folderPath = getFolderPath(publicFolder);

        logger.debug("[storeFile] file: "+ folderPath +" / "+ fileName +" / " + fileData.length + " byte");
        try {
            String destinationPath = folderPath + File.separator + fileName;
            logger.debug("[storeFile]: destinationPath="+ destinationPath);

            FileOutputStream fos = new FileOutputStream(destinationPath);
            fos.write(fileData);
            fos.close();

        } catch (Exception e) {
            logger.error("[storeFile] error:", e);
        }
        logger.debug("[storeFile] File with filename '"+fileName+"' stored.");

    }

    public void removeFile(boolean publicFolder, String fileName) {

        String folderPath = getFolderPath(publicFolder);

        File file = new File(folderPath + File.separator + fileName);
        boolean exists = file.exists();
        if (exists) {
            file.delete();
        }
    }

    /**
     * Copy file from public to protected folder or vice versa.
     * @param toPublicFolder destination folder (public = true)
     * @param fileName file name
     * @throws IOException
     */
    public void copyFile(boolean toPublicFolder, String fileName) throws IOException {

        String currentFileFolder = getFolderPath(!toPublicFolder);
        String newFileFolder = getFolderPath(toPublicFolder);

        File sourceFile = new File(currentFileFolder + File.separator + fileName);
        File destFile = new File(newFileFolder + File.separator + fileName);

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    private String getFolderPath(boolean publicFolder) {
        String folderPath = "";
        if (publicFolder) {
            folderPath = wikiShareRootPath + PUBLIC_FILDER;
        } else {
            folderPath = wikiShareRootPath + PROTECTED_FILDER;
        }
        return folderPath;
    }


    public void setWikiShareRootPath(String path) {
        this.wikiShareRootPath = path;
    }

    public String getWikiShareRootPath() {
        return this.wikiShareRootPath;
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