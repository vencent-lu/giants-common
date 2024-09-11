package com.giants.common.tools.db.relationship;

import java.util.List;

/**
 * ComparableResult TODO
 * date time: 2024/9/9 17:13
 * Copyright 2024 github.com/vencent-lu. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.3.0
 */
class ComparableResult<T extends Comparable<? super T>> {
    private List<T> addedEntityList;
    private List<T> removedEntityList;
    private List<ModifiedEntity<T>> modifiedEntityList;

    protected ComparableResult(List<T> addedEntityList, List<T> removedEntityList, List<ModifiedEntity<T>> modifiedEntityList) {
        this.addedEntityList = addedEntityList;
        this.removedEntityList = removedEntityList;
        this.modifiedEntityList = modifiedEntityList;
    }

    protected List<T> getAddedEntityList() {
        return addedEntityList;
    }

    protected void setAddedEntityList(List<T> addedEntityList) {
        this.addedEntityList = addedEntityList;
    }

    protected List<T> getRemovedEntityList() {
        return removedEntityList;
    }

    protected void setRemovedEntityList(List<T> removedEntityList) {
        this.removedEntityList = removedEntityList;
    }

    protected List<ModifiedEntity<T>> getModifiedEntityList() {
        return modifiedEntityList;
    }

    protected void setModifiedEntityList(List<ModifiedEntity<T>> modifiedEntityList) {
        this.modifiedEntityList = modifiedEntityList;
    }

    protected static class ModifiedEntity<T extends Comparable<? super T>> {
        private T sourceEntity;
        private T targetEntity;

        protected ModifiedEntity(T sourceEntity, T targetEntity) {
            this.sourceEntity = sourceEntity;
            this.targetEntity = targetEntity;
        }

        protected T getSourceEntity() {
            return sourceEntity;
        }

        protected void setSourceEntity(T sourceEntity) {
            this.sourceEntity = sourceEntity;
        }

        protected T getTargetEntity() {
            return targetEntity;
        }

        protected void setTargetEntity(T targetEntity) {
            this.targetEntity = targetEntity;
        }
    }
}
