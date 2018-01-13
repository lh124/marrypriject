package io.renren.service.smart.impl;

import io.renren.dao.app.SmartAppDao;
import io.renren.entity.app.SmartAppEntity;
import io.renren.service.smart.SmartAppService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("smartAppService")
public class SmartAppServiceImpl extends ServiceImpl<SmartAppDao, SmartAppEntity>  implements SmartAppService {
	@Autowired
	private SmartAppDao smartAppDao;
	
	@Override
	public SmartAppEntity queryObject(Integer id){
		return smartAppDao.queryObject(id);
	}
	
	@Override
	public List<SmartAppEntity> queryList(Map<String, Object> map){
		return smartAppDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smartAppDao.queryTotal(map);
	}
	
	@Override
	public void save(SmartAppEntity smartApp){
		smartAppDao.save(smartApp);
	}
	
	@Override
	public void update(SmartAppEntity smartApp){
		smartAppDao.update(smartApp);
	}
	
	@Override
	public void delete(Integer id){
		smartAppDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		smartAppDao.deleteBatch(ids);
	}
	
}
