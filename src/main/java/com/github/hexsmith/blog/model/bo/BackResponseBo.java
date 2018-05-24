package com.github.hexsmith.blog.model.bo;

import java.io.Serializable;

/**
 * Created by 13 on 2017/2/25.
 */
public class BackResponseBo implements Serializable {

    private static final long serialVersionUID = 8400133604861365679L;
    private String attachPath;
    private String themePath;
    private String sqlPath;

    public String getAttachPath() {
        return attachPath;
    }

    public void setAttachPath(String attachPath) {
        this.attachPath = attachPath;
    }

    public String getThemePath() {
        return themePath;
    }

    public void setThemePath(String themePath) {
        this.themePath = themePath;
    }

    public String getSqlPath() {
        return sqlPath;
    }

    public void setSqlPath(String sqlPath) {
        this.sqlPath = sqlPath;
    }
}
