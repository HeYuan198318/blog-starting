package com.fast.demo.basic.easyExcelUtil;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * @Author:C3006248
 * @Description: 單元格合併
 * @Date:Created in 下午 03:24 2021/1/19
 */
@Data
public class ExcelFillCellMergeStrategy implements CellWriteHandler {
    /**
     * 合并字段的下标
     */
    private int[] mergeColumnIndex;
    /**
     * 合并几行
     */
    private int mergeRowIndex;

    private String mergeType;

    public ExcelFillCellMergeStrategy() {
    }

    public ExcelFillCellMergeStrategy(int mergeRowIndex, int[] mergeColumnIndex,String mergeType) {
        this.mergeRowIndex = mergeRowIndex;
        this.mergeColumnIndex = mergeColumnIndex;
        this.mergeType=mergeType;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {
    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {
    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {
        //当前行
        int curRowIndex = cell.getRowIndex();

        //当前列
        int curColIndex = cell.getColumnIndex();
        if (curRowIndex > mergeRowIndex) {
            for (int i = 0; i < mergeColumnIndex.length; i++) {
                if (curColIndex == mergeColumnIndex[i]) {
                    if (mergeType.equals("Batch")) {
                        mergeWithPrevRowTwo(writeSheetHolder, cell, curRowIndex, curColIndex);
                    }else {
                        mergeWithPrevRow(writeSheetHolder, cell, curRowIndex, curColIndex);
                    }
                    break;
                }
            }
        }
    }
    /**
     * 当前单元格向上合并
     *
     * @param writeSheetHolder
     * @param cell             当前单元格
     * @param curRowIndex      当前行
     * @param curColIndex      当前列
     */
    private void mergeWithPrevRow(WriteSheetHolder writeSheetHolder, Cell cell, int curRowIndex, int curColIndex) {

        //获取当前行的第一列的数据和上一行的第一列数据，通过第一行数据是否相同进行合并
//        Cell preCell_now = cell.getSheet().getRow(curRowIndex ).getCell(curColIndex);
//        Object curData = preCell_now.getCellTypeEnum() == CellType.STRING ? preCell_now.getStringCellValue() : preCell_now.getNumericCellValue();
//        Cell preCell = cell.getSheet().getRow(curRowIndex - 1).getCell(curColIndex - 1);
//        Object preData = preCell.getCellTypeEnum() == CellType.STRING ? preCell.getStringCellValue() : preCell.getNumericCellValue();
        //获取当前行的当前列的数据和上一行的当前列列数据，通过上一行数据是否相同进行合并
        Object curData = cell.getCellTypeEnum() == CellType.STRING ? cell.getStringCellValue() : cell.getNumericCellValue();
        Cell preCell = cell.getSheet().getRow(curRowIndex - 1).getCell(curColIndex);
        Object preData = preCell.getCellTypeEnum() == CellType.STRING ? preCell.getStringCellValue() : preCell.getNumericCellValue();
        Boolean dataBool=curData.equals(preData);
        // 比较当前行的第一列的单元格与上一行是否相同，相同合并当前单元格与上一行
        Boolean bool=false;
        if (cell.getRow().getCell(8)!=null) {
            bool = cell.getRow().getCell(8).getStringCellValue().equals("TOTAL:");

        }
        if (dataBool&&!bool) {
           merge(writeSheetHolder,curRowIndex,curColIndex);
        }
    }

    private void merge(WriteSheetHolder writeSheetHolder, int curRowIndex, int curColIndex){
        Sheet sheet = writeSheetHolder.getSheet();
        List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
        boolean isMerged = false;
        for (int i = 0; i < mergeRegions.size() && !isMerged; i++) {
            CellRangeAddress cellRangeAddr = mergeRegions.get(i);
            // 若上一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
            if (cellRangeAddr.isInRange(curRowIndex - 1, curColIndex)) {
                sheet.removeMergedRegion(i);
                cellRangeAddr.setLastRow(curRowIndex);
                sheet.addMergedRegion(cellRangeAddr);
                isMerged = true;
            }
        }
        // 若上一个单元格未被合并，则新增合并单元
        if (!isMerged) {
            CellRangeAddress cellRangeAddress = new CellRangeAddress(curRowIndex - 1, curRowIndex, curColIndex, curColIndex);
            sheet.addMergedRegion(cellRangeAddress);
        }
    }
    private void mergeWithPrevRowTwo(WriteSheetHolder writeSheetHolder, Cell cell, int curRowIndex, int curColIndex) {

        //获取当前行的当前列的数据和上一行的当前列列数据，通过上一行数据是否相同进行合并
        Object curData = cell.getCellTypeEnum() == CellType.STRING ? cell.getStringCellValue() : cell.getNumericCellValue();
        Cell preCell = cell.getSheet().getRow(curRowIndex - 1).getCell(curColIndex);
        Object preData = preCell.getCellTypeEnum() == CellType.STRING ? preCell.getStringCellValue() : preCell.getNumericCellValue();
        Boolean dataBool=curData.equals(preData);
        //此处需要注意：因为我是按照總裝箱號确定是否需要合并的，所以获取每一行第二列数据和上一行第一列数据进行比较，如果相等合并
        Boolean bool=false;
        if (cell.getRow().getCell(3)!=null) {
             bool = cell.getRow().getCell(3).getStringCellValue()
                    .equals(cell.getSheet().getRow(curRowIndex - 1).getCell(3).getStringCellValue());
        }

        // 比较当前行的第一列的单元格与上一行是否相同，相同合并当前单元格与上一行
        if (dataBool && bool) {
            merge(writeSheetHolder,curRowIndex,curColIndex);
        }

    }
}
