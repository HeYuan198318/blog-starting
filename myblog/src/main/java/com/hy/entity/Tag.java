package com.hy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author C3006248
 * @create 2020/11/24  16:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_tag")
public class Tag implements Serializable {

    @Id
    private Long id;

    private String name;

    @Transient
    private List<Blog> blogs = new ArrayList<>();
}
