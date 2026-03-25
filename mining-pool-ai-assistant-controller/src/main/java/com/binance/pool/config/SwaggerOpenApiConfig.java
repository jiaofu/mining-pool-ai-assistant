package com.binance.pool.config;

import com.binance.pool.service.config.SysConfig;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import jakarta.annotation.Resource;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yyh on 2025/6/30.
 */
@Configuration
//@EnableSwagger2
@ConditionalOnProperty(prefix = "springdoc.swagger-ui", value = {"enabled"}, havingValue = "true")
public class SwaggerOpenApiConfig {

    @Bean
    public GroupedOpenApi customOpenAPI() {
        String website = "https://pool.qa1fdg.net/";


        // 联系人信息(contact)，构建API的联系人信息，用于描述API开发者的联系信息，包括名称、URL、邮箱等
        // name：文档的发布者名称 url：文档发布者的网站地址，一般为企业网站 email：文档发布者的电子邮箱
        Contact contact = new Contact()
                .name("Binance Pool Mining-Tech Team")                    // 作者名称
                .url(website)
                .extensions(new HashMap<String, Object>()); // 使用Map配置信息（如key为"name","email","url"）

        // 授权许可信息(license)，用于描述API的授权许可信息，包括名称、URL等；假设当前的授权信息为Apache 2.0的开源标准
        License license = new License()
                .name("SpringDoc")                         // 授权名称
                .url("http://springdoc.org")                // 授权信息
                .extensions(new HashMap<String, Object>());// 使用Map配置信息（如key为"name","url","identifier"）

        //创建Api帮助文档的描述信息、联系人信息(contact)、授权许可信息(license)
        Info info = new Info()
                .title("Binance Mining Pool ai  assistant 接口文档")      // Api接口文档标题（必填）
                .description("开发文档")     // Api接口文档描述
                .version("0.0.1")                                  // Api接口版本
                .termsOfService("https://binance.com")
                .license(license)                                  // 设置联系人信息
                .contact(contact);                                 // 授权许可信息

        // 返回信息
//        return new OpenAPI()
////                .openapi("2.4.0")  // Open API 2.4.0
//                .info(info);       // 配置Swagger3.0描述信息
        return GroupedOpenApi.builder().group("users")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    operation.addSecurityItem(new SecurityRequirement().addList("basicScheme"));
//                    generateGlobalRequestHeader(operation);
                    return operation;
                })
                .addOpenApiCustomizer(openApi -> openApi.info(info))
//                .packagesToScan("com.binance.pool.controller")
//                .pathsToMatch("/user/**")
                .build();
    }

    private void generateGlobalRequestHeader(Operation operation) {
        List<Parameter> parameters = operation.getParameters();
        if (parameters == null) {
            parameters = new ArrayList<>();
            operation.setParameters(parameters);
        }
        var parameter = new Parameter();
        var schema = new Schema<>();
        schema.setType("string");
        parameter.setName("X-USER-ID");
        parameter.setDescription("令牌");
        parameter.setIn("header");
        parameter.setRequired(false);
//        parameter.set$ref("");
        parameter.setDeprecated(false);
        parameter.setSchema(schema);
        parameters.add(parameter);
    }

}
