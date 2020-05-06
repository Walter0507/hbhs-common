package com.hbhs.common.elastic.search.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("pub.elastic.search")
@Data
public class ElasticSearchConfigProperties {

    private List<String> hostport;
    private int connectTimeout;
    private int socketTimeout;
    private boolean useSsl;
    private boolean basicAuth;
    private String loginName;
    private String loginPassword;

}
