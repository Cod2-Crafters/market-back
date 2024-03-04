package com.codecrafter.typhoon.service.social;

import com.codecrafter.typhoon.exception.BaseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
    public String getAccessToken(String code) {
        String accessToken = "";
        String refreshToekn = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=7cebc10989a4445b0de5c356817e4824");
            sb.append("&redirect_uri=http://localhost:8080/kredirect");
            sb.append("&client_secret=hLwT5vzNmdeBoWZROF4Tmc2t9PndbwtY");
            sb.append("&code=" + code);

            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            log.info("responsecode :::: ", responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            log.info("body :::: ", result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToekn =  element.getAsJsonObject().get("refresh_token").getAsString();

            log.info("acess token ::: ", accessToken);
            log.info("refresh token ::: ", refreshToekn);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    public Map<String, Object> createKakaoUser(String token) throws BaseException {

        String reqURL = "https://kapi.kakao.com/v2/user/me";
        System.out.println("token :::: " + token);

        Map<String, Object> userData = null;
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode :::: " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";


            while ((line = br.readLine()) != null) {
                result += line;
            }

            System.out.println("response body :::: " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            Long id = element.getAsJsonObject().get("id").getAsLong();
            String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

            userData = new HashMap<>();
            userData.put("id", id);
            userData.put("nickname", nickname);


            /*
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";

            if(hasEmail){
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

            System.out.println("id :::: " + id);
            System.out.println("email :::: " + email);

            br.close();
            */

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userData;
    }
}