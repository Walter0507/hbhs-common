package com.hbhs.common.domain.convertor;

import com.hbhs.common.domain.model.BaseEntity;
import com.hbhs.common.domain.model.request.param.BaseParam;
import com.hbhs.common.domain.model.response.info.BaseInfo;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCommonDataConvertor<C extends BaseParam, U extends BaseParam, I extends BaseInfo, E extends BaseEntity> {

    public abstract E createParam2Entity(C param);

    public abstract E updateParam2Entity(U param);

    public abstract I entity2Info(E entity);

    public List<I> entityList2Info(List<E> entityList){
        List<I> list = new ArrayList<>();
        if (entityList!=null&&entityList.size()>0){
            for (E entity : entityList) {
                list.add(entity2Info(entity));
            }
        }
        return list;
    }
}
