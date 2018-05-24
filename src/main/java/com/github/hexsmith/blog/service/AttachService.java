package com.github.hexsmith.blog.service;

import com.github.hexsmith.blog.model.vo.AttachVo;
import com.github.pagehelper.PageInfo;

/**
 * @author hexsmith
 * @since 2018-05-23 23:22
 * @version v1.0
 */
public interface AttachService {
    /**
     * 分页查询附件
     * @param page
     * @param limit
     * @return
     */
    PageInfo<AttachVo> getAttachs(Integer page, Integer limit);

    /**
     * 保存附件
     *
     * @param fname
     * @param fkey
     * @param ftype
     * @param author
     */
    void save(String fname, String fkey, String ftype, Integer author);

    /**
     * 根据附件id查询附件
     * @param id
     * @return
     */
    AttachVo selectById(Integer id);

    /**
     * 删除附件
     * @param id
     */
    void deleteById(Integer id);
}
