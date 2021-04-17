package com.fast.demo.basic.easyExcelUtil;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * Cell攔截器
 * @author C3901094
 * @date 2020年6月2日 上午10:23:29
 */
public class CustomCellWriteHandler implements CellWriteHandler {

	@Override
	public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
                                 Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
		

	}

	@Override
	public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,
                                Head head, Integer relativeRowIndex, Boolean isHead) {
		// TODO Auto-generated method stub

	}

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
	public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                 List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

//      log.info("第{}行每{}列！" , cell.getRowIndex(), cell.getColumnIndex());
      Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
      CellStyle cellStyle = CellStyleUtil.cellStyle(workbook);
      //根据校验结果设置单元格文字颜色
      /*if((cell.getRowIndex()-6) == cell.getColumnIndex()){
          //设置单元格背景色
          cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
          System.out.println("攔截器測試CustomCellWriteHandler.class");

      }*/
        // 只处理第一行
        if( 0 == cell.getRowIndex()){
            // 设置列宽
            Sheet sheet = writeSheetHolder.getSheet();
            sheet.setColumnWidth(cell.getColumnIndex(), 14 * 256);
            // 设置行高
            writeSheetHolder.getSheet().getRow(0).setHeight((short)(1.8*256));
            // 获取workbook
             workbook = writeSheetHolder.getSheet().getWorkbook();
            // 获取样式实例
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            // 获取字体实例
            WriteFont headWriteFont = new WriteFont();
            // 设置字体样式
            headWriteFont.setFontName("宋体");
            // 设置字体大小
            headWriteFont.setFontHeightInPoints((short)14);
            // 边框
            headWriteFont.setBold(true);
            headWriteCellStyle.setWriteFont(headWriteFont);
            // 设置背景颜色为灰色
            headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

            // 获取样式实例
             cellStyle = StyleUtil.buildHeadCellStyle(workbook, headWriteCellStyle);
            // 单元格设置样式
            cell.setCellStyle(cellStyle);
        }else{
            //自動換行
            cellStyle.setWrapText(true);
            cell.setCellStyle(cellStyle);
        }
	}

}
