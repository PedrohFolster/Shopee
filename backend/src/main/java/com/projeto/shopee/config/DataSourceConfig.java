package com.projeto.shopee.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource("classpath:custom.properties")
public class DataSourceConfig {

    @Value("${DATABASE_USERNAME}")
    private String dbUsername;

    @Value("${DATABASE_PASSWORD}")
    private String dbPassword;

    @Value("${DATABASE_URL}")
    private String dbUrl;

    @SuppressWarnings("rawtypes")
    @Bean
    public DataSource dataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url(dbUrl);
        builder.username(dbUsername);
        builder.password(dbPassword);
        builder.driverClassName("org.postgresql.Driver");
        return builder.build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.projeto.shopee");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true); 
        vendorAdapter.setShowSql(true); 
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", "create");
        factoryBean.setJpaProperties(jpaProperties);

        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;

    }
    
    // Mudar para create (Apagar a db, somente para realizar testes)
}

/*
 * Crie o arquivo custom.properties no diretório src/main/resources com o
 * seguinte conteúdo:
 *
 * DB_USERNAME=your_username
 * DB_PASSWORD=your_password
 *
 * Substitua 'your_username' e 'your_password' pelos valores reais das suas
 * credenciais do banco de dados.
 */

