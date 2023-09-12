package com.green.secondproject.common.config.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${file.dir}")
    private String fileDir;
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry reg) {
//        Path uploadDir = Paths.get(fileDir);
//        String uploadPath = uploadDir.toFile().getAbsolutePath();
//        if (fileDir.startsWith("../")) fileDir = fileDir.replace("../", "");
//        log.info("uploadPath {}", uploadPath);
//        reg.addResourceHandler( "/img/**")
//                .addResourceLocations(String.format("file:%s/", fileDir));
//        reg.addResourceHandler( "/**")
//                .addResourceLocations("classpath:/static/**")
//                .resourceChain(true)
//                .addResolver(new PathResourceResolver() {
//                    @Override
//                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
//                        Resource requestedResource = location.createRelative(resourcePath);
//                        // If we actually hit a file, serve that. This is stuff like .js and .css files.
//                        if (requestedResource.exists() && requestedResource.isReadable()) {
//                            return requestedResource;
//                        }
//                        // Anything else returns the index.
//                        return new ClassPathResource("/static/admin/index.html");
//                    }
//                });
//
//        reg.addResourceHandler("/admin/**","/admin")
//                .addResourceLocations("classpath:/static/admin/**")
//                .resourceChain(true)
//                .addResolver(new PathResourceResolver() {
//                    @Override
//                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
//                        Resource requestedResource = location.createRelative(resourcePath);
//                        // If we actually hit a file, serve that. This is stuff like .js and .css files.
//                        if (requestedResource.exists() && requestedResource.isReadable()) {
//                            return requestedResource;
//                        }
//
//                        // Anything else returns the index.
//                        return new ClassPathResource("/static/admin/index.html");
//                    }
//                });
//    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory(fileDir, registry);
    }
    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();
        log.info("uploadPath {}", uploadPath);
        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
        registry.addResourceHandler("/img/**").addResourceLocations("file:"+ uploadPath + "/");
        registry.addResourceHandler("/admin", "/admin/**")
                .addResourceLocations("classpath:/static/admin/**")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        // If we actually hit a file, serve that. This is stuff like .js and .css files.
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }
                        // Anything else returns the index.
                        return new ClassPathResource("/static/admin/index.html");
                    }
                });
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/**")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        // If we actually hit a file, serve that. This is stuff like .js and .css files.
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }
                        // Anything else returns the index.
                        return new ClassPathResource("/static/index.html");
                    }
                });
    }
}
