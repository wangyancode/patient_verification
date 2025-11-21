package com.dincher.framework.web.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description 用于导入导出的excel
 * @Author WangXiao
 * @Date 2022/7/1
 */
@Data
public class ExcelEntity {
    //文件名称
    private String fileName;
    //工作表名称
    private List<String> sheetNames;
    //文件中是否有目录sheet,默认没有目录sheet
    private Boolean catalogFlag;
    //目录标题,String代表表头的列对应的对象的某个属性，Object代表列的值
    private Map<String,Object> catalogTitles;
    //目录数据，integer代表sheet中行的索引值，object是行的具体数据
    private Map<Integer,Object> catalogData;
    //是否批量导入
    private Boolean multiImportFlag;

    public ExcelEntity() {
        this.catalogFlag =false;
        this.multiImportFlag =true;
    }
    public ExcelEntity(String fileName, List<String> sheetNames,Boolean catalogFlag) {
        this.fileName = fileName;
        this.sheetNames = sheetNames;
        this.catalogFlag = catalogFlag;
    }
}
