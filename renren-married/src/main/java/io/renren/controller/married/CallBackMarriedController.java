package io.renren.controller.married;

import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.married.MarryBlessingEntity;
import io.renren.entity.married.MarryMainEntity;
import io.renren.entity.married.MarryPhotoEntity;
import io.renren.entity.married.MarryWeddingEntity;
import io.renren.enums.TypeEnum;
import io.renren.service.married.MarryBlessingService;
import io.renren.service.married.MarryMainService;
import io.renren.service.married.MarryPhotoService;
import io.renren.service.married.MarryWeddingService;
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
	@Autowired
	private MarryWeddingService marryWeddingService;
	@Autowired
	private MarryBlessingService marryBlessingService;
	@Autowired
	private MarryPhotoService marryPhotoService;
	
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
					//婚礼图片
					if (type.equals(TypeEnum.MARRIED_WEDDING_PIC.getType())){
						marrywedding(jsona);
						return json;
					}
					//视频祝福
					if (type.equals(TypeEnum.MARRIED_VIDEO_PIC.getType())){
						video(jsona);
						return json;
					}
					//婚礼图片
					if (type.equals(TypeEnum.MARRIED_PHOTO_PIC.getType())){
						photo(jsona);
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
	 * 婚礼摄影图片
	 * @param json
	 */
	private void photo(JSONObject json){
		//信息图片
    	Integer id = json.containsKey("id") ? json.getInt("id") : null;
		if (id != null) {
			MarryPhotoEntity marryPhotoEntity = new MarryPhotoEntity();
			marryPhotoEntity.setWeddingid(id);
			marryPhotoEntity.setType(1);
			marryPhotoEntity.setPic(ControllerConstant.CDN_URL + json.getString("filename"));
			marryPhotoService.insert(marryPhotoEntity);
		}
	}
	
	/**
	 * 视频祝福上传
	 * @param json
	 */
	private void video(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
		if (id != null) {
			MarryBlessingEntity mbe = this.marryBlessingService.selectById(id);
			if (mbe != null) {
				mbe.setVideoblessing(ControllerConstant.CDN_URL + json.getString("filename"));
				this.marryBlessingService.update(mbe);
			} else {
				logger.error("回调关联数据不存在");
				logger.error(json.toString());
			}
		}
	}
	
	/**
	 * 婚礼图片上传
	 * @param json
	 */
	private void marrywedding(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
		if (id != null) {
			MarryWeddingEntity marryWeddingEntity = this.marryWeddingService.selectById(id);
			if (marryWeddingEntity != null) {
				marryWeddingEntity.setPhoto(ControllerConstant.CDN_URL + json.getString("filename"));
				this.marryWeddingService.update(marryWeddingEntity);
			} else {
				logger.error("回调关联数据不存在");
				logger.error(json.toString());
			}
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
