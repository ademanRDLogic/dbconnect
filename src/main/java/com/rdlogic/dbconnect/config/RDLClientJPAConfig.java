package com.rdlogic.dbconnect.config;

import com.rdlogic.dbconnect.db.RDLClientDatasourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({com.rdlogic.dbconnect.config.ConnectionConfig.class, JpaProperties.class})
@EnableJpaRepositories(basePackages = {"com.rdlogic.dbconnect.dao", "com.rdlogic.dbconnect.db.entities"}, transactionManagerRef = "txManager")
@EnableTransactionManagement
public class RDLClientJPAConfig {

    @Inject
    private JpaProperties jpaProperties;
    @Inject
    private ConnectionConfig connectionConfig;
    @Inject
    private ClientEnvironmentConfig environmentConfig;
    @Inject
    private RDLClientDatasourceFactory dsFactory;
    @Value("${rdl.client}")
    private String client;

    @Bean(name = "rdlDatasource")
    public DataSource rdlClientDatasource() throws SQLException {
//        dsFactory.setConnectionConfig(connectionConfig);
//        dsFactory.setEnvironmentConfig(environmentConfig);
        return dsFactory.buildClientDatasource(client);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws SQLException {
        Map<String,Object> hibernateProps = new LinkedHashMap<>();
        hibernateProps.putAll(this.jpaProperties.getProperties());
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(rdlClientDatasource());
        emf.setPackagesToScan("com.rdlogic.dbconnect.dao", "com.rdlogic.dbconnect.db.entities");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaPropertyMap(hibernateProps);
        return emf;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return entityManagerFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager txManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txm = new JpaTransactionManager();
        txm.setEntityManagerFactory(entityManagerFactory);
        return txm;
    }
}
