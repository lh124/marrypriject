package io.renren.service.smart.impl;

import io.renren.dao.smart.FreshmanGuideDao;
import io.renren.entity.smart.FreshmanGuideEntity;
import io.renren.service.smart.FreshmanGuideService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("freshmanGuideService")
public class FreshmanGuideServiceImpl extends ServiceImpl<FreshmanGuideDao, FreshmanGuideEntity>  implements FreshmanGuideService {
	@Autowired
	private FreshmanGuideDao freshmanGuideDao;
	
	@Override
	public FreshmanGuideEntity queryObject(Integer id){
		return freshmanGuideDao.queryObject(id);
	}
	
	@Override
	public List<FreshmanGuideEntity> queryList(Map<String, Object> map){
		return freshmanGuideDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return freshmanGuideDao.queryTotal(map);
	}
	
	@Override
	public void save(FreshmanGuideEntity freshmanGuide){
		freshmanGuideDao.save(freshmanGuide);
	}
	
	@Override
	public void update(FreshmanGuideEntity freshmanGuide){
		freshmanGuideDao.update(freshmanGuide);
	}
	
	@Override
	public void delete(Integer id){
		freshmanGuideDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		freshmanGuideDao.deleteBatch(ids);
	}
	
}
