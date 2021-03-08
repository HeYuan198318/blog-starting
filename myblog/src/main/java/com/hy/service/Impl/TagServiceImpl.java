package com.hy.service.Impl;

import com.hy.dao.TagDao;
import com.hy.entity.Tag;
import com.hy.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author C3006248
 * @create 2020/11/24  16:15
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagDao tagDao;

    @Override
    public List<Tag> getTagListTop() {
        return tagDao.getTagListTop();
    }

    @Override
    public List<Tag> getAllTagAndBlog() {
        return tagDao.getAllTagAndBlog();
    }

    @Override
    public List<Tag> getAllTag() {
        return tagDao.selectAll();
    }

    @Override
    public Tag getTag(Long id) {
        Tag tag=new Tag();
        tag.setId(id);
        return tagDao.selectOne(tag);
    }

    @Override
    public Tag getTagByName(String name) {
        Example e= new Example(Tag.class);
        Example.Criteria criteria = e.createCriteria(); // 添加条件
        criteria.andLike("name", name);
        return tagDao.selectOneByExample(e);
    }

    @Override
    public Integer saveTag(Tag tag) {
        return tagDao.insert(tag);
    }

    @Override
    public int deleteTag(Long id) {
        Tag tag=new Tag();
        tag.setId(id);
        int i=tagDao.delete(tag);
        return i;
    }

    @Override
    public Integer updateType(Tag tag) {
        return tagDao.updateByPrimaryKeySelective(tag);
    }
}
