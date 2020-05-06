package com.hbhs.test.repository.es;

import com.hbhs.test.domain.es.UserEsEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserEsRepository extends ElasticsearchRepository<UserEsEntity, String> {

}
