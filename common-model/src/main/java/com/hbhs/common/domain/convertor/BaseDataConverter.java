package com.hbhs.common.domain.convertor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class BaseDataConverter<S, T> {
    public BaseDataConverter() {
    }

    public abstract T convert(S object);

    public List<T> batchConvert(List<S> objects) {
        if (objects != null && !objects.isEmpty()) {
            List<T> targets = new ArrayList(objects.size());
            Iterator<S> var3 = objects.iterator();

            while(var3.hasNext()) {
                S object = var3.next();
                T t = this.convert(object);
                if (t != null) {
                    targets.add(t);
                }
            }

            return targets;
        } else {
            return Collections.emptyList();
        }
    }
}