package amhs.amhs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2 //开启swagger
public class SwaggerConfig {



    //配置swagger docket bean的实例
    @Bean
    public Docket docket(Environment environment){
        //设置要显示的swagger的环境
        Profiles profiles = Profiles.of("dev");
        //通过environment.acceptsProfiles 判断自己是否处在自己设定的环境当中
        boolean flag = environment.acceptsProfiles(profiles);

        return  new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("接口测试")
                .enable(flag)
                .select()
                //RequestHandlerSelectors 配置要扫描的接口方式
                //basePackage 配置要扫描的包
                .apis(RequestHandlerSelectors.basePackage("amhs.amhs.controller"))
                .build();
    }

    //配置swagger信息 apiInfo（）
    private ApiInfo apiInfo(){
        Contact contact = new Contact("阿米华晟","http://www.baidu.com","245709907@qq.com");
        return  new ApiInfo(
                "阿米华晟接口规范测试",
                "Api 接口测试",
                "v1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());

    }
}
