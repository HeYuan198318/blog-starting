package com.hy.controller.admin;

import com.github.pagehelper.Page;
import com.hy.entity.Type;
import com.hy.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hy
 * @date 2020/9/9 21:53
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable
                       , Model model){
        Page<Type> types = typeService.ListType(pageable);
        model.addAttribute("page",types);
        return "admin/types";
    }

}
