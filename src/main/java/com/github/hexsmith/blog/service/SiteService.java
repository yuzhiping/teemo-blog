package com.github.hexsmith.blog.service;


import com.github.hexsmith.blog.dto.MetaDto;
import com.github.hexsmith.blog.model.bo.ArchiveBo;
import com.github.hexsmith.blog.model.bo.BackResponseBo;
import com.github.hexsmith.blog.model.bo.StatisticsBo;
import com.github.hexsmith.blog.model.vo.CommentVo;
import com.github.hexsmith.blog.model.vo.ContentVo;

import java.util.List;

/**
 * 站点服务
 * @author hexsmith
 * @since 2018-05-23 23:22
 * @version v1.0
 */
public interface SiteService {


    /**
     * 最新收到的评论
     *
     * @param limit
     * @return
     */
    List<CommentVo> recentComments(int limit);

    /**
     * 最新发表的文章
     *
     * @param limit
     * @return
     */
    List<ContentVo> recentContents(int limit);

    /**
     * 查询一条评论
     * @param coid
     * @return
     */
    CommentVo getComment(Integer coid);

    /**
     * 系统备份
     * @param bk_type
     * @param bk_path
     * @param fmt
     * @return
     */
    BackResponseBo backup(String bk_type, String bk_path, String fmt) throws Exception;


    /**
     * 获取后台统计数据
     *
     * @return
     */
    StatisticsBo getStatistics();

    /**
     * 查询文章归档
     *
     * @return
     */
    List<ArchiveBo> getArchives();

    /**
     * 获取分类/标签列表
     * @return
     */
    List<MetaDto> metas(String type, String orderBy, int limit);

}
