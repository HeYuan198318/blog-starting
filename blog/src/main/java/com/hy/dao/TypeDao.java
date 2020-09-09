package com.hy.dao;

import com.github.pagehelper.Page;
import com.hy.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

/**
 * @author hy
 * @date 2020/9/9 21:35
 */
public interface TypeDao extends JpaRepository<Type,Long> {
   // Page<Type> findAll(Pageable pageable);
}
