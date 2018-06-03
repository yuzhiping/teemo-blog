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
package com.github.hexsmith.blog.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2018/5/30 20:44
 */
@Component
public class GeetestConfig {
    @Value("${geetest.id}")
    private String geetest_id;

    @Value("${geetest.key}")
    private String geetest_key;

    @Value("${geetest.newfailback}")
    private boolean newfailback;

    public String getGeetest_id() {
        return geetest_id;
    }

    public String getGeetest_key() {
        return geetest_key;
    }

    public boolean isnewfailback() {
        return newfailback;
    }

}