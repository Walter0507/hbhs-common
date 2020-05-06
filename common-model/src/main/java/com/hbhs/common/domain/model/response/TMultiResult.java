package com.hbhs.common.domain.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class TMultiResult<T> extends BaseResult {
    private List<T> data;
}
