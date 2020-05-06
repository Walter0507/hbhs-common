package com.hbhs.common.domain.model.request;

import com.hbhs.common.domain.model.Request;
import com.hbhs.common.domain.model.paging.Pagination;
import com.hbhs.common.domain.model.paging.SortingCondition;
import com.hbhs.common.domain.util.ParamAssert;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter@Setter@ToString(callSuper = true)
@NoArgsConstructor
public class QueryRequest<T> extends BaseRequest {
    private T param;
    private Pagination pagination;
    private List<SortingCondition> sorts = new ArrayList<>();

    @Override
    public void checkParam() {
        checkClientInfo();
        ParamAssert.assertNotNull(param, "Param is null");
        if (param instanceof Request){
            ((Request) param).checkParam();
        }

        ParamAssert.assertNotNull(pagination, "Param 'pagination' is null");
        pagination.checkParam();

    }
}
