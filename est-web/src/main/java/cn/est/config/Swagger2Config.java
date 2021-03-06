package cn.est.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config extends WebMvcConfigurationSupport {
    @Bean
    public Docket createRestApi() {

        List<Parameter> parameters = new ArrayList<>();
        Parameter timestamp = new ParameterBuilder()
                .name("timestamp")                          // 参数名称
                .description("时间戳")                      // 参数描述
                .modelRef(new ModelRef("String"))    // 参数类型
                .parameterType("header")                    // 参数传输类型
                .required(true )                            // 是否必传
                .build();
        parameters.add(timestamp);

        Parameter sourceType = new ParameterBuilder()
                .name("source-type")                         // 参数名称
                .description("访问来源( 0：APP，1：PC)")   // 参数描述
                .modelRef(new ModelRef("int"))       // 参数类型
                .parameterType("header")                    // 参数传输类型
                .required(true )                            // 是否必传
                .build();
        parameters.add(sourceType);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))//扫描带@Api注解的接口类
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("二手兔项目接口文档")
                .description("二手兔项目相关接口的文档")
                .termsOfServiceUrl("http://localhost:8080/est/")
                .version("1.0")
                .build();
    }
    /**
     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。 需要重新指定静态资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

}
