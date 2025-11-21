package com.dincher.project.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dincher.project.system.domain.entity.DictData;

import java.util.List;

/**
 * 字典 业务层
 * 
 *
 */
public interface DictDataService extends IService<DictData>
{
    /**
     * 根据条件分页查询字典数据
     * 
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    public List<DictData> selectDictDataList(DictData dictData);

    /**
     * 根据字典类型和字典键值查询字典数据信息
     * 
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String selectDictLabel(String dictType, String dictValue);

    /**
     * 根据字典数据ID查询信息
     * 
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    public DictData selectDictDataById(Integer dictCode);

    /**
     * 批量删除字典数据信息
     * 
     * @param dictCodes 需要删除的字典数据ID
     * @return 结果
     */
    public int deleteDictDataByIds(Integer[] dictCodes);

    /**
     * 新增保存字典数据信息
     * 
     * @param dictData 字典数据信息
     * @return 结果
     */
    public int insertDictData(DictData dictData);

    public int insertDictDataNoUser(DictData dictData);

    /**
     * 修改保存字典数据信息
     * 
     * @param dictData 字典数据信息
     * @return 结果
     */
    public int updateDictData(DictData dictData);
}
