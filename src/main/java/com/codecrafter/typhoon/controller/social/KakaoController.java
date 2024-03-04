package com.codecrafter.typhoon.controller.social;

import com.codecrafter.typhoon.service.social.KakaoService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/kakao")
    public String kakao() {
        return "/kakao";
    }

    //@GetMapping("/oauth2/authorization/kakao")
    @GetMapping("/kredirect")
    public Map<String, Object> kakaoCallback(@RequestParam("code") String code) {
        Map<String, Object> responseMap = new HashMap<>();
        String accessToken = kakaoService.getAccessToken(code);
        responseMap = kakaoService.createKakaoUser(accessToken);
        System.out.println("responseMap :: "+ responseMap);
        return responseMap;
    }

}
