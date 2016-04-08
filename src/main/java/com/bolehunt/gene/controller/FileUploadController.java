package com.bolehunt.gene.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bolehunt.gene.common.AppConfig;
import com.bolehunt.gene.common.ErrorStatus;
import com.bolehunt.gene.common.RestMessage;
import com.bolehunt.gene.common.RestSuccessMessage;
import com.bolehunt.gene.exception.ApplicationException;
import com.bolehunt.gene.service.FileService;

@Controller
public final class FileUploadController {
	
	private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	private AppConfig config;
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping(value="/uploadAvatar", method=RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
    public RestMessage<Map<String, Object>> handleUploadAvatar(MultipartHttpServletRequest request, HttpServletResponse response){
		List<ErrorStatus> errorList = new ArrayList<ErrorStatus>();
		Integer userId = Integer.valueOf(request.getParameter("userId"));
		
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

                fileService.insertAvatar(userId, uuid, relativePath);
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("path", "/gene/image/"+uuid);
                
                return new RestMessage<Map<String, Object>>().getSuccessMessage(map);
            } catch (Exception e) {
            	log.error("Upload file " + fileOriginalName + " exception:", e);
            	errorList.add(ErrorStatus.UPLOAD_FILE_EXCEPTION);
            	throw new ApplicationException(errorList);
            }
        } else {
        	errorList.add(ErrorStatus.UPLOAD_FILE_EMPTY);
        	throw new ApplicationException(errorList);
        }
    }
	
	@RequestMapping(value="/uploadWorks", method=RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
    public RestSuccessMessage handleUploadWorks(MultipartHttpServletRequest request, HttpServletResponse response){
		
		List<ErrorStatus> errorList = new ArrayList<ErrorStatus>();
		
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
	            	throw new ApplicationException(errorList);
	            }
	        } else {
	        	log.error("You failed to upload " + fileName + " because the file was empty.");
	        	throw new ApplicationException(errorList);
	        }
        }
		
        return RestSuccessMessage.getInstance();
    	
    }
}
