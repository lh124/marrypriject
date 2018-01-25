package io.renren.service.smart;

import io.renren.entity.smart.SmartProposalEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-25 15:47:28
 */
public interface SmartProposalService extends IService<SmartProposalEntity>{
	
	SmartProposalEntity queryObject(Integer id);
	
	List<SmartProposalEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmartProposalEntity smartProposal);
	
	void update(SmartProposalEntity smartProposal);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
