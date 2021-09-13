package com.giants.common.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.config.FastJsonConfig;

import java.lang.reflect.Type;

/**
 * FastJson 统一配置简化 FastJson 调用
 * date time: 2021/9/8 17:17
 * Copyright 2021 github.com/vencent-lu/giants-common Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.2
 */
public class FastJson {

    private FastJsonConfig fastJsonConfig;

    public String toJSONString(Object object) {
        return JSON.toJSONString(object, this.fastJsonConfig.getSerializeConfig(),
                this.fastJsonConfig.getSerializeFilters(),this.fastJsonConfig.getDateFormat(),
                JSON.DEFAULT_GENERATE_FEATURE,this.fastJsonConfig.getSerializerFeatures());
    }

    public Object parse(String text) {
        return JSON.parse(text, this.fastJsonConfig.getParserConfig(), this.fastJsonConfig.getFeatures());
    }

    public JSONObject parseObject(String text) {
        return JSON.parseObject(text, this.fastJsonConfig.getFeatures());
    }

    public <T> T parseObject(String text, Type clazz) {
        return JSON.parseObject(text, clazz, this.fastJsonConfig.getParserConfig(),
                this.fastJsonConfig.getParseProcess(), JSON.DEFAULT_PARSER_FEATURE, this.fastJsonConfig.getFeatures());
    }

    public void setFastJsonConfig(FastJsonConfig fastJsonConfig) {
        this.fastJsonConfig = fastJsonConfig;
    }
}
