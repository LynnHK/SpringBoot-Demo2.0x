package com.example.demo.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	Object saveData(MultipartFile file);

	File getFile(String mediaId);

}
