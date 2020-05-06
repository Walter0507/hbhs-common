package com.hbhs.common.error.handler;

import com.hbhs.common.error.BizException;
import com.hbhs.common.error.IErrorTemplate;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommonErrorHandler implements HandlerExceptionResolver {

    @Getter@Setter
    private String errorCodeKey = "errorCode";
    @Getter@Setter
    private String errorMessageKey = "errorMessage";

    @Override
    public ModelAndView resolveException(javax.servlet.http.HttpServletRequest httpServletRequest,
                                         javax.servlet.http.HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {

        Map<String, Object> exceptionResultMap = new HashMap<>();
        if (e instanceof BizException){
            exceptionResultMap.put(errorCodeKey, ((BizException) e).getErrorCode());
            exceptionResultMap.put(errorMessageKey, ((BizException) e).getErrorMessage());
        }else {
            exceptionResultMap.put(errorCodeKey, IErrorTemplate.SYSTEM_ERROR_CODE);
            exceptionResultMap.put(errorMessageKey, "系统错误");
        }
        return new ModelAndView(new MappingJackson2JsonView(), exceptionResultMap);
    }

}
