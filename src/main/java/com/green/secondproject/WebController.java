package com.green.secondproject;

import com.green.secondproject.config.security.model.MyUserDetails;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController implements ErrorController {
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/error")
    public String error(@AuthenticationPrincipal MyUserDetails user) {
        if (user == null) {
            return "redirect:/";
        }

        if ("ROLE_TC".equals(user.getRoles().get(0))) {
            return "redirect:/teacher/home";
        }

        if ("ROLE_STD".equals(user.getRoles().get(0))) {
            return "redirect:/student/home";
        }

        return "redirect:/";
    }
}
