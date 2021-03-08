package com.hy.service;

import com.hy.entity.Picture;

import java.util.List;

/**
 * @author :C3006248
 * @Description:照片墙业务层接口
 * @create : 2020/11/25 17:15
 */
public interface PictureService {

    //查询照片
    List<Picture> listPicture();

    //添加图片
    int savePicture(Picture picture);

    //根据id查询照片
    Picture getPicture(Long id);

//    编辑修改相册
    int updatePicture(Picture picture);

//    删除照片
    void deletePicture(Long id);

}