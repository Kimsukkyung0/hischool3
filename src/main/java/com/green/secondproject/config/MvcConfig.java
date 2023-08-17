//package com.green.secondproject.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Slf4j
//@Configuration
//public class MvcConfig implements WebMvcConfigurer {
//
//    @Value("/home/download")
//    private String fileDir;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        exposeDirectory(fileDir, registry);
//    }
//
//    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
//        Path uploadDir = Paths.get(dirName);
//        String uploadPath = uploadDir.toFile().getAbsolutePath();
//        log.info("uploadPath {}", uploadPath);
//        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
//        registry.addResourceHandler("/img/**").addResourceLocations("file:"+ uploadPath + "/");
//    }
//}