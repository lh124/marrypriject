package io.renren.controller.married;

import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.married.MarryMainEntity;
import io.renren.enums.TypeEnum;
import io.renren.service.married.MarryMainService;
import io.renren.utils.OssCallBackUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/married/callBack")
public class CallBackMarriedController {

	// 日志操作
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MarryMainService marryMainService;
	
	@ResponseBody
	@RequestMapping(value="/msgPic")
	@IgnoreAuth
	public JSONObject callBace(HttpServletRequest request, HttpServletResponse response) throws Exception{
		JSONObject jsona = OssCallBackUtil.analyzeCallBackData(request);
		JSONObject json = new JSONObject();
		if (jsona.getBoolean(OssCallBackUtil.VERIFY))
		{
			
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
					//商品图片
					if (type.equals(TypeEnum.MARRIED_MAIN_PIC.getType())){
						marrymain(jsona);
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
	
	/**
	 * 商品图片上传
	 * @param json
	 */
	private void marrymain(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
		if (id != null) {
			MarryMainEntity marryMainEntity = this.marryMainService.selectById(id);
			if (marryMainEntity != null) {
				marryMainEntity.setPic(ControllerConstant.CDN_URL + json.getString("filename"));
				this.marryMainService.update(marryMainEntity);
			} else {
				logger.error("回调关联数据不存在");
				logger.error(json.toString());
			}
		}
	}

}
