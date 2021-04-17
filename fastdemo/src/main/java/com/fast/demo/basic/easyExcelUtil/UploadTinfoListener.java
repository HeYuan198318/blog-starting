package com.fast.demo.basic.easyExcelUtil;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.read.metadata.holder.ReadHolder;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;
import com.fast.demo.basic.util.CustomException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.groups.Default;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 模板的读取类
 * 不歸屬spring管理,每一次都要新創建
 */
@Slf4j
@Data
public class UploadTinfoListener<T> extends AnalysisEventListener<T> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private int BATCH_COUNT = 5000;
    //用來計數(整個excel)
    private int excelSum = 0;
    private List<T> list = new ArrayList<T>();
    List errorList = new ArrayList<T>();
    /**
     * Spring管理的业务逻辑类
     */
    private Object serv;
    public UploadTinfoListener(Object serv) {
        this.serv = serv;
    }

    public UploadTinfoListener() {
    }

    /**
     * 这个每一条数据解析都会来调用
     * @param data
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        Map<String, ExcelCellBo> propertyNameMap = getPropertyNameMap(true,context);
        if (!validate(data,propertyNameMap)) {
            list.clear();
            throw new CustomException("上傳數據有誤:"+errorList);
        }
        list.add(data);
    	excelSum ++;
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
        	//ExcelSaveHandler.saveData(serv,list);
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
       // ExcelSaveHandler.saveData(serv,list);

        log.info("所有数据解析完成,一共" + excelSum + "條數據");
        //throw new CustomException(200, "OK! 共提交數據" + excelSum + "條");
    }

    /**
     * 校驗數據
     * @param data  解析出來的數據
     * @param propertyNameMap
     * @return
     */
    boolean validate(T data, Map<String, ExcelCellBo> propertyNameMap) {
        boolean validateResult = true;
        Set<ConstraintViolation<T>> validateSet = Validation.buildDefaultValidatorFactory().getValidator().validate(data, Default.class);
        if (validateSet != null && !validateSet.isEmpty()) {
            validateResult = false;
            ExcelErrorDTO errorDTO;
            for (ConstraintViolation<T> constraint : validateSet) {
                Path propertyPath = constraint.getPropertyPath();
                String propertyName = propertyPath.toString();
                ExcelCellBo bo = propertyNameMap.get(propertyName);
                errorDTO = new ExcelErrorDTO();
                errorDTO.setHeadName(bo.getHeadName());
                Object invalidValue = constraint.getInvalidValue();
                if (invalidValue != null) {
                    errorDTO.setValue(invalidValue.toString());
                }else {
                    errorDTO.setValue("無錯誤");
                }
                errorDTO.setColumnIndex(bo.getColumnIndex()+1);
                errorDTO.setRowIndex(bo.getRowIndex()+1);
                errorDTO.setErrMsg("第"+errorDTO.getRowIndex()+"行,第"+errorDTO.getColumnIndex()+"列,"+constraint.getMessage());
                errorList.add(errorDTO);
            }
        }
        return validateResult;
    }

    /**
     *
     * @param isSingleHeader  是否為單行表頭
     * @param analysisContext
     * @return
     */
    Map<String, ExcelCellBo> getPropertyNameMap(boolean isSingleHeader, AnalysisContext analysisContext){
        Map<String, ExcelCellBo> propertyNameMap = new HashMap<>(16);
        ReadRowHolder readRowHolder = analysisContext.readRowHolder();
        //当前正在处理数据行值
        Integer rowIndex = readRowHolder.getRowIndex();
        ReadHolder readHolder = analysisContext.currentReadHolder();
        ExcelReadHeadProperty excelReadHeadProperty = readHolder.excelReadHeadProperty();
        Collection<ExcelContentProperty> values;
        if (isSingleHeader){
            Map<Integer, ExcelContentProperty> contentPropertyMap = excelReadHeadProperty.getContentPropertyMap();
            values = contentPropertyMap.values();
        }else {
            //也适用于单行表头
            Map<String, ExcelContentProperty> fieldNameContentPropertyMap = excelReadHeadProperty.getFieldNameContentPropertyMap();
            values = fieldNameContentPropertyMap.values();
        }
        ExcelCellBo bo;
        for (ExcelContentProperty contentProperty : values) {
            bo = new ExcelCellBo();
            bo.setRowIndex(rowIndex);
            bo.setColumnIndex(contentProperty.getHead().getColumnIndex());
            bo.setFieldName(contentProperty.getHead().getFieldName());
            //多行表头
            bo.setHeadName(String.join(",",contentProperty.getHead().getHeadNameList()));
            bo.setField(contentProperty.getField());
            propertyNameMap.put(contentProperty.getHead().getFieldName(),bo);
        }
        return propertyNameMap;
    }


    @Data
    public class ExcelCellBo {
        private Field field;
        private String fieldName;
        private String headName;
        private Integer columnIndex;
        private Integer rowIndex;
    }

    @Data
    public class ExcelErrorDTO {
        private String headName;
        private Integer columnIndex;
        private Integer rowIndex;
        private String value;
        private String errMsg;
    }

}
