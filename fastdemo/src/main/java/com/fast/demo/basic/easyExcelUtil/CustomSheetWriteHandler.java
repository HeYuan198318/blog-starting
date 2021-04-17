package com.fast.demo.basic.easyExcelUtil;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

/**
 * Sheet攔截器
 * @author C3901094
 * @date 2020年6月2日 上午10:23:44
 */
public class CustomSheetWriteHandler implements SheetWriteHandler {

	@Override
	public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
		// TODO Auto-generated method stub

	}

}
