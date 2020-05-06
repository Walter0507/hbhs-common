package com.hbhs.common.elastic.search.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.DefaultReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.EntityMapper;

@EnableConfigurationProperties(ElasticSearchConfigProperties.class)
public class ReactiveElasticSearchConfig extends AbstractReactiveElasticsearchConfiguration {

    @Autowired
    private ElasticSearchConfigProperties configProperties;


    @Override
    public ReactiveElasticsearchClient reactiveElasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(configProperties.getHostport().toArray(new String[]{}))
                // TODO
                .build();
        return DefaultReactiveElasticsearchClient.create(clientConfiguration);
    }


    /*@Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(configProperties.getHostport().toArray(new String[]{}))
//                .
                .build();
        return RestClients.create(clientConfiguration).rest();
    }*/

    @Bean
    @Override
    public EntityMapper entityMapper() {
        ElasticsearchEntityMapper entityMapper = new ElasticsearchEntityMapper(elasticsearchMappingContext(),
                new DefaultConversionService());
        entityMapper.setConversions(elasticsearchCustomConversions());

        return entityMapper;
    }
}
