package com.amb.wikishare.domain;

import org.springframework.web.multipart.MultipartFile;


public class WikiFile {

    // MultipartFile represents files name, size and byte[] data
    private MultipartFile multipartFile;

    // Optional file name and path for <code>FileDao</code>
    private String fileName = "";
    private String filePath = "";
    private long fileSize = -1;

    private boolean publicFile = true;


    public String getFileName() {
        if (fileName != null && fileName.length() > 0) {
            return fileName;
        }
        if (multipartFile != null) {
            fileName = multipartFile.getOriginalFilename();
        }
        return "";
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
        if (filePath != null && filePath.length() > 0) {
            return filePath;
        }
        if (multipartFile != null) {
            fileName = multipartFile.getOriginalFilename();
        }
        return "";
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

    public void setSize(long sizeInBytes) {
        fileSize = sizeInBytes;
    }

    public long getSize() {
        if (multipartFile != null) {
            return  multipartFile.getSize();
        }
        return fileSize;
    }

    public String getSizeForHuman() {
        String sizeForHuman = "";
        long sizeInBytes = 0;
        try {
            if (multipartFile != null) {
                sizeInBytes = multipartFile.getSize();
            } else {
                sizeInBytes = fileSize;
            }
        } catch (Exception e) {}

        if (sizeInBytes < 1000) {
            sizeForHuman = sizeInBytes + " byte";
        }
        if (sizeInBytes >= 1000 && sizeInBytes < 1000000) {
            sizeForHuman = sizeInBytes / 1000 + " kB";
        }
        if (sizeInBytes >= 1000000 && sizeInBytes < 1000000000) {
            sizeForHuman = sizeInBytes / 1000000 + "." + sizeInBytes % 1000 + " MB";
        }

        return sizeForHuman;
    }

    public String getContentType() {
        if (multipartFile != null) {
            return  multipartFile.getContentType();
        }
        return "";
    }

    public boolean getPublicFile() {
        return publicFile;
    }

    public void setPublicFile(boolean publicFile) {
        this.publicFile = publicFile;
    }



}