package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.APIResponse;
import com.example.demo.service.FileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/file")
@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 页面直接访问localhost:8080
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping("/upload")
	@ResponseBody
	public Object upload(@RequestParam(value="file") MultipartFile file, HttpServletRequest request) {
		if (file == null || file.isEmpty()) {
			return APIResponse.builder().code(APIResponse.FAIL).msg("null file uploaded").build();
		}
		
		long size = file.getSize(); //文件大小校验
		
		String name = request.getParameter("name");
		log.info("上传文件：name={}, size={}kb", name, size/1024);
		
		try {
			
			return fileService.saveData(file);
		} catch (Exception e) {
			return APIResponse.builder().code(APIResponse.FAIL).msg("cause error: " + e.getMessage()).build();
		}
		
	}
	
	@GetMapping("/download")
	public void download(@RequestParam String mediaId, HttpServletResponse response) {
		log.info("download file: {}", mediaId);
		
		File file = fileService.getFile(mediaId);
		if (file == null) {
			APIResponse result = APIResponse.builder().code(APIResponse.FAIL).msg("file not found").data(mediaId).build();
			writeToResponse(JSON.toJSONString(result), response);
			return;
		}
		if (!file.exists()) {
			APIResponse result = APIResponse.builder().code(APIResponse.FAIL).msg("the file is overdue").data(mediaId).build();
			writeToResponse(JSON.toJSONString(result), response);
			return;
		}
		
		response.setContentType("application/x-download");
		response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());
		
		try {
			InputStream inputStream = new FileInputStream(file);
			OutputStream outputStream = response.getOutputStream();
			IOUtils.copy(inputStream, outputStream);
			outputStream.flush();
			inputStream.close();
			outputStream.close();
		} catch (IOException e) {
			log.error("file download excepiton", e);
			APIResponse result = APIResponse.builder().code(APIResponse.FAIL).msg("ioexception" + e).build();
			writeToResponse(JSON.toJSONString(result), response);
		}

	}
	
	private void writeToResponse(String msg, HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(msg);
		} catch (IOException e) {
			log.error("write msg error", e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
}
