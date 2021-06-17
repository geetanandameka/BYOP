package com.example.url.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    public static final Contact DEFAULT_CONTACT = new Contact("Url Management", "localhost",
            "geetanandameka@gmail.com");

    public static final ApiInfo DEFAULT_API_INFO = new ApiInfoBuilder().contact(DEFAULT_CONTACT).license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0").title("Url Management API Guide")
            .description("Details of API'S").version("1.0").build();

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO).useDefaultResponseMessages(false)
                .enable(/*
                 */true)
                .select().apis(RequestHandlerSelectors.any()).paths(Predicates.not(PathSelectors.regex("/error"))).build()
                .forCodeGeneration(true).enableUrlTemplating(false).globalOperationParameters(operationParameters())
                .tags(new Tag("UrlManagement", "Url Management API Details"));
    }

    private List<Parameter> operationParameters() {
        List<Parameter> headers = new ArrayList<>();
        return headers;
    }
}
