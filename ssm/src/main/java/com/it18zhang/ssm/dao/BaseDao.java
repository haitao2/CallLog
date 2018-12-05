package com.it18zhang.ssm.dao;

import java.util.List;

/**
 * 基本Dao接口
 */
public interface BaseDao<T> {
    void insert(T t);

    void update(T t);

    void delete(Integer id);

    T selectOne(Integer id);

    List<T> selectAll();

    List<T> selectPage(int offset,int len);

    int selectCount();
}
