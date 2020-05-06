package com.hbhs.common.domain.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString(callSuper = true)
public class TSingleResult<T> extends BaseResult {
    private T data;

}
