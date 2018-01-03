package io.renren.dao;

import io.renren.entity.UserEntity;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:06
 */
public interface UserDao extends BaseDao<UserEntity>,BaseMapper<UserEntity> {

    UserEntity queryByMobile(String mobile);
}
 