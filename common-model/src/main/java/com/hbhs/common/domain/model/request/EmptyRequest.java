package com.hbhs.common.domain.model.request;

public class EmptyRequest extends BaseRequest {

    @Override
    public void checkParam() {
        super.checkClientInfo();
    }
}
