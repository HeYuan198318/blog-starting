package com.fast.demo.basic.easyExcelUtil;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author C3006248
 * @create 2020/10/30  9:49
 */
@Slf4j
public class CustomRowWriteHandler implements RowWriteHandler {

    /**
     * 一定将样式设置成全局变量
     * 首行只需要创建一次样式就可以 不然每行都创建一次 数据量大的话会保错
     * 异常信息：The maximum number of Cell Styles was exceeded. You can define up to 64000 style in a .xlsx Workbook
     */
    private CellStyle firstCellStyle;

    /**
     * 列号
     */
    private int count = 0;
    @Override
    public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer integer, Boolean aBoolean) {
        if (log.isDebugEnabled()) {
            log.debug("第{}行创建完毕！", row.getRowNum());
        }
        Cell cell = row.createCell(0);
        if (firstCellStyle == null) {
            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
            firstCellStyle = CellStyleUtil.cellStyle(workbook);
            log.info("设置首列样式成功");
        }
        cell.setCellStyle(firstCellStyle);
        //设置列宽  0列   10个字符宽度
        writeSheetHolder.getSheet().setColumnWidth(0, 10 * 256);
        if (row.getRowNum() == 0) {
            cell.setCellValue("序号");
            return;
        }
        cell.setCellValue(++count);

    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer integer, Boolean aBoolean) {

    }
}
