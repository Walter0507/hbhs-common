package com.hbhs.common.domain.model.paging;

import com.hbhs.common.domain.model.request.param.BaseParam;
import com.hbhs.common.domain.util.ParamAssert;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
public class Pagination extends BaseParam {
    public static final int DEFAULT_PAGE_SIZE = 20;

    private long count;
    private int pageNum;
    private int pageSize;
    private List<SortingCondition> sortingConditions = new ArrayList<>();

    public Pagination(int pageNum, int pageSize) {
        this.pageNum = Math.max(1, pageNum);
        this.pageSize = pageSize <= DEFAULT_PAGE_SIZE && pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
    }

    public Pagination(int pageNum, int pageSize, int count) {
        this(pageNum,pageSize);
        this.count = (long)count;
    }

    @Override
    public void checkParam() {
        ParamAssert.expectTrue(pageNum>0,"Param 'pagination.pageNum' not valid");
        ParamAssert.expectTrue(pageSize>0, "Param 'pagination.pageSize' not valid");
    }
}
