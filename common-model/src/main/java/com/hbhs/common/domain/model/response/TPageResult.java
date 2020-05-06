package com.hbhs.common.domain.model.response;

import com.hbhs.common.domain.model.paging.Pagination;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter@Setter@ToString(callSuper = true)
public class TPageResult<T> extends BaseResult {
    private List<T> data;
    private Pagination pagination;

}
