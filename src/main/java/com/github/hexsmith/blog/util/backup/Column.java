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
package com.github.hexsmith.blog.util.backup;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2018/5/18 21:59
 */
public class Column {

    private String name;
    private String typeName;
    private int dataType;

    public String getName() {
        return name;
    }

    public int getDataType() {
        return dataType;
    }

    public Column(String name, String typeName, int dataType) {
        super();
        this.name = name;
        this.typeName = typeName;
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "Column [name=" + name + ", typeName=" + typeName
                + ", dataType=" + dataType + "]";
    }

}
