package com.rainbowdoor.vmeet.controller;

import com.rainbowdoor.vmeet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/register")
    @ResponseBody
    public Map<String, Object> register(@RequestBody Map<String, Object> requestBody) {
        String url = "https://15636000.bbscloud.com/api/register";
        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestBody, Map.class);

        Map<String, Object> result = new HashMap<>();
        if (response.getBody() != null) {
            Map responseBody = response.getBody();
            result.putAll(responseBody);

            if ((Integer) responseBody.get("type") == 1) {
                userService.insertUser(
                        (String) requestBody.get("userName"),
                        (String) requestBody.get("password"),
                        String.valueOf(requestBody.get("accountMobile"))
                );
            }
        }
        return result;
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, String> login(@RequestBody Map<String, String> requestBody) {
        Map<String, String> result = new HashMap<>();
        if (userService.selectCountByNameAndPassword(requestBody.get("userName"), requestBody.get("password")) > 0) {
            result.put("type", "1");
            result.put("message", "登录成功");
        } else {
            result.put("type", "0");
            result.put("message", "用户名或密码错误");
        }
        return result;
    }
}
