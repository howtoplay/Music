package com.ethen.music.config;

import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "com.ethen.music")
@MapperScan("com.ethen.music.mapper")
@Configuration
public class FluentMyBatisConfig {

  @Bean
  public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    return bean;
  }
}
