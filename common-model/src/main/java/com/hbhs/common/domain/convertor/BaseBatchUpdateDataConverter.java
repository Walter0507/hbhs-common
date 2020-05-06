package com.hbhs.common.domain.convertor;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseBatchUpdateDataConverter<T, K, M, P> {
    public BaseBatchUpdateDataConverter() {
    }

    public List<T> convert(List<K> createList, List<M> updateList, List<String> deleteList, P parent) {
        List<T> sessionEvents = null;
        if (createList != null && createList.size() > 0) {
            sessionEvents = new ArrayList();
            sessionEvents.addAll(this.getCreateParamConverter().batchConvert(createList, parent));
        }

        if (updateList != null && updateList.size() > 0) {
            if (sessionEvents == null) {
                sessionEvents = new ArrayList();
            }

            sessionEvents.addAll(this.getUpdateParamConverter().batchConvert(updateList, parent));
        }

        if (deleteList != null && deleteList.size() > 0) {
            if (sessionEvents == null) {
                sessionEvents = new ArrayList();
            }

            sessionEvents.addAll(this.getDeleteParamConverter().batchConvert(deleteList, parent));
        }

        return sessionEvents;
    }

    protected abstract BaseDataConverterWithParent<K, T, P> getCreateParamConverter();

    protected abstract BaseDataConverterWithParent<M, T, P> getUpdateParamConverter();

    protected abstract BaseDataConverterWithParent<String, T, P> getDeleteParamConverter();
}