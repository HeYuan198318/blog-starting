package com.fast.demo.basic.controller;

import com.fast.demo.basic.vo.ResponseMsg;
import org.springframework.http.HttpStatus;

/**
 * @ClassNameBaseApiService
 * @Description
 * @Author C3005890
 * @Date 2019/8/27 下午 03:45
 * @Version V1.0
 **/
public class BaseApiController {

	/**
	 * 方法描述: (返回成功)
	 */
	public <T> ResponseMsg<T> setResultSuccess() {
		return setResult(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
	}

	/**
	 * 方法描述: (返回成功，有参数)
	 */
	public <T> ResponseMsg<T> setResultSuccess(String msg) {
		return setResult(HttpStatus.OK.value(), msg, null);
	}

	/**
	 * 方法描述: (返回成功,有参数，有数据)
	 */
	public <T> ResponseMsg<T> setResultSuccessData(T data) {
		return setResult(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
	}

	/**
	 * 方法描述: (返回成功,有参数，有数据)
	 */
	public <T> ResponseMsg<T> setResultSuccessData(String msg, T data) {
		return setResult(HttpStatus.OK.value(), msg, data);
	}

	/**
	 * 服务器异常 方法描述: (返回失败)
	 */
	public <T> ResponseMsg<T> setResultError() {
		return setResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
				null);
	}

	/**
	 * 服务器异常 方法描述: (返回失败，有参数)
	 */
	public <T> ResponseMsg<T> setResultError(String msg) {
		return setResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null);
	}

	/**
	 * 400 请求参数异常 setReqParamsError 方法描述: (参数错误)
	 */
	public <T> ResponseMsg<T> setReqParamsError(String msg) {
		return setResult(HttpStatus.BAD_REQUEST.value(), msg, null);
	}

	/**
	 * 401 方法描述: (未授權)
	 */
	public ResponseMsg<Object> setResultUnauthorizedError() {
		return setResult(HttpStatus.UNAUTHORIZED.value(), "無/無效 的 Token", null);
	}

	/**
	 * 401 方法描述: (未授權)
	 */
	public <T> ResponseMsg<T> setResultUnauthorizedError(String msg) {
		return setResult(HttpStatus.UNAUTHORIZED.value(), msg, null);
	}

	/**
	 * 403 方法描述: (无权限操作)
	 */
	public <T> ResponseMsg<T> setReqForbiddenError(String msg) {
		return setResult(HttpStatus.FORBIDDEN.value(), msg, null);
	}

	/**
	 * 403 方法描述: (无权限操作)
	 */
	public <T> ResponseMsg<T> setReqForbiddenError(String msg, T data) {
		return setResult(HttpStatus.FORBIDDEN.value(), msg, data);
	}

	/**
	 * 方法描述: (自定义返回 )
	 */
	public <T> ResponseMsg<T> setResult(Integer code, String msg, T data) {
		return new ResponseMsg<>(code, msg, data);
	}
}