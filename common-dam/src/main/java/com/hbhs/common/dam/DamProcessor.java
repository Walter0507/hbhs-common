package com.hbhs.common.dam;

public interface DamProcessor<RES>  {

    void init();

    <R extends DamRequest> RES process(R request);

    void shutdown();
}
