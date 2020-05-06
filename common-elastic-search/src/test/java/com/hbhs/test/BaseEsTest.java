package com.hbhs.test;

import com.hbhs.common.elastic.search.config.ElasticSearchConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// SpringJUnit支持，由此引入Spring-Test框架支持！
@RunWith(SpringJUnit4ClassRunner.class)
//@EnableReactiveElasticsearchRepositories(basePackages = {"com.example.mtc.order.repository.es"})
@EnableElasticsearchRepositories(basePackages = {"com.hbhs.test.repository.es"})
@Import({ElasticSearchConfig.class})
@Slf4j
public class BaseEsTest {

    static {
        try {
            System.setProperty("pub.elastic.search.hostport[0]","127.0.0.1:9200");
            System.setProperty("pub.elastic.search.connect-timeout","5000");
            System.setProperty("pub.elastic.search.socket-timeout","5000");
//            System.setProperty("pub.elastic.search.use-ssl","false");
//            System.setProperty("pub.elastic.search.basic-auth","false");
        }catch (Exception e){
            log.error("Failed to load properties file", e);
        }
    }
}
