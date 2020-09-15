package com.ly.local.common.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ly.carpoll.walkmoney.service.impl.CrudService;
import com.ly.local.common.entity.SoundVideoAuth;
import com.ly.local.common.dao.SoundVideoAuthDao;

/**
 * 用户表Service
 * @author fu.zhou
 * @version 2020-08-28
 */
@Service
@Transactional(readOnly = true)
public class SoundVideoAuthService extends CrudService<SoundVideoAuthDao, SoundVideoAuth> {

	
}