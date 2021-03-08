package com.hy.service.Impl;

import com.hy.NotFoundException;
import com.hy.dao.BlogDao;
import com.hy.dao.TagDao;
import com.hy.entity.Blog;
import com.hy.queryvo.*;
import com.hy.service.BlogService;
import com.hy.util.MarkdownUtils;
import com.hy.util.MySystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author :C3006248
 * @Description:博客列表业务层接口实现类
 * @create : 2020/11/25 17:08
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private TagDao tagDao;

    @Override
    public ShowBlog getBlogById(Long id) {
        return blogDao.getBlogById(id);
    }

    @Override
    public List<BlogQuery> getAllBlog() {
        return blogDao.getAllBlogQuery();
    }

    @Override
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blog.setCommentCount(0);
        int b= blogDao.saveBlog(blog);
        if(!MySystemUtil.isStrEmpty(blog.getTagIds())){
            String[] str_ids=blog.getTagIds().split(",");
            //组装id的集合
            /*for (String string:str_ids){
                Map<String, Long> map = new HashMap<>();
                map.put("blogId", blog.getId());
                map.put("tagId", Long.parseLong(string));
                Integer i=tagDao.insertByblog(map);
            }*/
        }
        return b;
    }

    @Override
    public int updateBlog(ShowBlog showBlog) {
        showBlog.setUpdateTime(new Date());
        if(!MySystemUtil.isStrEmpty(showBlog.getTagIds())){
            String[] str_ids=showBlog.getTagIds().split(",");
            //删除原先的关联集合
            Integer i=tagDao.deleteByblog(showBlog.getId());
            //组装id的集合
            for (String string:str_ids){
                Map<String, Long> map = new HashMap<>();
                map.put("blogId", showBlog.getId());
                map.put("tagId", Long.parseLong(string));
                Integer n=tagDao.insertByblog(map);
            }
        }
        return blogDao.updateBlog(showBlog);
    }

    @Override
    public void deleteBlog(Long id) {
        blogDao.deleteBlog(id);
    }

    @Override
    public List<BlogQuery> getBlogBySearch(SearchBlog searchBlog) {
        return blogDao.searchByTitleOrTypeOrRecommend(searchBlog);
    }

    @Override
    public List<FirstPageBlog> getAllFirstPageBlog() {
        return blogDao.getFirstPageBlog();
    }

    @Override
    public List<RecommendBlog> getRecommendedBlog() {
        List<RecommendBlog> allRecommendBlog = blogDao.getAllRecommendBlog();
        return allRecommendBlog;
    }

    //前三条
    @Override
    @Cacheable(cacheNames = {"newBlog"})
    public List<FirstPageBlog> getNewBlog() {
        return blogDao.getNewBlog();
    }

    @Override
    public List<FirstPageBlog> getSearchBlog(String query) {
        return blogDao.getSearchBlog(query);
    }

    @Override
    public DetailedBlog getDetailedBlog(Long id) {
        System.out.println(id);
        DetailedBlog detailedBlog = blogDao.getDetailedBlog(id);
        if (detailedBlog == null) {
            throw new NotFoundException("该博客不存在");
        }
        String content = detailedBlog.getContent();
        detailedBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
//        文章访问数量自增
        blogDao.updateViews(id);
//        文章评论数量更新
        blogDao.getCommentCountById(id);
        return detailedBlog;
    }


    @Override
    public List<FirstPageBlog> getByTypeId(Long typeId) {
        return blogDao.getByTypeId(typeId);
    }

    @Override
    public Integer getBlogTotal() {
        return blogDao.getBlogTotal();
    }

    @Override
    public Integer getBlogViewTotal() {
        return blogDao.getBlogViewTotal();
    }

    @Override
    public Integer getBlogCommentTotal() {
        return blogDao.getBlogCommentTotal();
    }

    @Override
    public Integer getBlogMessageTotal() {
        return blogDao.getBlogMessageTotal();
    }

    //根据标签id获取博客信息
    @Override
    public List<FirstPageBlog> getByTagId(Long id) {
        return blogDao.getByTagId(id);
    }
}