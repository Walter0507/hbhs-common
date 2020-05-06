package com.hbhs.common.domain.convertor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class BaseDataConverterWithParent<S, T, P> extends BaseDataConverter<S, T> {
    public BaseDataConverterWithParent() {
    }

    @Override
    public T convert(S object) {
        return this.convert(object, (P)null);
    }

    public abstract T convert(S object, P parent);

    public List<T> batchConvert(List<S> objects, P parent) {
        if (objects != null && !objects.isEmpty()) {
            List<T> targets = new ArrayList(objects.size());
            Iterator<S> var4 = objects.iterator();

            while(var4.hasNext()) {
                S object = var4.next();
                T t = this.convert(object, parent);
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