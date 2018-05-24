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
package com.github.hexsmith.blog.exception.handler;

import com.github.hexsmith.blog.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2018/5/23 21:08
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BizException.class)
    public String bizException(Exception e) {
        logger.error("find exception: e = {} ", e.getMessage());
        e.printStackTrace();
        return "common/error_500";
    }

    @ExceptionHandler(value = Exception.class)
    public String exception(Exception e){
        logger.error("find exception:e={}",e.getMessage());
        e.printStackTrace();
        return "common/error_404";
    }

}
