package com.github.hexsmith.blog.model.bo;


import com.github.hexsmith.blog.model.vo.CommentVo;

import java.util.List;

/**
 * 返回页面的评论，包含父子评论内容
 * Created by 13 on 2017/2/24.
 */
public class CommentBo extends CommentVo {

    private static final long serialVersionUID = -8915925611344745201L;
    private int levels;
    private List<CommentVo> children;

    public CommentBo(CommentVo comments) {
        setAuthor(comments.getAuthor());
        setMail(comments.getMail());
        setCoid(comments.getCoid());
        setAuthorId(comments.getAuthorId());
        setUrl(comments.getUrl());
        setCreated(comments.getCreated());
        setAgent(comments.getAgent());
        setIp(comments.getIp());
        setContent(comments.getContent());
        setOwnerId(comments.getOwnerId());
        setCid(comments.getCid());
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public List<CommentVo> getChildren() {
        return children;
    }

    public void setChildren(List<CommentVo> children) {
        this.children = children;
    }
}
