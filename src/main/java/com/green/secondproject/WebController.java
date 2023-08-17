//package com.green.secondproject;
//
//import com.green.secondproject.config.security.model.MyUserDetails;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//public class WebController implements ErrorController {
//    @GetMapping({"/", "/error"})
//    public String index() {
//        return "index.html";
//    }
//
////    @GetMapping("/")
////    public String index() {
////        return "index.html";
////    }
////
////    @GetMapping("/error")
////    public String error() {
////        return "redirect:/";
////    }
//
////    @RequestMapping
////    public String index() {
////        return "forward:/index.html";
////    }
//}
//
