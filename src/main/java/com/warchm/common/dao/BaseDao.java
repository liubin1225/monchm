package com.warchm.common.dao;

import java.util.List;

/**
 * dao基类
 *
 * @author LiShuai
 * @date 2017/7/19 0019 13:41
 * @last_modified_date 2017/7/19 0019 13:41
 * @description
 */
@SuppressWarnings("all")
public interface BaseDao<T> {
    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(String id);

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    public T get(T entity);

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity);

    /**
     * 查询所有数据列表
     *
     * @param entity
     * @return
     */
    public List<T> findAllList(T entity);

    /**
     * 查询所有数据列表
     *
     * @return
     */
    public List<T> findAllList();

    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    public int insert(T entity);

    /**
     * 更新数据
     *
     * @param entity
     * @return
     */
    public int update(T entity);

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     *
     * @param id
     * @return
     */
    public int delete(String id);

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     *
     * @param entity
     * @return
     */
    public int delete(T entity);

}
