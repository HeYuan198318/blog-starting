package com.hy.controller;

import com.hy.entity.User;
import com.hy.service.BlogService;
import com.hy.service.TagService;
import com.hy.service.TypeService;
import com.hy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


/**
 * @Description: 首页控制器
 * @Author: ONESTAR
 * @Date: Created in 13:59 2020/3/25
 * @QQ群: 530311074
 * @URL: https://onestar.newstar.net.cn/
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }

    //搜索博客
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    //跳转博客详情页面
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    //最新博客列表
    @GetMapping("/footer/newblog")
    public String newblogs(Model model, HttpSession session) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        User user=(User)session.getAttribute("user");
        if (user!=null){
            model.addAttribute("index",userService.getUserInfo(user));
        }else{
            user=new User();
            user.setId((long) 1);
            model.addAttribute("index",userService.getUserInfo(user));
        }

        return "_fragments :: newblogList";
    }


//    最新博客列表
//    @GetMapping("/footer/newblog")
//    public String newblogs(Model model) {
//        List<FirstPageBlog> newBlog = blogService.getNewBlog();
//        model.addAttribute("newblogs", newBlog);
//        return "index :: newblogList";
//    }

//    博客信息
    @GetMapping("/footer/blogmessage")
    public String blogMessage(Model model){
//        int blogTotal = blogService.getBlogTotal();
//        int blogViewTotal = blogService.getBlogViewTotal();
//        int blogCommentTotal = blogService.getBlogCommentTotal();
//        int blogMessageTotal = blogService.getBlogMessageTotal();
//
//        model.addAttribute("blogTotal",blogTotal);
//        model.addAttribute("blogViewTotal",blogViewTotal);
//        model.addAttribute("blogCommentTotal",blogCommentTotal);
//        model.addAttribute("blogMessageTotal",blogMessageTotal);
        return "index :: blogMessage";
    }
}