package com.bolehunt.gene.service.impl;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolehunt.gene.common.ErrorStatus;
import com.bolehunt.gene.domain.Avatar;
import com.bolehunt.gene.domain.AvatarExample;
import com.bolehunt.gene.exception.ApplicationException;
import com.bolehunt.gene.persistence.AvatarMapper;
import com.bolehunt.gene.service.FileService;

@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	AvatarMapper avatarMapper;

	public Avatar getAvatarByUUID(String uuid){
		AvatarExample ex = new AvatarExample();
		ex.createCriteria().andUuidEqualTo(uuid);
		List<Avatar> avatarList = avatarMapper.selectByExample(ex);
		if(avatarList != null && avatarList.size() > 0){
			return avatarList.get(0);
		}
		
		return null;
	}
	
	public String getRelativePath(String fileName, String uuid) {
		String ext = FilenameUtils.getExtension(fileName);
		LocalDate date = LocalDate.now(); 
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd");
		String dateStr = dtf.print(date);
		return dateStr + "/" + uuid + "." + ext;
	}
	
	public void insertAvatar(int userId, String uuid, String relativePath) {
		// delete user's previous avatar if exists
		AvatarExample ex = new AvatarExample();
		ex.createCriteria().andUserIdEqualTo(userId);
		avatarMapper.deleteByExample(ex);
		
		// insert new avatar
		Avatar avatar = new Avatar();
        avatar.setPath(relativePath);
        avatar.setUuid(uuid);
        avatar.setUserId(userId);
		avatarMapper.insert(avatar);
	}
	
}