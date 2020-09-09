package com.hy.service;

import com.github.pagehelper.Page;
import com.hy.entity.Type;
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

    int deleteType(Long id);
}
