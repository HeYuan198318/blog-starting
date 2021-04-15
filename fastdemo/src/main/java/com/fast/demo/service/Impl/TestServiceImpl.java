package com.fast.demo.service.Impl;

import com.fast.demo.basic.service.BaseApiService;
import com.fast.demo.basic.vo.ResponseMsg;
import com.fast.demo.dao.DataSource;
import com.fast.demo.service.TestService;
import com.fast.demo.vo.PlateBasicChoseInfoVo;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.deploy.net.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:C3006248
 * @Description:
 * @Date:Created in 下午 02:26 2021/4/14
 */
@Service
public class TestServiceImpl extends BaseApiService implements TestService {

    @Autowired
    RestTemplate restTemplate;

    @Resource(name = "DataSource")
    DataSource dataSource;


    @Override
    public ResponseMsg getAllCodePrinciple(String factory) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("factory", factory);
        ResponseMsg data=restTemplate.getForObject(dataSource.TEST_API(), ResponseMsg.class,params);
        List<PlateBasicChoseInfoVo> list= (List<PlateBasicChoseInfoVo>) data.getData();

        return setResultSuccessData(list);
    }
}
