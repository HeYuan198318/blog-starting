package com.fast.demo.basic.service;


import com.fast.demo.basic.constants.BaseHttpStatusConstants;
import com.fast.demo.basic.vo.ResponseMsg;

/**
 * 
 * @ClassName: BaseApiService
 * @Description: 方便封裝返回消息對象
 * @author:C3006748
 * @date 2021年3月30日 下午3:18:11
 *
 */
public class BaseApiService {

	/**
	 * 方法描述: (返回成功)
	 */
	public ResponseMsg setResultSuccess() {
		return setResult(BaseHttpStatusConstants.HTTP_RES_CODE_200, BaseHttpStatusConstants.HTTP_RES_CODE_200_VALUE, null);
	}

	/**
	 * 方法描述: (返回成功，有参数)
	 */
	public ResponseMsg setResultSuccess(String msg) {
		return setResult(BaseHttpStatusConstants.HTTP_RES_CODE_200, msg, null);
	}

	/**
	 * 方法描述: (返回成功,有参数，有数据)
	 */
	public ResponseMsg setResultSuccessData(Object data) {
		return setResult(BaseHttpStatusConstants.HTTP_RES_CODE_200, BaseHttpStatusConstants.HTTP_RES_CODE_200_VALUE, data);
	}

	/**
	 * 方法描述: (返回失败)
	 */
	public ResponseMsg setResultError() {
		return setResult(BaseHttpStatusConstants.HTTP_RES_CODE_500, BaseHttpStatusConstants.HTTP_RES_CODE_500_VALUE, null);
	}

	/**
	 * 方法描述: (返回失败，有参数)
	 */
	public ResponseMsg setResultError(String msg) {
		return setResult(BaseHttpStatusConstants.HTTP_RES_CODE_500, msg, null);
	}

	/**
	 * 方法描述: (返回失败，無結果)
	 */
	public ResponseMsg setResultNoResourcesError(String msg) {
		return setResult(BaseHttpStatusConstants.NONE_RES, msg, null);
	}

	/**
	 * 方法描述: (未認證)
	 */
	public ResponseMsg setResultUnauthorizedError() {
		return setResult(BaseHttpStatusConstants.UNAUTHORIZED, "訪問令牌無效/身份驗證未能通過", null);
	}

	/**
	 * 方法描述: (無權限)
	 */
	public ResponseMsg setResultNoACCESSError() {
		return setResult(BaseHttpStatusConstants.No_ACCESS, "無權限", null);
	}

	/**
	 * 方法描述: (参数错误)
	 */
	public ResponseMsg setResultParamterError(String msg) {
		return setResult(BaseHttpStatusConstants.HTTP_RES_CODE_400, msg, null);
	}

	/**
	 * 方法描述: (自定义返回 )
	 */
	public ResponseMsg setResult(Integer code, String msg, Object data) {
		ResponseMsg messageInfo = new ResponseMsg(String.valueOf(code), msg, data);
		return messageInfo;
	}

}