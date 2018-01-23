package io.renren.service.app.impl;

import io.renren.dao.app.WorkMainDao;
import io.renren.entity.app.WorkMainEntity;
import io.renren.service.app.WorkMainService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("workMainService")
public class WorkMainServiceImpl extends ServiceImpl<WorkMainDao, WorkMainEntity>  implements WorkMainService {
	@Autowired
	private WorkMainDao workMainDao;
	
	@Override
	public WorkMainEntity queryObject(Integer id){
		return workMainDao.queryObject(id);
	}
	
	@Override
	public List<WorkMainEntity> queryList(Map<String, Object> map){
		return workMainDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return workMainDao.queryTotal(map);
	}
	
	@Override
	public void save(WorkMainEntity workMain){
		workMainDao.save(workMain);
	}
	
	@Override
	public void update(WorkMainEntity workMain){
		workMainDao.update(workMain);
	}
	
	@Override
	public void delete(Integer id){
		workMainDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		workMainDao.deleteBatch(ids);
	}
	
}
