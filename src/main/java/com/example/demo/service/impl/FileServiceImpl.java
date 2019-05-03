package com.example.demo.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.APIResponse;
import com.example.demo.service.FileService;
import com.example.demo.utils.DemoUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

//	private final String tempPath = System.getProperty("catalina.base") + File.separator + "temp";
	
	@Value("${web.temp.file-path}")
	private String tempPath;
	
	@Value("#{'${upload.file.types}'.split(',')}")
	private List<String> allowFileTypes;
	
	@Value("#{${injection.map}}")
	private Map<String, String> map;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	private static Map<String, String> fileReposity;
	static {
		fileReposity = new HashMap<String, String>();
		fileReposity.put("fmid001", "D:/java/workspace2/springboot-demo/app_temps/ccfdd3e330f0ca3946cbd13f43ab1326.jpg");
		fileReposity.put("fmid002", "xxxx");
		fileReposity.put("fmid003", "D:/java/workspace2/springboot-demo/app_temps/sdaf.xlsx");
	}
	
	@Override
	public Object saveData(MultipartFile file) {
	
		// 文件格式校验
		String fileName = file.getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
		
		log.info("allowFieTypes:{}", allowFileTypes);
		log.info("map:{}", map);
		
		if (!allowFileTypes.contains(fileType)) {
			throw new RuntimeException("不支持的文件格式！");
		}
//		if (!"xlsx".equals(fileType) && !"xls".equals(fileType)) {
//			throw new RuntimeException("不支持的文件格式！");
//		}
		
		// 创建缓存目录
		File filePath = new File(tempPath);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		
		// 先存到缓存目录
		final String fileFullPath = String.format("%s/%s.%s", tempPath, DemoUtils.getUniqueMd5(), fileType);
		log.info("存储文件：{}", fileFullPath);
		try {
			file.transferTo(new File(fileFullPath));
		} catch (IOException e) {
			log.error("文件存储失败", e);
			throw new RuntimeException("文件存储失败！", e);
		}
		
		try {
			// 读取文件进行存储
			taskExecutor.execute(new Runnable() {
				
				public void run() {
					
					readFileAndSaveData(fileFullPath);
				}
				
			});
		} catch (Exception e) {
			log.error("异步执行失败", e);
		}
		
		return APIResponse.builder().code(APIResponse.SUCCESS).msg("success").data(fileFullPath).build();
	}
	
	public void readFileAndSaveData(String filePath) {
		log.info("异步读取文件，处理数据中。。。");
		try {
			TimeUnit.SECONDS.sleep(6);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		// 加载文件
//		List<Map<String,Object>> excel = null;
//		List<DemoDto> list = null;
//		
//		try {
//			excel = ExcelUtil.readExcel(filePath);
//		} catch (Exception e) {
//			throw new RuntimeException("加载文件失败");
//		}
//		
//		// 封装数据到list
//		try {
//			list = JSON.parseArray(JSON.toJSONString(excel), DemoDto.class);
//		} catch (Exception e) {
//			throw new RuntimeException("数据封装失败");
//		}
//		
//		// 存储list，操作省略，后续定时job清除缓存
//		fileDao.save(list);
	}

	@Override
	public File getFile(String mediaId) {
		String uri = fileReposity.get(mediaId);
		if (StringUtils.isEmpty(uri)) {
			return null;
		}
		
		return new File(uri);
	}
	
}
