package com.hbhs.common.domain.model.request;

import com.hbhs.common.domain.util.ParamAssert;
import lombok.*;

@Getter@Setter@ToString(callSuper = true)@Builder
@NoArgsConstructor@AllArgsConstructor
public class IdRequest extends BaseRequest {
    private String id;

    @Override
    public void checkParam() {
        checkClientInfo();
        ParamAssert.assertNotBlank(id, "Param 'id' is null");
    }
}
