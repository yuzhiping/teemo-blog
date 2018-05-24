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

import java.util.ArrayList;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2018/5/18 22:05
 */
public class ColumnCollection extends ArrayList<Column> {

    private static final long serialVersionUID = -3399188477563370223L;

    public int indexByLabel(String label){
        for(int i = 0; i < this.size(); i++){
            if(get(i).getLabel().equals(label)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        if(size() == 0){
            return "Columns is empty";
        }
        String s = "Columns : {" + String.valueOf(get(0).getLabel());
        for(int i = 1; i < size(); i++){
            s += ", " + String.valueOf(get(i).getLabel());
        }
        s += "}";
        return s;
    }

}
