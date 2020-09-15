package com.ly.carpoll.walkmoney.dao;

import org.springframework.stereotype.Repository;
import com.ly.carpoll.walkmoney.entity.OperationStrategy;

/**
 * 营销平台策略表DAO接口
 * @author fu.zhou
 * @version 2020-04-22
 */
@Repository("OperationStrategy")
public interface OperationStrategyDao extends CrudDao<OperationStrategy> {
	
}