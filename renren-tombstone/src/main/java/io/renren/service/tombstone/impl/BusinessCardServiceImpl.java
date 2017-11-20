package io.renren.service.tombstone.impl;

import io.renren.dao.tombstone.BusinessCardDao;
import io.renren.entity.tombstone.BusinessCardEntity;
import io.renren.service.tombstone.BusinessCardService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("businessCardService")
public class BusinessCardServiceImpl extends ServiceImpl<BusinessCardDao, BusinessCardEntity>  implements BusinessCardService {
	@Autowired
	private BusinessCardDao businessCardDao;
	
	@Override
	public BusinessCardEntity queryObject(Integer id){
		return businessCardDao.queryObject(id);
	}
	
	@Override
	public List<BusinessCardEntity> queryList(Map<String, Object> map){
		return businessCardDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return businessCardDao.queryTotal(map);
	}
	
	@Override
	public void save(BusinessCardEntity businessCard){
		businessCardDao.save(businessCard);
	}
	
	@Override
	public void update(BusinessCardEntity businessCard){
		businessCardDao.update(businessCard);
	}
	
	@Override
	public void delete(Integer id){
		businessCardDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		businessCardDao.deleteBatch(ids);
	}
	
}
