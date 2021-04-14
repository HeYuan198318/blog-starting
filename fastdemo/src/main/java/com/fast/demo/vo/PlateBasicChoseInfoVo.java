package com.fast.demo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author:C3006248
 * @Description: 材質下拉選擇信息
 * @Date:Created in 下午 02:50 2021/4/14
 */
@Data
public class PlateBasicChoseInfoVo implements Serializable {

    @ApiModelProperty(value = "材質")
    private String material;

    @ApiModelProperty(value = "編碼原則")
    private String codePrinciple;
}
