package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@PropertySource("classpath:application.properties")
public class ContextConfig {
    @Value("${app.datasource.url}")
    public String datasourceUrl;
    @Value("${app.datasource.username}")
    public String datasourceUsername;
    @Value("${app.datasource.password}")
    public String datasourcePassword;
    @Value("${app.datasource.driverclassname}")
    public String driverClassName;

    @Value("${server.host}")
    public String serverHost;
    @Value("${server.port}")
    public String serverPort;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(datasourceUrl);
        dataSource.setUsername(datasourceUsername);
        dataSource.setPassword(datasourcePassword);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public ObjectMapper objectMapper(){
//        return new ObjectMapper();
//    }
}
