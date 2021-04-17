package com.fast.demo.basic.easyExcelUtil;

import com.fast.demo.basic.util.CustomException;

import java.util.List;

public class ExcelSaveHandler {

	/**
	 * Excel數據存储数据库
	 */
	public static void saveData(Object serv, List list) {
		Integer num = 0;
		if (list.size() < 1) {
			throw new CustomException("未在Excel中找到相應數據.");
		}
//		if (serv instanceof BasicinfoService) {// Service匹配
//			if (list.get(0) instanceof AuditIssueDto) {
//				num = ((AuditIssueService) serv).upLoadAuditIssues(list);
//			}
//		}

			if (num < list.size()) {
				if (num == -1) {
					throw new CustomException(500,
							"上傳數據格式有誤");
				}
				throw new CustomException(500,"導入數據" + num + "條,丟失" + (list.size() - num) + "條,數據已存在或數據錯誤.");
			}
	}
}

