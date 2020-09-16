package com.hy.service;

import com.hy.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author hy
 * @date 2020/9/9 21:32
 */
public interface TypeService {
    Type saveType(Type type);
    Type getType(Long id);
    Page<Type> ListType(Pageable pageable);
    Type updateType(Long id,Type type);

    void deleteType(Long id);

    Type getTypeByName(String name);
}
