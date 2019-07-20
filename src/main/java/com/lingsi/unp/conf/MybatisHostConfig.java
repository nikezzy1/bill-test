package com.lingsi.unp.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.lingsi.uap.mapper"}, sqlSessionTemplateRef = "hostSqlSessionTemplate")
public class MybatisHostConfig {


    @Bean
    //默认数据源
    @Primary
    //类型安全的属性配置
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource hostDataSource() {
        //通过DataSourceBuilder构建数据源
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(name = "hostSqlSessionFactory")
    @Primary
    public SqlSessionFactory hostSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //由于拆分了包结构，这里指定mapper
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapping/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "hostTransactionManager")
    @Primary
    public DataSourceTransactionManager hostTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "hostSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate hostSqlSessionTemplate(@Qualifier("hostSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}