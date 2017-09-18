package io.renren.service.smart;

import com.baomidou.mybatisplus.service.IService;

import io.renren.entity.smart.PhotoPicWorkMsgEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-18 13:12:52
 */
public interface PhotoPicWorkMsgService extends IService<PhotoPicWorkMsgEntity>{
	
	PhotoPicWorkMsgEntity queryObject(Long id);
	
	List<PhotoPicWorkMsgEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoPicWorkMsgEntity photoPicWorkMsg);
	
	void update(PhotoPicWorkMsgEntity photoPicWorkMsg);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
