package com.lashes.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan({"com.lashes.*"})
@EnableTransactionManagement
@Import({SecurityConfig.class})
public class AppConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws URISyntaxException{
        LocalContainerEntityManagerFactoryBean emm = new LocalContainerEntityManagerFactoryBean();
        emm.setDataSource(dataSource());
        emm.setPackagesToScan(new String[]{"com.lashes.entities"});
        JpaVendorAdapter vendorAdapter =new HibernateJpaVendorAdapter();
        emm.setJpaVendorAdapter(vendorAdapter);
        emm.setJpaProperties(additionalProperties());
        return emm;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }


    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties additionalProperties(){
        Properties prop = new Properties();
        prop.put("hibernate.format_sql","true");
        prop.put("hibernate.show_sql","true");
        prop.put("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
        prop.setProperty("hibernate.hbm2ddl.auto","update");
        return prop;

    }



   /* @Bean(name = "dataSource")
    public BasicDataSource dataSource(){
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/lashes");
        ds.setUsername("postgres");
        ds.setPassword("postgres");
        return  ds;

    }*/

    @Bean
    public DataSource dataSource() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
        System.out.println(dbUri);

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        System.out.println("the username is : "+username);
        System.out.println("the password is : "+password);

        String dbUrl = "jdbc:postgresql://ec2-50-19-126-219.compute-1.amazonaws.com:5432/d7tnikeeo9k246";

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("org.postgresql.Driver");

        return dataSource;
    }


    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public TemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(templateResolver());
        engine.addDialect(new SpringSecurityDialect());
        return engine;
    }

    private ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(false);
        return resolver;
    }

    /* configure resource handlers to server static resources like css, javascript etc */
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");

    }


}
