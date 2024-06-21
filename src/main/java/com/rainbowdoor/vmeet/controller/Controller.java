package com.rainbowdoor.vmeet.controller;

import com.rainbowdoor.vmeet.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/backend/login")
    @ResponseBody
    public Map<String, String> login(@RequestParam Map<String, String> requestBody) {
        Map<String, String> result = new HashMap<>();
        if (userService.selectCountByNameAndPassword(requestBody.get("userName"), requestBody.get("password")) > 0 || userService.selectCountByPhoneAndPassword(requestBody.get("userName"), requestBody.get("password")) > 0){
            result.put("type", "1");
            result.put("message", "登录成功");
        } else {
            result.put("type", "0");
            result.put("message", "用户名或密码错误");
        }
        return result;
    }

    @PutMapping("/backend/reset-password")
    @ResponseBody
    public Map<String, Object> resetPassword(@RequestBody Map<String, String> requestBody, HttpServletRequest request) {
        String url = "https://15636000.bbscloud.com/api/auth/reset-password";

        // 从原请求中获取所有的Cookie header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 获取原始请求中的Cookie并转发
        String cookieHeader = request.getHeader(HttpHeaders.COOKIE);
        if (cookieHeader != null && !cookieHeader.isEmpty()) {
            headers.set(HttpHeaders.COOKIE, cookieHeader);
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
            headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);
        }

        // 创建HttpEntity，包含请求体和头部信息
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        // 发起PUT请求
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Map.class);

        Map<String, Object> result = new HashMap<>();
        if (response.getBody() != null) {
            result.putAll(response.getBody());

            if ((Integer) response.getBody().get("type") == 1) {
                userService.updatePasswordByPhone(requestBody.get("newPassword"), requestBody.get("accountMobile"));
            }
        }
        return result;
    }

    @PutMapping("/backend/username")
    @ResponseBody
    public Map<String, Object> username(@RequestBody Map<String, String> requestBody, HttpServletRequest request) {
        String url = "https://15636000.bbscloud.com/api/auth/username";

        // 从原请求中获取所有的Cookie header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 获取原始请求中的Cookie并转发
        String cookieHeader = request.getHeader(HttpHeaders.COOKIE);
        if (cookieHeader != null && !cookieHeader.isEmpty()) {
            headers.set(HttpHeaders.COOKIE, cookieHeader);
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
            headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);
        }

        String accountMobile = requestBody.get("username").substring(0, requestBody.get("username").indexOf("|"));
        String newUsername = requestBody.get("username").substring(requestBody.get("username").indexOf("|") + 1);
        requestBody.put("username", newUsername);
        // 创建HttpEntity，包含请求体和头部信息
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        // 发起PUT请求
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Map.class);

        Map<String, Object> result = new HashMap<>();
        if (response.getBody() != null) {
            result.putAll(response.getBody());

            if ((Integer) response.getBody().get("type") == 1) {
                userService.updateNameByPhone(newUsername, accountMobile);
            }
        }
        return result;
    }

    @PutMapping("/backend/account-mobile")
    @ResponseBody
    public Map<String, Object> accountMobile(@RequestBody Map<String, String> requestBody, HttpServletRequest request) {
        String url = "https://15636000.bbscloud.com/api/auth/account-mobile";

        // 从原请求中获取所有的Cookie header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 获取原始请求中的Cookie并转发
        String cookieHeader = request.getHeader(HttpHeaders.COOKIE);
        if (cookieHeader != null && !cookieHeader.isEmpty()) {
            headers.set(HttpHeaders.COOKIE, cookieHeader);
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
            headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);
        }

        String oldAccountMobile = requestBody.get("accountMobile").substring(0, requestBody.get("accountMobile").indexOf("|"));
        String newAccountMobile = requestBody.get("accountMobile").substring(requestBody.get("accountMobile").indexOf("|") + 1);
        requestBody.put("accountMobile", newAccountMobile);
        // 创建HttpEntity，包含请求体和头部信息
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        // 发起PUT请求
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Map.class);

        Map<String, Object> result = new HashMap<>();
        if (response.getBody() != null) {
            result.putAll(response.getBody());

            if ((Integer) response.getBody().get("type") == 1) {
                userService.updatePhoneByPhone(newAccountMobile, oldAccountMobile);
            }
        }
        return result;
    }

    @PutMapping("/backend/api-reset-password")
    @ResponseBody
    public Map<String, Object> apiResetPassword(@RequestBody Map<String, String> requestBody, HttpServletRequest request) {
        String url = "https://15636000.bbscloud.com/api/reset-password";

        // 从原请求中获取所有的Cookie header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 获取原始请求中的Cookie并转发
        String cookieHeader = request.getHeader(HttpHeaders.COOKIE);
        if (cookieHeader != null && !cookieHeader.isEmpty()) {
            headers.set(HttpHeaders.COOKIE, cookieHeader);
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
            headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);
        }

        // 创建HttpEntity，包含请求体和头部信息
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        // 发起PUT请求
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Map.class);

        Map<String, Object> result = new HashMap<>();
        if (response.getBody() != null) {
            result.putAll(response.getBody());

            if ((Integer) response.getBody().get("type") == 1) {
                userService.updatePasswordByPhone(requestBody.get("newPassword"), requestBody.get("accountMobile"));
            }
        }
        return result;
    }
}
