package com.fast.demo.basic.util;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = -3456461491173141776L;

	private Integer errCode;// 錯誤代碼
	private String errMsg;// 錯誤描述
	private Object data;// 数据

	public CustomException() {
		super();
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
		this.errMsg = message;
	}

	public CustomException(String message) {
		super(message);
		this.errMsg = message;
	}

	public CustomException(Throwable cause) {
		super(cause);
	}

	public CustomException(Integer code, String message) {
		super(message);
		this.errMsg = message;
		this.errCode = code;
	}

	public CustomException(Integer code, String message, Object data) {
		super(message);
		this.errMsg = message;
		this.errCode = code;
		this.data = data;
	}

	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "CustomException [errCode=" + errCode + ", errMsg=" + errMsg + ", data=" + data + "]";
	}

}
