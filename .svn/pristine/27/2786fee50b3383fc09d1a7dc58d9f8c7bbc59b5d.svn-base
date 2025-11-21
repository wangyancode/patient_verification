package com.dincher.project.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dincher.project.system.domain.entity.Config;

import java.util.List;

/**
 * 参数配置 服务层
 * 
 *
 */
public interface ConfigService extends IService<Config>
{
    /**
     * 查询参数配置信息
     * 
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    public Config selectConfigById(Integer configId);

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configKey 参数键名
     * @return 参数键值
     */
    public String selectConfigByKey(String configKey);

    /**
     * 查询参数配置列表
     * 
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    public List<Config> selectConfigList(Config config);

    /**
     * 新增参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    public int insertConfig(Config config);

    /**
     * 修改参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    public int updateConfig(Config config);

    /**
     * 批量删除参数信息
     * 
     * @param configIds 需要删除的参数ID
     * @return 结果
     */
    public int deleteConfigByIds(Integer[] configIds);

    /**
     * 清空缓存数据
     */
    public void clearCache();

    /**
     * 校验参数键名是否唯一
     * 
     * @param config 参数信息
     * @return 结果
     */
    public String checkConfigKeyUnique(Config config);

    /**
     * 重置参数缓存数据
     */
    public void resetConfigCache();

    /**
     * 加载参数缓存数据
     */
    public void loadingConfigCache();
}
