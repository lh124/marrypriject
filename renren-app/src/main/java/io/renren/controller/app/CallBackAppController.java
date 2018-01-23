package io.renren.controller.app;

import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.app.WorkMainEntity;
import io.renren.enums.TypeEnum;
import io.renren.service.app.WorkMainService;
import io.renren.utils.OssCallBackUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appInterface/callBack/")
public class CallBackAppController {
	
	// 日志操作
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WorkMainService workMainService;
	
	@RequestMapping(value="/msgPic")
	@IgnoreAuth
	public JSONObject callBace(HttpServletRequest request, HttpServletResponse response) throws Exception{
		JSONObject jsona = OssCallBackUtil.analyzeCallBackData(request);
		JSONObject json = new JSONObject();
		if (jsona.getBoolean(OssCallBackUtil.VERIFY)){
			
			// 返回阿里云通知信息
			json.element("Status", "OK");
			response.setStatus(HttpServletResponse.SC_OK);
			response.addHeader("Content-Length", String.valueOf(json.toString().length()));
			
			// 从json中获取响应的数据
			
			String fileName = jsona.containsKey("filename") ? jsona.getString("filename") : null;
			String size = jsona.containsKey("size") ? jsona.getString("size") : null;
			Integer type = jsona.containsKey("type") && jsona.getString("type") != null ? 
					jsona.getInt("type") : null;
			// 异常捕获
			try{
				if (type != null && fileName != null && size != null){
					// 工作管理平台上传图片
					if (type.equals(TypeEnum.WORK_MAIN_PIC.getType())){
						uploadWorkMain(jsona);
						return json;
					}
				}
				
			} catch(Exception e){
				e.printStackTrace();
				logger.error("回调异常", e);
				logger.error(jsona.toString());
			} 
			return json;
		} else {
			json.element("Status", "verdify not ok");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.addHeader("Content-Length", String.valueOf(json.toString().length()));
			return json;
		}
	}
	
	private void uploadWorkMain(JSONObject json){
		Integer id = json.containsKey("id") ? json.getInt("id") : null;
		if (id != null) {
			WorkMainEntity workMain = workMainService.queryObject(id);
			if(workMain != null){
				workMain.setImg(ControllerConstant.CDN_URL + json.getString("filename"));
				workMainService.update(workMain);
			}
		}
	}

}
