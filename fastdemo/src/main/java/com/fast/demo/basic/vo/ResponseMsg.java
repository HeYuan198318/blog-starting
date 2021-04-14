package com.fast.demo.basic.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

/**
 * 
 * @ClassName: ResponseMsg
 * @Description: 返回消息對象
 * @author:C3006748
 * @date 2021年3月30日 下午3:20:07
 * @version V1.0
 * @param <T>
 */
@Getter
@Setter
@ApiModel(description = "返回响应数据")
public class ResponseMsg<T> {

	public ResponseMsg() {
	};

	public ResponseMsg(Integer code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		if (data != null) {
			this.data = data;
		}

	}

	public ResponseMsg(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResponseMsg(String code, String msg) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		if (pattern.matcher(code).matches()) {
			this.code = Integer.valueOf(code);
		} else {
			this.code = 500;
		}
		this.msg = msg;
	}

	public ResponseMsg(String code, String msg, T data) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		if (pattern.matcher(code).matches()) {
			this.code = Integer.valueOf(code);
		} else {
			this.code = 500;
		}

		this.msg = msg;
		if (data != null) {
			this.data = data;
		}

	}

	@ApiModelProperty(value = "状态码")
	private Integer code;
	@ApiModelProperty(value = "消息")
	private String msg;
	@ApiModelProperty(value = "數據")
	private T data;
}
