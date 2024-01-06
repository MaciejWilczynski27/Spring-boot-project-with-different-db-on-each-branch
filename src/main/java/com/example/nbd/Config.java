package com.example.nbd;



import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DataCenterReplication;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

import java.util.Arrays;
import java.util.List;


@Configuration
public class Config extends AbstractCassandraConfiguration implements BeanClassLoaderAware {

    @Value("${cassandra.keyspace-name}")
    private String keyspaceName;

    @Value("${cassandra.local-datacenter}")
    private String datacenter;

    @Value("${cassandra.replication-factor}")
    private Long replicationFactor;

    @Value("${cassandra.contact-points}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private Integer port;

    @Value("${cassandra.username}")
    private String username;

    @Value("${cassandra.password}")
    private String password;

    @Value("${cassandra.schema-action}")
    private String schemaAction;

    @Value("${cassandra.consistency-level}")
    private String consistencyLevel;

    @Override
    public String getKeyspaceName() {
        return keyspaceName;
    }

    @Override
    public CqlSessionFactoryBean cassandraSession() {
        CqlSessionFactoryBean cqlSessionFactoryBean = super.cassandraSession();
        cqlSessionFactoryBean.setPort(port);
        cqlSessionFactoryBean.setContactPoints(contactPoints);
        cqlSessionFactoryBean.setKeyspaceName(keyspaceName);
        cqlSessionFactoryBean.setLocalDatacenter(datacenter);
        cqlSessionFactoryBean.setKeyspaceCreations(getKeyspaceCreations());
        cqlSessionFactoryBean.setPassword(password);
        cqlSessionFactoryBean.setUsername(username);
        return cqlSessionFactoryBean;
    }
    @Override
    public CqlTemplate cqlTemplate() {
        CqlTemplate cqlTemplate = super.cqlTemplate();
        cqlTemplate.setConsistencyLevel(DefaultConsistencyLevel.valueOf(consistencyLevel));
        return cqlTemplate;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.valueOf(schemaAction);
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(keyspaceName)
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withNetworkReplication(DataCenterReplication.of(datacenter, replicationFactor));
        return Arrays.asList(specification);
    }
}
