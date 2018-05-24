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

import java.util.Date;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2018/5/18 22:07
 */
public class Row {

    private Object[] data;
    private DataTable dataTable;

    public Row(DataTable dataTable, Object[] data) {
        this.dataTable = dataTable;
        this.data = data;
    }

    public Object get(String columnLabel) {
        return get(dataTable.getColumns().indexByLabel(columnLabel));
    }

    public Object get(int index) {
        return data[index];
    }

    public String getString(int index) {
        return String.valueOf(get(index));
    }

    public String getString(String label) {
        return String.valueOf(get(label));
    }

    public Integer getInteger(String label) {
        return (Integer) (get(label));
    }

    public Date getDate(int index) {
        return (Date) get(index);
    }

    @Override
    public String toString() {
        if (data == null || data.length == 0) {
            return "{}";
        }
        String s = "{" + String.valueOf(data[0]);
        for (int i = 1; i < data.length; i++) {
            s += ", " + String.valueOf(data[i]);
        }
        s += "}";
        return s;
    }

}
