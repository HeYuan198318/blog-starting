package com.hy.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author:C3006248
 * @Description:
 * @Date:Created in ${TITM} 2020/12/10
 */
@Data
@Table(name = "t_userTest")
public class UserTest {
    @Id
    private String id;

    @Column(name = "userName")
    private String user_name;
}
