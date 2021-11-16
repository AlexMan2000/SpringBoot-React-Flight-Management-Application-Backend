package nyu.alex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("nyu.alex.dao.mapper")
public class AlexApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlexApplication.class, args);
    }


}
