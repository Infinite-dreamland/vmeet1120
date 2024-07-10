package com.rainbowdoor.vmeet.controller;

import com.rainbowdoor.vmeet.service.AssetService;
import com.rainbowdoor.vmeet.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private UserService userService;
    @Autowired
    private AssetService assetService;

    @Autowired
    private RestTemplate restTemplate;

    private String secret_key = "xl1120";

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

    @GetMapping("/backend/isAssetNameDuplicated")
    @ResponseBody
    public String isAssetNameDuplicated(@RequestParam String assetname, @RequestParam String username, @RequestParam Integer expires, @RequestParam String type, @RequestParam String token)
    {
        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return "timestamp expired";
        }
        if(userService.selectPasswordByUsername(username) != null)
        {
            String password = userService.selectPasswordByUsername(username);
            String generatedToken = generateMD5(assetname + expires + password + type + username);
            if(generatedToken.equals(token))
            {
                Integer uid = userService.selectIdByUsername(username);
                if(assetService.selectAssetCountByNameUidAndType(assetname, uid, type) == 0)
                {
                    return "ok";
                }
                else
                {
                    return "duplicated";
                }
            }
        }
        if(userService.selectPasswordByPhone(username) != null)
        {
            String password = userService.selectPasswordByPhone(username);
            String generatedToken = generateMD5(assetname + expires + password + type + username);
            if(!generatedToken.equalsIgnoreCase(token))
            {
                return "invalid token";
            }
            Integer uid = userService.selectIdByPhone(username);
            if(assetService.selectAssetCountByNameUidAndType(assetname, uid, type) == 0)
            {
                return "ok";
            }
            else
            {
                return "duplicated";
            }
        }
        return "wrong username or password";
    }

    @PutMapping("/backend/UserAssets")
    @ResponseBody
    public String uploadFile(@RequestParam("assetName") String assetName,
                             @RequestParam("fileName") String fileName,
                             @RequestParam("username") String username,
                             @RequestParam("expires") Integer expires,
                             @RequestParam("type") String type,
                             @RequestParam("token") String token,
                             HttpServletRequest request) {
        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return "timestamp expired";
        }
        long fileSize;
        Path tempFile;
        try {
            tempFile = Files.createTempFile("upload-", ".tmp"); // Create temp file
            // Transfer incoming file to the temp file
            fileSize = request.getInputStream().transferTo(Files.newOutputStream(tempFile));
        } catch (IOException e) {
            return "Failed to read file size: " + e.getMessage();
        }
        Integer uid = -1;
        if(userService.selectPasswordByUsername(username) != null)
        {
            String password = userService.selectPasswordByUsername(username);
            String generatedToken = generateMD5(assetName + expires + fileName + password + type + username + fileSize);
            if(generatedToken.equals(token))
            {
                uid = userService.selectIdByUsername(username);
            }
        }
        if(userService.selectPasswordByPhone(username) != null && uid == -1)
        {
            String password = userService.selectPasswordByPhone(username);
            String generatedToken = generateMD5(assetName + expires + fileName + password + type + username + fileSize);
            if(!generatedToken.equalsIgnoreCase(token))
            {
                return "invalid token";
            }
            uid = userService.selectIdByPhone(username);
        }
        else return "wrong username or password";
        try {
            String basePath = "D:\\UserAssetsManager\\UserAssetsManager\\Assets\\UserAssets";
            Path destinationPath = Paths.get(basePath, generateMD5(uid.toString() + secret_key), type, assetName, fileName).normalize();

            // Ensure the directory exists
            Files.createDirectories(destinationPath.getParent());

            // Save the file
            Files.move(tempFile, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            if(assetService.selectAssetCountByNameUidAndType(assetName, uid, type) == 0) assetService.insertAsset(assetName, uid, type, "private");
            return "File uploaded successfully: " + destinationPath;
        } catch (IOException e) {
            return "Failed to upload file: " + e.getMessage();
        }
    }

    private String generateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

}
