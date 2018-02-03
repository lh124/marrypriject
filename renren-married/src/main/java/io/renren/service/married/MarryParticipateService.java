package io.renren.service.married;

import io.renren.entity.married.MarryParticipateEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-12-18 10:07:37
 */
public interface MarryParticipateService extends IService<MarryParticipateEntity>{
	
	MarryParticipateEntity queryObject(Integer id);
	
	List<MarryParticipateEntity> queryList(Map<String, Object> map);
	
	List<MarryParticipateEntity> queryList1(Map<String, Object> map);
	
	List<MarryParticipateEntity> queryListtongji(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarryParticipateEntity marryParticipate);
	
	void update(MarryParticipateEntity marryParticipate);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
