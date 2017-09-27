package io.renren.service.smart.impl;

import io.renren.dao.smart.SysWeixinMenuDao;
import io.renren.entity.smart.SysWeixinMenuEntity;
import io.renren.service.smart.SysWeixinMenuService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("sysWeixinMenuService")
public class SysWeixinMenuServiceImpl extends ServiceImpl<SysWeixinMenuDao, SysWeixinMenuEntity>  implements SysWeixinMenuService {
	@Autowired
	private SysWeixinMenuDao sysWeixinMenuDao;
	
	@Override
	public SysWeixinMenuEntity queryObject(Integer id){
		return sysWeixinMenuDao.queryObject(id);
	}
	
	@Override
	public List<SysWeixinMenuEntity> queryList(Map<String, Object> map){
		return sysWeixinMenuDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysWeixinMenuDao.queryTotal(map);
	}
	
	@Override
	public void save(SysWeixinMenuEntity sysWeixinMenu){
		sysWeixinMenuDao.save(sysWeixinMenu);
	}
	
	@Override
	public void update(SysWeixinMenuEntity sysWeixinMenu){
		sysWeixinMenuDao.update(sysWeixinMenu);
	}
	
	@Override
	public void delete(Integer id){
		sysWeixinMenuDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		sysWeixinMenuDao.deleteBatch(ids);
	}
	
}
