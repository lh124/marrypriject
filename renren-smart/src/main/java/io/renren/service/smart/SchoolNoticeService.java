package io.renren.service.smart;

import io.renren.entity.smart.SchoolNoticeEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-09-19 09:12:53
 */
public interface SchoolNoticeService extends IService<SchoolNoticeEntity>{
	
	SchoolNoticeEntity queryObject(Integer id);
	
	List<SchoolNoticeEntity> queryList(Map<String, Object> map);
	
	List<SchoolNoticeEntity> queryListtongji(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SchoolNoticeEntity schoolNotice);
	
	void update(SchoolNoticeEntity schoolNotice);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
