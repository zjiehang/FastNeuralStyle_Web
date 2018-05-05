package xmu.sw.zjh.paper.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import xmu.sw.zjh.paper.constant.JsonResult;
import xmu.sw.zjh.paper.service.IndexService;

@Controller
@RequestMapping(value="/")
public class IndexController 
{	
	@Autowired
	@Qualifier("indexService")
	private IndexService indexService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String indexPage()
	{
		return "index";
	}
	
	@RequestMapping(value="createResult",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult resultCreating(
			@RequestParam(value="content",required=true) MultipartFile contentFile,
			@RequestParam(value="style",required=false) MultipartFile styleFile,
			@RequestParam(value="stylepath",required=false) String stylePath,
			HttpServletRequest request)
	{
		return indexService.getResultByController(contentFile, styleFile, stylePath, request);
	}
}
