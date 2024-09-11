package com.giants.common.tools.db.relationship;

/**
 * RelationEntityHandler TODO
 * date time: 2024/9/10 18:19
 * Copyright 2024 github.com/vencent-lu. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.3.0
 */
public abstract class RelationEntityHandler<T extends Comparable<? super T>> {

    /**
     * 删除实体
     * @param entity 实体对象
     */
    protected abstract void remove(T entity);

    /**
     * 新增实体
     * @param entity 实体对象
     */
    protected abstract void add(T entity);

    /**
     * 修改实体：对比原对象与目标对象有无变化，如果有变化则修改，并返回true
     * @param sourceEntity 原实体对象
     * @param targetEntity 目标实体对象(修改后)
     * @return 是否修改成功，默认不修改
     */
    protected boolean modify(T sourceEntity, T targetEntity) {
        return false;
    }

    public int handle(ComparableResult<T> comparableResult) {
        int rows = 0;

        for (T entity : comparableResult.getRemovedEntityList()) {
            this.remove(entity);
            rows++;
        }

        for (T entity : comparableResult.getAddedEntityList()) {
            this.add(entity);
            rows++;
        }

        for (ComparableResult.ModifiedEntity<T> modifiedEntity : comparableResult.getModifiedEntityList()) {
            if (modify(modifiedEntity.getSourceEntity(), modifiedEntity.getTargetEntity())) {
                rows++;
            }
        }

        return rows;
    }

}
