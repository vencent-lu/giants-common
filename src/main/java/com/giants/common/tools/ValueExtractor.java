package com.giants.common.tools;

import com.giants.common.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * RelationEntityHandler TODO
 * date time: 2024/9/10 18:19
 * Copyright 2024 github.com/vencent-lu. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.3.0
 */
public abstract class ValueExtractor<V, O> {
    public abstract V extractValue(O obj);

    public List<V> extractFrom(List<O> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<V> valueList = new ArrayList<>(list.size());
        for (O obj : list) {
            V value = extractValue(obj);
            if (value != null) {
                valueList.add(value);
            }
        }
        return valueList;
    }
}
