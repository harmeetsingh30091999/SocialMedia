package com.Social11.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	boolean uploadimage(String path ,String filename, MultipartFile file);
	
	InputStream getresource(String path, String filename) throws Exception;
}
