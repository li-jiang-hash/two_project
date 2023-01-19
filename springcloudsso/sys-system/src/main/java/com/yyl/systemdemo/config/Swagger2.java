//package com.yyl.systemdemo.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.ArrayList;
//import java.util.List;
//@Configuration
//@EnableSwagger2
//public class Swagger2 {
//
//    @Bean
//    public Docket createRestApi() {
//        ParameterBuilder ticketPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<Parameter>();
//        ticketPar.name("Authorization").description("bearer ")//Token 以及Authorization 为自定义的参数，需要哪个就可以写成那个
//                .modelRef(new ModelRef("string")).parameterType("header")
//                .required(true).build(); //header中的Authorization 是必填项
//        pars.add(ticketPar.build());
//        return new Docket(DocumentationType.SWAGGER_2)
//                // .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.yyl.systemdemo.controller"))// controller 文件所在的包
//                .paths(PathSelectors.any())// 所有的路径
//                .build().apiInfo(new ApiInfoBuilder()
//                        .title("SpringSecurity整合Swagger-bootstrap")
//                        .description("SpringSecurity整合Swagger-bootstrap，详细信息......")
//                        .version("9.0")
//                        .contact(new Contact("yuyongli","","123@qq.com"))
//                        .license("The Apache License")
//                        .licenseUrl("http://www.baidu.com")
//                        .build()).globalOperationParameters(pars);
//    }
//
////    private ApiInfo apiInfo() {
////        return new ApiInfoBuilder()
////                .title("服务:权限管理，用户管理，页面管理，日志 后台 APIs")
////                .description("服务:权限管理，用户管理，页面管理，日志 后台")
////                .termsOfServiceUrl("http://localhost:7100") //代码的路径
////                .contact(new Contact("yuyongli","","123@qq.com"))
////                .version("1.0")
////                .build();
////    }
//
//}