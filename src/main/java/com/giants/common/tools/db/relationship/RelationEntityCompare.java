package com.giants.common.tools.db.relationship;

import com.giants.common.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * RelationEntityCompare 关系实体比较器
 * date time: 2024/9/10 17:51
 * Copyright 2024 github.com/vencent-lu. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.3.0
 */
public class RelationEntityCompare {

    public static <T extends Comparable<? super T>> ComparableResult<T> compare(List<T> sourceEntityList,
                                                                      List<T> targetEntityList) {
        if (CollectionUtils.isEmpty(sourceEntityList)) {
            return new ComparableResult<>(targetEntityList, Collections.emptyList(), Collections.emptyList());
        }
        if (CollectionUtils.isEmpty(targetEntityList)) {
            return new ComparableResult<>(Collections.emptyList(), sourceEntityList, Collections.emptyList());
        }

        Collections.sort(sourceEntityList);
        Collections.sort(targetEntityList);

        List<T> addedEntityList = new ArrayList<>();
        List<T> removedEntityList = new ArrayList<>();
        List<ComparableResult.ModifiedEntity<T>> modifiedEntityList = new ArrayList<>();

        int i = 0, j = 0;
        int sSize = sourceEntityList.size(), tSize = targetEntityList.size();
        while (i < sSize && j < tSize) {
            T one = sourceEntityList.get(i);
            T another = targetEntityList.get(j);

            int compare = one.compareTo(another);
            if (compare == 0) {
                modifiedEntityList.add(new ComparableResult.ModifiedEntity<>(one, another));
                //处理IdList中有重复项
                while (++i < sSize && one.equals(sourceEntityList.get(i))) ;
                while (++j < tSize && another.equals(targetEntityList.get(j))) ;
            } else if (compare < 0) {
                //已删除
                removedEntityList.add(one);
                i++;
            } else {
                //新增
                addedEntityList.add(another);
                j++;
            }
        }
        for (; i < sourceEntityList.size(); i++) {
            removedEntityList.add(sourceEntityList.get(i));
        }
        for (; j < targetEntityList.size(); j++) {
            addedEntityList.add(targetEntityList.get(j));
        }
        return new ComparableResult<>(addedEntityList, removedEntityList, modifiedEntityList);
    }

}
