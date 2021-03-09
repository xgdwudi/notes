//package com.atguigu.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.TransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
///**
// * @version 1.0
// * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
// * @ClassName Txconfig
// * @Description TODO
// * @Aurhor xu
// * @Ddte 2021/3/7 11:01
// **/
//@Configuration
//@ComponentScan(basePackages = "com.atguigu")
//@EnableTransactionManagement  // 开启事务
//public class Txconfig {
//
//
//    @Bean
//    public DruidDataSource getDruidDataSource(){
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/jpa");
//        druidDataSource.setUsername("root");
//        druidDataSource.setPassword("123456");
//        return druidDataSource;
//    }
////    创建jdbcTemplate对象
//    @Bean
//    public JdbcTemplate getJdbcTemplate(DataSource druidDataSource){
//        JdbcTemplate jdbcTemplate = new JdbcTemplate();
//        jdbcTemplate.setDataSource(druidDataSource);
//        return jdbcTemplate;
//    }
////    创建事务管理器
//    @Bean
//    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource druidDataSource){
//        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
//        dataSourceTransactionManager.setDataSource(druidDataSource);
//        return dataSourceTransactionManager;
//    }
//}
