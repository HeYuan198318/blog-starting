package com.fast.demo.basic.constants;

/**
 * 
 * @ClassName: BaseHttpStatusCode
 * @Description: 系統 請求回顯狀態碼
 * @author:C3006748
 * @date 2021年3月30日 下午3:19:09
 *
 */
public interface BaseHttpStatusConstants {

	/**
	 * 响应请求成功code
	 */
	final static Integer HTTP_RES_CODE_200 = 200;

	/**
	 * 响应请求成功信息
	 */
	final static String HTTP_RES_CODE_200_VALUE = "success";

	/**
	 * 系统错误信息
	 */
	final static Integer HTTP_RES_CODE_500 = 500;

	/**
	 * 系统错误code
	 */
	final static String HTTP_RES_CODE_500_VALUE = "error";

	/**
	 * 参数错误
	 */
	final static Integer HTTP_RES_CODE_400 = 400;

	/**
	 * 无查询结果
	 */
	final static Integer NONE_RES = 404;

	/**
	 * 未認證
	 */
	final static Integer UNAUTHORIZED = 401;

	/**
	 * 無權限
	 */
	final static Integer No_ACCESS = 403;

	/**
	 * 服務器超時無響應
	 * 
	 * @Value {@value}
	 **/
	public static final String TIME_OUT = "504";
}
