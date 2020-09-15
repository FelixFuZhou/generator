package com.ly.local.common.dao;

import org.springframework.stereotype.Repository;
import com.ly.local.common.entity.SoundVideoAuth;

/**
 * 用户表DAO接口
 * @author fu.zhou
 * @version 2020-08-28
 */
@Repository("SoundVideoAuth")
public interface SoundVideoAuthDao extends CrudDao<SoundVideoAuth> {
	
}