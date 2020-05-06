package com.hbhs.common.datasource.autoconfig.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter@Setter@ToString
public class DatabaseConfig {
	
	private String driverClassName = "com.mysql.jdbc.Driver";
	private String url;
	private String username;
	private String password;

	/** 连接池中最初初始化后的连接数 **/
	private int initialSize = 5;
	/** 连接池中最大活动连接数**/
	private int maxActive = 300;
	/** 连接池中最大空闲连接数，超过的空闲连接将被释放，如果设置为负数表示不限**/
	private int maxIdle = 20;
	/** 连接池中最小空闲连接数，低于这个数量会被创建新的连接**/
	private int minIdle = 5;
	/** 最大等待时间，当没有可用连接时，连接池等待连接释放的最大时间，超过该时间限制会抛出异常，如果设置-1表示无限等待**/
	private int maxWait = 60000;
	/** 超过时间限制，回收没有用(废弃)的连接**/
	private int removeAbandonedTimeout = 180;
	/** 超过removeAbandonedTimeout时间后，是否进**/
	private boolean removeAbandoned = true;
	private boolean testOnBorrow = true;
	private boolean testOnReturn = true;
	private boolean testWhileIdle = true;
	private boolean testOnConnect = true;
	private String validationQuery = "SELECT 1";
	/** 检查无效连接的时间间隔 **/
	private int timeBetweenEvictionRunsMillis = 1000 * 5;
	private String jdbcInterceptors = "ConnectionState;StatementFinalizer";

	public static class DatabasePropertiesList{
		
		private List<DatabaseConfig> databaseConfigList = new ArrayList<DatabaseConfig>();


		public List<DatabaseConfig> getDatabaseConfigList() {
			return databaseConfigList;
		}


		public void setDatabaseConfigList(List<DatabaseConfig> databaseConfigList) {
			this.databaseConfigList = databaseConfigList;
		}
	}
}
