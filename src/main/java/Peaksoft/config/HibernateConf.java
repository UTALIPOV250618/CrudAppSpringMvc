package Peaksoft.config;


import Peaksoft.model.Car;
import Peaksoft.model.User;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
    @EnableTransactionManagement
    public class HibernateConf {

        @Bean
        public LocalSessionFactoryBean sessionFactory() {
            LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
            sessionFactory.setDataSource(dataSource());
            sessionFactory.setPackagesToScan("Peaksoft");
            sessionFactory.setAnnotatedClasses(User.class, Car.class);

            sessionFactory.setHibernateProperties(hibernateProperties());

            return sessionFactory;
        }

        @Bean
        public DataSource dataSource() {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName("org.h2.Driver");
            dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
            dataSource.setUsername("postgres");
            dataSource.setPassword("postgres");

            return dataSource;
        }

        @Bean
        public PlatformTransactionManager hibernateTransactionManager() {
            HibernateTransactionManager transactionManager
                    = new HibernateTransactionManager();
            transactionManager.setSessionFactory(sessionFactory().getObject());
            return transactionManager;
        }

        private final Properties hibernateProperties() {
            Properties hibernateProperties = new Properties();
            hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
            hibernateProperties.setProperty("hibernate.hbm2ddl.auto","update");
            hibernateProperties.setProperty("hibernate.dialect","org.hibernate.dialect.H2Dialect");
            hibernateProperties.setProperty("hibernate.show_sql", "true");

            return hibernateProperties;
        }
    }

