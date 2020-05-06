package com.hbhs.common.domain.model.request;

import com.hbhs.common.domain.model.Request;
import com.hbhs.common.domain.util.ParamAssert;
import lombok.*;

@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
public abstract class BaseRequest implements Request {
    protected String operatorId;
    protected String operatorName;
    protected String source;
    protected String clientType;
    protected String clientIp;
    protected OperationType operationType;

    public void checkClientInfo() {
        ParamAssert.assertNotBlank(operatorId, "Param 'operatorId' is empty.");
//        ParamAssert.assertNotBlank(operatorName, "Param 'operatorName' is empty.");
//        ParamAssert.assertNotBlank(source, "Param 'source' is empty.");
//        ParamAssert.assertNotBlank(clientType, "Param 'clientType' is empty.");
//        ParamAssert.assertNotBlank(clientIp, "Param 'clientIp' is empty.");
        ParamAssert.assertNotNull(operationType, "Param 'operationType' is empty.");
    }
}
