package cn.est;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@MapperScan("cn.est.mapper")
@SpringBootApplication
public class EstWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstWebApplication.class, args);
    }

}
