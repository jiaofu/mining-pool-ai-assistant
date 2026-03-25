//package com.binance.pool.config;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by wanghui on 2018/6/5.
// */
//@Configuration
//@EnableSwagger2
//@ConditionalOnProperty(prefix = "swagger", value = {"enable"}, havingValue = "true")
//public class Swagger2 {
//
//    @Bean
//    public Docket petApi() {
//
//        //添加head参数start
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<Parameter>();
//
//        //tokenPar.name("X-USER-ID").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        //pars.add(tokenPar.build());
//        //添加head参数end
//
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .globalOperationParameters(pars)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.binance.pool.controller"))
//                .build();
//
//    }
//
//
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("binance")
//                .description("binance")
//                .termsOfServiceUrl("binance.com")
//                .contact("binance")
//                .version("1.0")
//                .build();
//    }
//
//}
