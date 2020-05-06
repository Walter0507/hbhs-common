package com.hbhs.common.datasource.autoconfig.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "db")
@Getter
@Setter
@ToString
public class DatabaseConfigProperties {

    private DatabaseConfig master;

    private List<DatabaseConfig> slaves;
}
