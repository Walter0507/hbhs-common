package com.hbhs.common.datasource.route;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    private List<String> masterKeyList = new ArrayList<>();
    private List<String> slaveKeyList = new ArrayList<>();

    @Override
    protected Object determineCurrentLookupKey() {
        String dbKey = null;
        if (DynamicDataSourceHolder.accessSlave()){
            dbKey = slaveKeyList.get(new Random().nextInt(slaveKeyList.size()));
        }else{
            dbKey = masterKeyList.get(new Random().nextInt(masterKeyList.size()));
        }
        log.debug("Current request will choose database instance: {}", dbKey);
        return dbKey;
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources){
        super.setTargetDataSources(targetDataSources);

        for (Object o : targetDataSources.keySet()) {
            if (o.toString().startsWith(DynamicDataSourceHolder.SLAVE)){
                slaveKeyList.add(o.toString());
            }else if (o.toString().startsWith(DynamicDataSourceHolder.MASTER)){
                masterKeyList.add(o.toString());
            }
        }
        log.debug("Already set dynamic datasource, master: {}, slaves:{}", masterKeyList, slaveKeyList);
    }
}
