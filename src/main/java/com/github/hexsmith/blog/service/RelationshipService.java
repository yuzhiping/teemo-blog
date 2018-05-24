package com.github.hexsmith.blog.service;


import com.github.hexsmith.blog.model.vo.RelationshipVoKey;

import java.util.List;

/**
 * @author hexsmith
 * @since 2018-05-23 23:22
 * @version v1.0
 */
public interface RelationshipService {
    /**
     * 按住键删除
     * @param cid
     * @param mid
     */
    void deleteById(Integer cid, Integer mid);

    /**
     * 按主键统计条数
     * @param cid
     * @param mid
     * @return 条数
     */
    Long countById(Integer cid, Integer mid);


    /**
     * 保存對象
     * @param relationshipVoKey
     */
    void insertVo(RelationshipVoKey relationshipVoKey);

    /**
     * 根据id搜索
     * @param cid
     * @param mid
     * @return
     */
    List<RelationshipVoKey> getRelationshipById(Integer cid, Integer mid);
}
