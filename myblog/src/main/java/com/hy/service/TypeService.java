package com.hy.service;

import com.hy.entity.Type;

import java.util.List;


/**
 * @author :C3006248
 * @Description:文章分类业务层接口
 * @create : 2020/11/25 17:16
 */
public interface TypeService {

    int saveType(Type type);

    Type getType(Long id);

    List<Type>getAllType();

    List<Type>getAllTypeAndBlog();

    Type getTypeByName(String name);

    int updateType(Type type);

    void deleteType(Long id);

    List<Type> getTypeListTop();
}