package com.bolehunt.gene.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bolehunt.gene.common.AppConfig;
import com.bolehunt.gene.domain.Avatar;
import com.bolehunt.gene.service.FileService;

@Controller
public final class ImageResolverController {
	
	private static final Logger log = LoggerFactory.getLogger(ImageResolverController.class);
	
	@Autowired
	private AppConfig config;
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping(value="/image/{uuid}", method = RequestMethod.GET)
	public void showImage(@PathVariable("uuid") String uuid, HttpServletRequest request, HttpServletResponse response){
		ServletOutputStream stream = null;
		BufferedInputStream bis = null;
		try{
			stream = response.getOutputStream();
			response.addHeader("Content-Disposition", "inline;"); // for display
			//response.addHeader("Content-Disposition", "attachment; filename=" + "filename.jpg"); // for download
			
			Avatar avatar = fileService.getAvatarByUUID(uuid);
			
			if(avatar != null){
				String fileRelativePath = avatar.getPath();
				Path path = Paths.get(config.getFileUploadPath() + fileRelativePath);
				String contentType = Files.probeContentType(path);
				
				// Should investigate right content type,
				// if setting wrong content type, IE would download the image instead of display it
				response.setContentType(contentType);
				
				bis = new BufferedInputStream(new FileInputStream(path.toFile()));
				int readBytes = 0;
				while ((readBytes = bis.read()) != -1){
					stream.write(readBytes);
				}
			}
		    
		}catch(IOException e){
			log.error("Display file exception:", e);
		}finally{
			try{
				if(stream != null)stream.close();
				if(bis != null)bis.close();
			}catch(IOException e){
				log.error("Close output stream exception:", e);
			}
		}
	}
	

}
