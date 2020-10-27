package com.hy.dao;

import com.github.pagehelper.Page;
import com.hy.entity.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author hy
 * @date 2020/9/9 21:35
 */
public interface TypeDao extends JpaRepository<Type,Long> {
    Type findByName(String name);
    // Page<Type> findAll(Pageable pageable);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
