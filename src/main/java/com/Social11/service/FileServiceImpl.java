package com.Social11.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileServiceImpl implements FileService{

	@Override
	public boolean uploadimage(String path,String filename,MultipartFile file) {
	
		String name = file.getOriginalFilename();
		String filepath = path + File.separator+filename;
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		try {
			Files.copy(file.getInputStream(), Paths.get(filepath));
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}

	@Override
	public InputStream getresource(String path, String filename) throws FileNotFoundException {
		
		String fullpath = path+File.separator+filename;
		InputStream is = new FileInputStream(fullpath);

		return is;		
	}
	
	public void DeleteImage(String path,String filename) {
		String filepath = path +filename;
		System.out.println(path);
		System.out.println(filepath);
		File f= new File(filepath);
//		System.out.println(f.exists()+" It exist or not");
//		System.out.println(f.delete());
		if(f.delete()) {
			System.out.println("file Deleted Succesfully");
		}
		return;
	}

}
