package io.renren.service.smart;

import io.renren.entity.smart.ClassNoticeEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-09-20 10:04:06
 */
public interface ClassNoticeService extends IService<ClassNoticeEntity>{
	
	ClassNoticeEntity queryObject(Integer id);
	
	List<ClassNoticeEntity> queryList(Map<String, Object> map);
	
	List<ClassNoticeEntity> queryListtongji(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ClassNoticeEntity classNotice);
	
	void update(ClassNoticeEntity classNotice);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
