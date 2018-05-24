package com.github.hexsmith.blog.service;


import com.github.hexsmith.blog.model.vo.OptionVo;

import java.util.List;
import java.util.Map;

/**
 * options的接口
 * @author hexsmith
 * @since 2018-05-23 23:22
 * @version v1.0
 */
public interface OptionService {

    void insertOption(OptionVo optionVo);

    void insertOption(String name, String value);

    List<OptionVo> getOptions();


    /**
     * 保存一组配置
     *
     * @param options
     */
    void saveOptions(Map<String, String> options);

    OptionVo getOptionByName(String name);
}
