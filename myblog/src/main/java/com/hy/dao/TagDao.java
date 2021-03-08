package com.hy.dao;

import com.hy.entity.Tag;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author C3006248
 * @create 2020/11/24  16:17
 */
@Repository
public interface TagDao extends Mapper<Tag> {
    List<Tag> getTagListTop();

    List<Tag> getAllTagAndBlog();

    Integer insertByblog(Map<String, Long> map);

    Integer deleteByblog(Long parseInt);
}
