package io.renren.service.smart;

import com.baomidou.mybatisplus.service.IService;

import io.renren.entity.smart.PhotoClassWorkMsgEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-17 16:11:02
 */
public interface PhotoClassWorkMsgService extends IService<PhotoClassWorkMsgEntity>{
	
	PhotoClassWorkMsgEntity queryObject(Long id);
	
	List<PhotoClassWorkMsgEntity> queryList(Map<String, Object> map);
	
	List<PhotoClassWorkMsgEntity> queryListtongji(Map<String, Object> map); 
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoClassWorkMsgEntity photoClassWorkMsg);
	
	void update(PhotoClassWorkMsgEntity photoClassWorkMsg);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
