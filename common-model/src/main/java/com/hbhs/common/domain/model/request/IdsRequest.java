package com.hbhs.common.domain.model.request;

import com.hbhs.common.domain.util.ParamAssert;

import java.util.List;

public class IdsRequest extends BaseRequest {
    private List<String> ids;

    @Override
    public void checkParam() {
        checkClientInfo();
        ParamAssert.assertNotEmpty(ids, "Param 'ids' is empty");
    }
}
