package io.renren.controller.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月24日 下午11:05:27
 */
@Controller
public class SysPageController {
	
	// --------------- 管理后端页面跳转
	@RequestMapping("sys/{url}.html")
	public String page(@PathVariable("url") String url ){
		return "sys/" + url + ".html";
	}
	
	@RequestMapping("uniform/{url}.html")
	public String uniformPage(@PathVariable("url") String url ){
		return "uniform/" + url + ".html";
	}

	@RequestMapping("generator/{url}.html")
	public String generator(@PathVariable("url") String url){
		return "generator/" + url + ".html";
	}
	
	@RequestMapping("school/{url}.html")
	public String school(@PathVariable("url") String url){
		return "school/" + url + ".html";
	}
	
	
	// ---------------- 客户端页面跳转 -----------------------
	@RequestMapping("front/{module}/{url}.html")
	public String frontPage(@PathVariable("url") String url, @PathVariable("module") String module){
		return "front/" + module + "/" + url + ".html";
	}
	
	@RequestMapping("/front/{url}.html")
	public String front(@PathVariable("url") String url){
		return "front/" + url + ".html";
	}
	
	@RequestMapping("/phone/schoolclass/ClassPhotoLook")
	public void front(HttpServletRequest request, HttpServletResponse response, Long classId) throws Exception{
		
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/" +
				"/" + "front/classes/classHomePage.html?classId=" + classId;
		response.sendRedirect(basePath);
		//return "front/classes/classHomePage.html";
	}
	// 补救系统，为了兼容以前的二维码
	@RequestMapping("/homepage/ClassPhotoLook")
	public void oldPath(HttpServletRequest request, HttpServletResponse response, Long classId) throws Exception{
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/" +
				"/" + "front/classes/classHomePage.html?classId=" + classId;
		response.sendRedirect(basePath);
		//return "front/classes/classHomePage.html";http://wrs.gykjewm.com/homepage/ClassPhotoLook?classId=45
	}
	
	// -----------------------智能校服--------------------------
	@RequestMapping("smart/{module}/{url}.html")
	public String smartPage(@PathVariable("url") String url, @PathVariable("module") String module){
		return "smart/" + module + "/" + url + ".html";
	}
	
	@RequestMapping("/smart/{url}.html")
	public String smart(@PathVariable("url") String url){
		return "smart/" + url + ".html";
	}
	
	// -----------------------墓碑二维码--------------------------
	@RequestMapping("tombstone/{module}/{url}.html")
	public String tombstonePage(@PathVariable("url") String url, @PathVariable("module") String module){
		return "tombstone/" + module + "/" + url + ".html";
	}
		
	@RequestMapping("/tombstone/{url}.html")
	public String tombstone(@PathVariable("url") String url){
		return "tombstone/" + url + ".html";
	}
}
