package com.bolehunt.gene.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bolehunt.gene.common.AppConfig;
import com.bolehunt.gene.common.FileUploadResponse;
import com.bolehunt.gene.service.FileService;

@Controller
public class FileUploadController {
	
	private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	private AppConfig config;
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping(value="/uploadAvatar", method=RequestMethod.POST)
    public @ResponseBody FileUploadResponse handleUploadAvatar(MultipartHttpServletRequest request, HttpServletResponse response){
		FileUploadResponse jsonResponse = new FileUploadResponse();
		MultipartFile file = request.getFile("avatar");
		String fileOriginalName = "";
    	if (!file.isEmpty()) {
    		fileOriginalName = file.getOriginalFilename();
            try {
                byte[] bytes = file.getBytes();
                String uuid = UUID.randomUUID().toString();
                log.debug("uuid = {}", uuid);
                String relativePath = fileService.getRelativePath(fileOriginalName, uuid);
                File uploadFile = new File(config.getFileUploadPath() + relativePath);
                uploadFile.getParentFile().mkdir();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadFile));
                stream.write(bytes);
                stream.close();
                log.info("You successfully uploaded avatar " + fileOriginalName + "!");

                fileService.insertAvatar(3, uuid, relativePath);
                
                jsonResponse.setAppend(false);
                jsonResponse.setInitialPreview(new String[]{"<img src='/gene/image/"+ uuid +"' class='file-preview-image' alt='Avatar' title='Avatar'>"});
                return jsonResponse;
            } catch (Exception e) {
            	log.error("Upload file " + fileOriginalName + " exception:", e);
            	jsonResponse.setError("You failed to upload " + fileOriginalName + " => " + e.getMessage());
                return jsonResponse;
            }
        } else {
        	jsonResponse.setError("You failed to upload " + fileOriginalName + " because the file was empty.");
            return jsonResponse;
        }
    }
	
	@RequestMapping(value="/uploadWorks", method=RequestMethod.POST)
    public @ResponseBody FileUploadResponse handleUploadWorks(MultipartHttpServletRequest request, HttpServletResponse response){
		FileUploadResponse jsonResponse = new FileUploadResponse();
		
		List<MultipartFile> files = request.getFiles("files");
        String fileName = "";
        for(MultipartFile file : files){
        	if (!file.isEmpty()) {
	    		fileName = file.getOriginalFilename();
	            try {
	                byte[] bytes = file.getBytes();
	                BufferedOutputStream stream = 
	                        new BufferedOutputStream(new FileOutputStream(new File(config.getFileUploadPath() + fileName)));
	                stream.write(bytes);
	                stream.close();
	                
	                log.info("You successfully uploaded " + fileName + "!");
	            } catch (Exception e) {
	            	log.error("Upload file {} exception:", fileName, e);
	            	jsonResponse.setError("You failed to upload " + fileName + " => " + e.getMessage());
	                return jsonResponse;
	            }
	        } else {
	        	jsonResponse.setError("You failed to upload " + fileName + " because the file was empty.");
	            return jsonResponse;
	        }
        }
		
        return jsonResponse;
    	
    }
}
