package com.hy;

import com.hy.dao.UserTestDao;
import com.hy.entity.UserTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootTest
@Slf4j
class MyblogApplicationTests {

    @Autowired
    UserTestDao userTestDao;

    @Test
    void contextLoads() {
        //點讚
        final String getTokenUrl = "http://iedu.foxconn.com/Improve2020/Class/LikeWork?workId=55&";
        //瀏覽
        final String lookUrl="http://iedu.foxconn.com/Improve2020/Class/VisitWork?workId=55&";
        String woikId="49";
        String platForm="PC";
        RestTemplate restTemplate = new RestTemplate();
        List<UserTest> userTestList=userTestDao.selectAll();
        //遍歷工號集合
        for(int i=0;i<userTestList.size();i++) {
            //System.out.println(userTestList.get(i).getUser_name());
            String msgInfo = restTemplate.postForObject(getTokenUrl + "username=" + userTestList.get(i).getUser_name() + "&platForm=" + platForm, HttpMethod.POST, String.class);
            //System.out.println(msgInfo);
            log.info(msgInfo);
        }

        //遍歷工號集合
       /* for(int i=0;i<userTestList.size();i++) {
            //System.out.println(userTestList.get(i).getUser_name());
            String msgInfo = restTemplate.postForObject(lookUrl + "username=" +
                    userTestList.get(i).getUser_name() + "&platForm=" + platForm, HttpMethod.POST, String.class);
            log.info(msgInfo);
        }*/

    }


}
