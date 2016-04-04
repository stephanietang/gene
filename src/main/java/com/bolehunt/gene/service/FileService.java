package com.bolehunt.gene.service;

import com.bolehunt.gene.domain.Avatar;

public interface FileService {

	public Avatar getAvatarByUUID(String uuid);
	
	public String getRelativePath(String fileName, String uuid);
	
	public void insertAvatar(int userId, String uuid, String relativePath);
	
}
