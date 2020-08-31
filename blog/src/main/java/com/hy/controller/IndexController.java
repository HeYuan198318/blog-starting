package com.hy.controller;

import com.hy.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @Description: 首页控制器
 * @Author: ONESTAR
 * @Date: Created in 13:59 2020/3/25
 * @QQ群: 530311074
 * @URL: https://onestar.newstar.net.cn/
 */
@Controller
public class IndexController {

   /* @Autowired
    private BlogDao blogDao;

    @Autowired
    private BlogService blogService;

//    @Autowired
//    private TypeService typeService;

    @Autowired
    private CommentService commentService;
*/
//    分页查询博客列表
    @GetMapping("/")
    public String index(){
      /*  PageHelper.startPage(pageNum,10);
        List<FirstPageBlog> allFirstPageBlog = blogService.getAllFirstPageBlog();
        List<RecommendBlog> recommendedBlog = blogService.getRecommendedBlog();

        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(allFirstPageBlog);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("recommendedBlogs", recommendedBlog);*/
//        int i=9/0;
//        String blog=null;
//        if (blog==null){
//            throw new NotFoundException("找不到");
//        }

        return "index";
    }
/*
//    搜索博客
    @PostMapping("/search")
    public String search(Model model,
                         @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         @RequestParam String query) {
        PageHelper.startPage(pageNum, 1000);
        List<FirstPageBlog> searchBlog = blogService.getSearchBlog(query);
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(searchBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }

//    跳转博客详情页面
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        DetailedBlog detailedBlog = blogService.getDetailedBlog(id);
        List<Comment> comments = commentService.listCommentByBlogId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("blog", detailedBlog);
        return "blog";
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
    }*/
}