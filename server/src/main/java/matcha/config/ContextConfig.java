package matcha.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.List;

@ComponentScan("matcha")
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

    @Value("${api.server.host}")
    public String serverHost;
    @Value("${api.server.port}")
    public String serverPort;

    @Value("${api.basepackage}")
    public String basePackage;

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

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//
//    @Bean
//    public ObjectMapper objectMapper(){
//        return new ObjectMapper();
//    }
}
