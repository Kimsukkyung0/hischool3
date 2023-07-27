//package com.green.secondproject.config;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfiguration {
//    private final String JWT_TYPE;
//    private final String HEADER_SCHEME_NAME;
//
//    public SwaggerConfiguration(@Value("${springboot.jwt.token-type}") String jwtType
//            , @Value("${springboot.jwt.header-scheme-name}") String headerSchemeName) {
//        this.JWT_TYPE = jwtType;
//        this.HEADER_SCHEME_NAME = headerSchemeName;
//    }
//
//    @Bean
//    public OpenAPI openAPI() {
//        final Info info = new Info()
//                .version("v0.0.1")
//                .title("고등학교 성적 관리")
//                .description("성적 관리 시스템");
//
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList(HEADER_SCHEME_NAME);
//
//        SecurityScheme securityScheme = new SecurityScheme()
//                .name(HEADER_SCHEME_NAME)
//                .type(SecurityScheme.Type.HTTP)
//                .scheme(JWT_TYPE)
//                .in(SecurityScheme.In.HEADER)
//                .bearerFormat("JWT");
//
//        Components components = new Components()
//                .addSecuritySchemes(HEADER_SCHEME_NAME, securityScheme);
//
//        // SecuritySchemes 등록
//        return new OpenAPI()
//                .info(info)
//                .addSecurityItem(securityRequirement)
//                .components(components);
//    }
//}
