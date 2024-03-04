package com.codecrafter.typhoon.controller.social;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class SocialController {
    @GetMapping("/klogin")
    public String kakaoLogin() {
        return "/klogin.html";
    }

}
