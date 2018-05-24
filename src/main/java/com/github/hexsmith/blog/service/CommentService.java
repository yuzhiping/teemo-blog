package com.github.hexsmith.blog.service;

import com.github.hexsmith.blog.model.bo.CommentBo;
import com.github.hexsmith.blog.model.vo.CommentVo;
import com.github.hexsmith.blog.model.vo.CommentVoExample;
import com.github.pagehelper.PageInfo;

/**
 * @author hexsmith
 * @since 2018-05-23 23:22
 * @version v1.0
 */
public interface CommentService {

    /**
     * 保存对象
     * @param commentVo
     */
    String insertComment(CommentVo commentVo);

    /**
     * 获取文章下的评论
     * @param cid
     * @param page
     * @param limit
     * @return CommentBo
     */
    PageInfo<CommentBo> getComments(Integer cid, int page, int limit);

    /**
     * 获取文章下的评论
     * @param commentVoExample
     * @param page
     * @param limit
     * @return CommentVo
     */
    PageInfo<CommentVo> getCommentsWithPage(CommentVoExample commentVoExample, int page, int limit);


    /**
     * 根据主键查询评论
     * @param coid
     * @return
     */
    CommentVo getCommentById(Integer coid);


    /**
     * 删除评论，暂时没用
     * @param coid
     * @param cid
     * @throws Exception
     */
    void delete(Integer coid, Integer cid);

    /**
     * 更新评论状态
     * @param comments
     */
    void update(CommentVo comments);

}
