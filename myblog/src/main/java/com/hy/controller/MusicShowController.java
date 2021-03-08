package com.hy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author :C3006248
 * @Description:音乐盒页面显示控制器
 * @create : 2020/11/25 15:27
 */
@Controller
public class MusicShowController {

    @GetMapping("/music")
    public String about() {
        return "music";
    }

}