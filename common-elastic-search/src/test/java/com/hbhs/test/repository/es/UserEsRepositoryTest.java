package com.hbhs.test.repository.es;

import com.hbhs.test.BaseEsTest;
import com.hbhs.test.domain.es.UserEsEntity;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.Date;
import java.util.UUID;

public class UserEsRepositoryTest extends BaseEsTest {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private ElasticsearchRestTemplate restTemplate;
    @Autowired
    private UserEsRepository repository;

    @After
    public void tearDown() throws Exception {
        client.close();
    }

    @Test
    public void testIndex(){
        restTemplate.deleteIndex(UserEsEntity.class);
        restTemplate.createIndex(UserEsEntity.class);
        restTemplate.putMapping(UserEsEntity.class);
        restTemplate.refresh(UserEsEntity.class);
        System.out.println("aaaa");
    }

    @Test
    public void addUser() throws Exception{
        UserEsEntity entity = new UserEsEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setAge(10);
        entity.setName("ho");
        entity.setType("MAIL");
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setIsDeleted(false);
        repository.save(entity);
        Thread.sleep(10000L);
    }
}