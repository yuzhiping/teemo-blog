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
 * @since 2018/5/18 22:01
 */
public class FK {

    private String column;
    private Table referenceTable;
    private String referencePK;

    public Table getReferenceTable() {
        return referenceTable;
    }

    public FK(String column, Table referenceTable, String referencePK) {
        this.column = column;
        this.referenceTable = referenceTable;
        this.referencePK = referencePK;
    }

    @Override
    public String toString() {
        return "FK [column=" + column + ", referenceTable=" + referenceTable
                + ", referencePK=" + referencePK + "]";
    }

}
