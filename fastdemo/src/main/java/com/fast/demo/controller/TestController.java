package com.fast.demo.controller;

import com.fast.demo.basic.controller.BaseApiController;
import com.fast.demo.basic.vo.ResponseMsg;
import com.fast.demo.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:C3006248
 * @Description:
 * @Date:Created in 下午 02:25 2021/4/14
 */
@RestController
@RequestMapping("/fast")
@Api("test")
public class TestController extends BaseApiController {

    @Autowired
    TestService testService;

    @ApiOperation(value = "獲取材質/編碼原則")
    @GetMapping("/getAllCodePrinciple")
    public ResponseMsg getAllCodePrinciple(String factory){
        ResponseMsg rm=null;
        try {
            rm=testService.getAllCodePrinciple(factory);
        }catch (Exception e){
            e.printStackTrace();
            return setResultError(e.getMessage());
        }
        return rm;
    }

}
