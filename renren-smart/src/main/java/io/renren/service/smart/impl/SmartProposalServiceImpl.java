package io.renren.service.smart.impl;

import io.renren.dao.smart.SmartProposalDao;
import io.renren.entity.smart.SmartProposalEntity;
import io.renren.service.smart.SmartProposalService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("smartProposalService")
public class SmartProposalServiceImpl extends ServiceImpl<SmartProposalDao, SmartProposalEntity>  implements SmartProposalService {
	@Autowired
	private SmartProposalDao smartProposalDao;
	
	@Override
	public SmartProposalEntity queryObject(Integer id){
		return smartProposalDao.queryObject(id);
	}
	
	@Override
	public List<SmartProposalEntity> queryList(Map<String, Object> map){
		return smartProposalDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smartProposalDao.queryTotal(map);
	}
	
	@Override
	public void save(SmartProposalEntity smartProposal){
		smartProposalDao.save(smartProposal);
	}
	
	@Override
	public void update(SmartProposalEntity smartProposal){
		smartProposalDao.update(smartProposal);
	}
	
	@Override
	public void delete(Integer id){
		smartProposalDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		smartProposalDao.deleteBatch(ids);
	}
	
}
