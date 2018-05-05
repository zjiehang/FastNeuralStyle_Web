package xmu.sw.zjh.paper.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import xmu.sw.zjh.paper.constant.JsonResult;

public interface IndexService 
{
	JsonResult getResultByController(
			MultipartFile contentFile,
			MultipartFile styleFile,
			String stylePath,
			HttpServletRequest request);
	
	float[][][][] getResultByContentAndStylePath(
			MultipartFile contentFile,
			String stylePath,
			HttpServletRequest request)throws IOException;
	
	float[][][][] getResultByContentAndStyle(
			MultipartFile contentFile,
			MultipartFile styleFile,
			HttpServletRequest request)throws IOException;
}
