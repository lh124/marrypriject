package io.renren.service.smart.impl;

import io.renren.dao.smart.SysWeixinDao;
import io.renren.entity.smart.SysWeixinEntity;
import io.renren.service.smart.SysWeixinService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("sysWeixinService")
public class SysWeixinServiceImpl extends ServiceImpl<SysWeixinDao, SysWeixinEntity>  implements SysWeixinService {
	@Autowired
	private SysWeixinDao sysWeixinDao;
	
	@Override
	public SysWeixinEntity queryObject(Integer id){
		return sysWeixinDao.queryObject(id);
	}
	
	@Override
	public List<SysWeixinEntity> queryList(Map<String, Object> map){
		return sysWeixinDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysWeixinDao.queryTotal(map);
	}
	
	@Override
	public void save(SysWeixinEntity sysWeixin){
		sysWeixinDao.save(sysWeixin);
	}
	
	@Override
	public void update(SysWeixinEntity sysWeixin){
		sysWeixinDao.update(sysWeixin);
	}
	
	@Override
	public void delete(Integer id){
		sysWeixinDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		sysWeixinDao.deleteBatch(ids);
	}
	
}
