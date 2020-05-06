package com.hbhs.common.dam;

public interface EventHandler<REQ, RES> {

    int expireMillis();

    RES handler(REQ request);
}
