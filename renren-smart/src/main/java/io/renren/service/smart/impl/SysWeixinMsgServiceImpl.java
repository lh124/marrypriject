package io.renren.service.smart.impl;

import io.renren.dao.smart.SysWeixinMsgDao;
import io.renren.entity.smart.SysWeixinMsgEntity;
import io.renren.service.smart.SysWeixinMsgService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("sysWeixinMsgService")
public class SysWeixinMsgServiceImpl extends ServiceImpl<SysWeixinMsgDao, SysWeixinMsgEntity>  implements SysWeixinMsgService {
	@Autowired
	private SysWeixinMsgDao sysWeixinMsgDao;
	
	@Override
	public SysWeixinMsgEntity queryObject(Integer id){
		return sysWeixinMsgDao.queryObject(id);
	}
	
	@Override
	public List<SysWeixinMsgEntity> queryList(Map<String, Object> map){
		return sysWeixinMsgDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysWeixinMsgDao.queryTotal(map);
	}
	
	@Override
	public void save(SysWeixinMsgEntity sysWeixinMsg){
		sysWeixinMsgDao.save(sysWeixinMsg);
	}
	
	@Override
	public void update(SysWeixinMsgEntity sysWeixinMsg){
		sysWeixinMsgDao.update(sysWeixinMsg);
	}
	
	@Override
	public void delete(Integer id){
		sysWeixinMsgDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		sysWeixinMsgDao.deleteBatch(ids);
	}
	
}
