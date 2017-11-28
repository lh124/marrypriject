package io.renren.service.smart.impl;

import io.renren.dao.smart.SmartVideoDeviceDao;
import io.renren.entity.smart.SmartVideoDeviceEntity;
import io.renren.service.smart.SmartVideoDeviceService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("smartVideoDeviceService")
public class SmartVideoDeviceServiceImpl extends ServiceImpl<SmartVideoDeviceDao, SmartVideoDeviceEntity>  implements SmartVideoDeviceService {
	@Autowired
	private SmartVideoDeviceDao smartVideoDeviceDao;
	
	@Override
	public SmartVideoDeviceEntity queryObject(Integer id){
		return smartVideoDeviceDao.queryObject(id);
	}
	
	@Override
	public List<SmartVideoDeviceEntity> queryList(Map<String, Object> map){
		return smartVideoDeviceDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smartVideoDeviceDao.queryTotal(map);
	}
	
	@Override
	public void save(SmartVideoDeviceEntity smartVideoDevice){
		smartVideoDeviceDao.save(smartVideoDevice);
	}
	
	@Override
	public void update(SmartVideoDeviceEntity smartVideoDevice){
		smartVideoDeviceDao.update(smartVideoDevice);
	}
	
	@Override
	public void delete(Integer id){
		smartVideoDeviceDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		smartVideoDeviceDao.deleteBatch(ids);
	}
	
}
