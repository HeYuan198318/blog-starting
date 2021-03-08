package com.hy.service;

import com.hy.entity.Message;

import java.util.List;

/**
 * @author :C3006248
 * @Description:留言业务层接口
 * @create : 2020/11/25 17:15
 */
public interface MessageService {

    //查询留言列表
    List<Message> listMessage();

    //保存留言
    int saveMessage(Message message);

    //删除留言
    void deleteMessage(Long id);

}