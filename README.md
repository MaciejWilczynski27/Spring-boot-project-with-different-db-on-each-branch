Spring boot CRUD application using different database managements systems(or message brokers) on each branch. All the databases(and message brokers) are running on docker containers
- Master - uses PostgreSQL
- NBD_Cassandra - uses Apache Cassandra
- NBD_MongoDB - uses MongoDB
- NBD_Redis_MongoDB - uses Redis as cache and MongoDB as it`s main datasource
- NBD_Kafka_MongoDB - uses MongoDB as datasource, and Kafka as a message broker to send information another application which is included in this project
