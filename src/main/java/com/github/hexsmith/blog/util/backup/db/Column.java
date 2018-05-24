/*
 *Copyright  (C) 2016-2018 The hexsmith Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.github.hexsmith.blog.util.backup.db;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2018/5/18 22:05
 */
public class Column {


    private String catalogName;
    private String schemaName;
    private String tableName;
    private String name;
    private String label;
    private int type;
    private String typeName;
    private String columnClassName;
    private int displaySize;
    private int precision;
    private int scale;

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public int getType() {
        return type;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setColumnClassName(String columnClassName) {
        this.columnClassName = columnClassName;
    }

    public void setDisplaySize(int displaySize) {
        this.displaySize = displaySize;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return "Column [catalogName=" + catalogName + ", schemaName="
                + schemaName + ", tableName=" + tableName + ", name=" + name
                + ", label=" + label + ", type=" + type + ", typeName="
                + typeName + ", columnClassName=" + columnClassName
                + ", displaySize=" + displaySize + ", precision=" + precision
                + ", scale=" + scale + "]";
    }

}
