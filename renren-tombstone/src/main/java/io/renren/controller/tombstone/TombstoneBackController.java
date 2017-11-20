package io.renren.controller.tombstone;

import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.tombstone.BombstonePicEntity;
import io.renren.entity.tombstone.BusinessCardEntity;
import io.renren.entity.tombstone.TombstoneDeadEntity;
import io.renren.entity.tombstone.TombstoneUserEntity;
import io.renren.enums.TypeEnum;
import io.renren.service.tombstone.BombstonePicService;
import io.renren.service.tombstone.BusinessCardService;
import io.renren.service.tombstone.TombstoneDeadService;
import io.renren.service.tombstone.TombstoneUserService;
import io.renren.utils.OssCallBackUtil;

import java.util.Date;

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
@RequestMapping("/tombstone/callBack/")
public class TombstoneBackController {

	// 日志操作
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TombstoneUserService tombstoneUserService;
	@Autowired
	private TombstoneDeadService tombstoneDeadService;
	@Autowired
	private BombstonePicService bombstonePicService;
	@Autowired
	private BusinessCardService businessCardService;
	@ResponseBody
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
					// 头像上传
					if (type.equals(TypeEnum.TOMBSTONE_USER_PIC.getType())){
						updateUserImage(jsona);
						return json;
					}
					//家谱头像
					if (type.equals(TypeEnum.TOMBSTONE_DEAD_PIC.getType())){
						updateDeadImage(jsona);
						return json;
					}
					//个人图片
					if (type.equals(TypeEnum.TOMBSTONE_DEADUSER_PIC.getType())){
						updateDeadUserImage(jsona);
						return json;
					}
					//个人名片
					if (type.equals(TypeEnum.BUSINESS_CARD_PIC.getType())){
						updatecardImage(jsona);
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
	 * 个人名片上传
	 * @param json
	 */
	private void updatecardImage(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
    	Long pictype = json.containsKey("pictype") ? json.getLong("pictype") : null;
    	BusinessCardEntity businessCard = businessCardService.selectById(id);
    	if(businessCard != null){
    		if(pictype == 1){
    			businessCard.setPic(ControllerConstant.CDN_URL + json.getString("filename"));
    		}else if(pictype == 2){
    			businessCard.setWeixinpic(ControllerConstant.CDN_URL + json.getString("filename"));
    		}
    		businessCardService.update(businessCard);
    	}
	}
	
	/**
	 * 个人图片头像上传
	 * @param json
	 */
	private void updateDeadUserImage(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
    	BombstonePicEntity bombstonePic = new BombstonePicEntity();
    	bombstonePic.setPic(ControllerConstant.CDN_URL + json.getString("filename"));
    	bombstonePic.setCreatetime(new Date());
    	bombstonePic.setUserid(id + "");
    	bombstonePicService.save(bombstonePic);
	}
	
	/**
	 * 家谱头像上传
	 * @param json
	 */
	private void updateDeadImage(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
		if (id != null) {
			TombstoneDeadEntity smartCourseware = this.tombstoneDeadService.selectById(id);
			if (smartCourseware != null) {
				smartCourseware.setImage(ControllerConstant.CDN_URL + json.getString("filename"));
				this.tombstoneDeadService.update(smartCourseware);
			} else {
				logger.error("智能校服随堂课件回调关联数据不存在");
				logger.error(json.toString());
			}
		}
	}
	
	/**
	 * 用户头像上传
	 * @param json
	 */
	private void updateUserImage(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
    	Long type2 = json.containsKey("type2") ? json.getLong("type2") : null;
		if (id != null) {
			TombstoneUserEntity smartCourseware = this.tombstoneUserService.selectById(id);
			if (smartCourseware != null) {
				if(type2==2){
					smartCourseware.setShipin(ControllerConstant.CDN_URL + json.getString("filename"));
				}else{
					smartCourseware.setPic(ControllerConstant.CDN_URL + json.getString("filename"));
				}
				this.tombstoneUserService.update(smartCourseware);
			} else {
				logger.error("智能校服随堂课件回调关联数据不存在");
				logger.error(json.toString());
			}
		}
	}
	
	
}
