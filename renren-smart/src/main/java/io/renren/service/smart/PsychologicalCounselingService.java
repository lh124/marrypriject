package io.renren.service.smart;

import io.renren.entity.smart.PsychologicalCounselingEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-09-21 09:34:56
 */
public interface PsychologicalCounselingService extends IService<PsychologicalCounselingEntity>{
	
	PsychologicalCounselingEntity queryObject(Integer id);
	
	List<PsychologicalCounselingEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PsychologicalCounselingEntity psychologicalCounseling);
	
	void update(PsychologicalCounselingEntity psychologicalCounseling);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
