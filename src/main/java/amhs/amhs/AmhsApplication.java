package amhs.amhs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.servlet.MultipartConfigElement;


@SpringBootApplication
//@EnableJpaRepositories(basePackages = "amhs.amhs.dao")
//@EntityScan(basePackages = "amhs.amhs.entity")
public class AmhsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmhsApplication.class, args);
    }
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(uploadFolder);
        return factory.createMultipartConfig();
    }


}
