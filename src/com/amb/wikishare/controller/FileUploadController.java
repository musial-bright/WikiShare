package com.amb.wikishare.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.amb.wikishare.dao.FileDAO;
import com.amb.wikishare.domain.WikiFile;


public class FileUploadController extends SimpleFormController {

    protected Log logger = LogFactory.getLog(getClass());

    private FileDAO fileDao = null;


    protected ModelAndView onSubmit(
            HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors) throws Exception {

        // cast the bean
        WikiFile wikiFile = (WikiFile) command;

        // let's see if there's content there
        MultipartFile multipartFile = wikiFile.getMultipartFile();

        if (multipartFile == null) {
            // hmm, that's strange, the user did not upload anything
            logger.error("Uploaded file is null!");
        }

        // well, let's do nothing with the bean for now and return
        fileDao.storeFile(
                wikiFile.getPublicFile(),
                multipartFile.getOriginalFilename(),
                multipartFile.getBytes());


        //return super.onSubmit(request, response, command, errors);
        return new ModelAndView(new RedirectView(getSuccessView()));
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException {

        WikiFile file = new WikiFile();

        if (request.getParameter("publicFile") == null) {
            logger.debug("[fromBackingObject] uploading protected file");
            file.setPublicFile(false);
        } else {
            logger.debug("[fromBackingObject]Êuploading public file");
        }

        return file;
    }

    protected void initBinder(HttpServletRequest request,
            ServletRequestDataBinder binder) throws ServletException {
        // to actually be able to convert Multipart instance to byte[]
        // we have to register a custom editor
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
        // now Spring knows how to handle multipart object and convert them
    }


    public void setFileDao(FileDAO fileDao) {
        this.fileDao = fileDao;
    }
}
