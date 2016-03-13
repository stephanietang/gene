package com.bolehunt.gene.form;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm {

	private MultipartFile file;
	
	private MultipartFile[] files;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	
	
}
