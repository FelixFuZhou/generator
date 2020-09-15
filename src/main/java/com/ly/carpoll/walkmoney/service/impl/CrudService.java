package com.ly.carpoll.walkmoney.service.impl;

import com.ly.carpoll.walkmoney.dao.CrudDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service基类
 * @author zhoufu
 * @version
 * @param <D>
 * @param <T>
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T > {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    /**
     * 获取单条数据
     * @param id
     * @return
     */
    public T get(long id){
        return dao.get (id);
    }

    /**
     * 获取单条数据
     * @param entity
     * @return
     */
    public T get(T entity){
        return dao.getByBean (entity);
    }

    /**
     * 查询列表数据
     * @param entity
     * @return
     */
    public List<T> findList(T entity){
        return dao.findList (entity);
    }


    /**
     * 保存数据（插入或更新）
     * @param entity
     * @return
     */
    @Transactional(readOnly = false)
    public int save(T entity){
      return   dao.insert (entity);
    }

    /**
     * 删除数据
     * @param entity
     */
    @Transactional(readOnly = false)
    public void delete(T entity){
        dao.delete (entity);
    }


    /**
     * 获取一条记录
     * @param entity
     * @return
     */
    public T getBean(T entity){
        List<T> list= dao.findList (entity);
        if(CollectionUtils.isNotEmpty (list)){
            return list.get (0);
        }
       return null;
    }
    
    
    /**
     * 保存数据（插入或更新）
     * @param entity
     * @return
     */
    @Transactional(readOnly = false)
    public int update(T entity){
        return  dao.update (entity);
    }

}
