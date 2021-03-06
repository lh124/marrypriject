package io.renren.service.smart.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.smart.IoDao;
import io.renren.entity.smart.IoEntity;
import io.renren.service.smart.IoService;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("ioService")
public class IoServiceImpl extends ServiceImpl<IoDao, IoEntity>  implements IoService {
	@Autowired
	private IoDao ioDao;
	
	@Override
	public IoEntity queryObject(Integer id){
		return ioDao.queryObject(id);
	}
	
	@Override
	public List<IoEntity> queryList(Map<String, Object> map){
		return ioDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ioDao.queryTotal(map);
	}
	
	@Override
	public void save(IoEntity io){
		ioDao.save(io);
	}
	
	@Override
	public void update(IoEntity io){
		ioDao.update(io);
	}
	
	@Override
	public void delete(Integer id){
		ioDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		ioDao.deleteBatch(ids);
	}
	
}
