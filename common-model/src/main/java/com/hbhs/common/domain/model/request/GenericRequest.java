package com.hbhs.common.domain.model.request;

import com.hbhs.common.domain.model.Request;
import com.hbhs.common.domain.util.ParamAssert;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GenericRequest<T> extends BaseRequest {

    private T param;

    @Override
    public void checkParam() {
        checkClientInfo();
        ParamAssert.assertNotNull(param, "Param is null");
        if (param instanceof Request) {
            ((Request) param).checkParam();
        }
    }
}
