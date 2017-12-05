package io.renren.service.smart.impl;

import io.renren.dao.smart.SmartLeaveDao;
import io.renren.entity.smart.SmartLeaveEntity;
import io.renren.service.smart.SmartLeaveService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("smartLeaveService")
public class SmartLeaveServiceImpl extends ServiceImpl<SmartLeaveDao, SmartLeaveEntity>  implements SmartLeaveService {
	@Autowired
	private SmartLeaveDao smartLeaveDao;
	
	@Override
	public SmartLeaveEntity queryObject(Integer id){
		return smartLeaveDao.queryObject(id);
	}
	
	@Override
	public List<SmartLeaveEntity> queryList(Map<String, Object> map){
		return smartLeaveDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smartLeaveDao.queryTotal(map);
	}
	
	@Override
	public void save(SmartLeaveEntity smartLeave){
		smartLeaveDao.save(smartLeave);
	}
	
	@Override
	public void update(SmartLeaveEntity smartLeave){
		smartLeaveDao.update(smartLeave);
	}
	
	@Override
	public void delete(Integer id){
		smartLeaveDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		smartLeaveDao.deleteBatch(ids);
	}
	
}
