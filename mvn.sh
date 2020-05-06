cd common-dependencies
mvn install -Dmaven.test.skip=true

cd ../common-model
mvn install -Dmaven.test.skip=true

cd ../common-tools
mvn install -Dmaven.test.skip=true

cd ../common-datasource
mvn install -Dmaven.test.skip=true

cd ../common-error-handler
mvn install -Dmaven.test.skip=true

cd ../common-distribute-lock
mvn install -Dmaven.test.skip=true

cd ../common-elastic-search
mvn install -Dmaven.test.skip=true

cd ../common-job-executor
mvn install -Dmaven.test.skip=true

cd ../common-mq
mvn install -Dmaven.test.skip=true



cd ../common-apm
mvn install -Dmaven.test.skip=true

cd ../common-dam
mvn install -Dmaven.test.skip=true

cd ../common-ratelimiter
mvn install -Dmaven.test.skip=true

cd ../common-tracing-log
mvn install -Dmaven.test.skip=true

cd ../common-tracing-api
mvn install -Dmaven.test.skip=true

cd ../