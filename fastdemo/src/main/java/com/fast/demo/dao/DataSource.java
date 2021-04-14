package com.fast.demo.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @Author:C3006248
 * @Description:
 * @Date:Created in 下午 03:35 2021/4/14
 */
@Repository(value = "DataSource")
public class DataSource implements TestDao{

    @Value("${dataSource.api.url}")
    private String dbUrl;

    @Override
    public String TEST_API(){
        return this.dbUrl+"/materialplate/plateWarehouse/getAllCodePrinciple?factory={factory}";
    }

}
