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

import java.util.ArrayList;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2018/5/18 22:03
 */
public class TableCollection extends ArrayList<Table> {


    /**
     *
     */
    private static final long serialVersionUID = -5824322959198795936L;

    /**
     * Sort tables according to constraints
     */
    public void sort(){
        for(int i = 0 ; i < size(); ){
            boolean corrupted = false;
            for(int j = i + 1; j < size(); j++){
                if(get(i).isReferenced(get(j))){
                    Table table = get(i);
                    remove(table);
                    add(table);
                    corrupted = true;
                    break;
                }
            }
            if(!corrupted){
                i++;
            }
        }
    }

}
