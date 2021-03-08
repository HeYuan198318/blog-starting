package com.hy.service;

import com.hy.entity.Tag;

import java.util.List;

/**
 * @author C3006248
 * @create 2020/11/24  16:15
 */
public interface TagService {
    //前六条标签信息
    List<Tag> getTagListTop();

    List<Tag> getAllTagAndBlog();

    List<Tag> getAllTag();

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Integer saveTag(Tag tag);

    int deleteTag(Long id);

    Integer updateType(Tag tag);
}
