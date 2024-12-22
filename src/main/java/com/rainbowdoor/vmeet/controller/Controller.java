package com.rainbowdoor.vmeet.controller;

import com.rainbowdoor.vmeet.entity.*;
import com.rainbowdoor.vmeet.service.*;
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
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private UserService userService;
    @Autowired
    private AssetService assetService;
    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatSessionService chatSessionService;

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
            Path destinationPath = Paths.get(basePath, generateMD5(uid.toString() + secret_key), type, assetName + fileName.substring(fileName.lastIndexOf("."))).normalize();

            // Ensure the directory exists
            Files.createDirectories(destinationPath.getParent());

            // Save the file
            Files.move(tempFile, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            if(assetService.selectAssetCountByNameUidAndType(assetName, uid, type) == 0) assetService.insertAsset(assetName, uid, type, "private");
            else assetService.updateLastModifiedTimeByNameUidAndType(assetName, uid, type);
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

    @GetMapping("/backend/getOwnAssets")
    @ResponseBody
    public ResponseEntity<List<Asset>> getOwnAssets(
            @RequestParam String assetName,
            @RequestParam String username,
            @RequestParam Integer expires,
            @RequestParam String type,
            @RequestParam String token) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = -1;
        String password = userService.selectPasswordByUsername(username);

        if (password != null) {
            String generatedToken = generateMD5(assetName + expires + password + type + username);
            if (generatedToken.equals(token)) {
                uid = userService.selectIdByUsername(username);
            }
        }

        if (password == null || uid == -1) {
            password = userService.selectPasswordByPhone(username);
            if (password != null) {
                String generatedToken = generateMD5(assetName + expires + password + type + username);
                if (!generatedToken.equalsIgnoreCase(token)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
                uid = userService.selectIdByPhone(username);
            }
        }

        if (uid == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<Asset> assets = assetService.selectAssetsByNameAndUidAndType(assetName, uid, type);
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/backend/getPublicAssets")
    @ResponseBody
    public ResponseEntity<List<UserAssetWithoutPrivacy>> getOwnAssets(
            @RequestParam String assetName,
            @RequestParam String type) {

        List<UserAssetWithoutPrivacy> assets = assetService.selectPublicAssetsByNameAndType(assetName, type);
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/backend/getFriendship")
    @ResponseBody
    public ResponseEntity<List<Friendship>> getFriendship(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<Friendship> friendships = friendshipService.selectFriendshipsByUid(uid);
        return ResponseEntity.ok(friendships);
    }

    @GetMapping("/backend/getAcceptedFriendship")
    @ResponseBody
    public ResponseEntity<List<Friendship>> getAcceptedFriendship(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<Friendship> friendships = friendshipService.selectAcceptedFriendshipsByUid(uid);
        return ResponseEntity.ok(friendships);
    }

    @GetMapping("/backend/getPendingFriendship")
    @ResponseBody
    public ResponseEntity<List<Friendship>> getPendingFriendship(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<Friendship> friendships = friendshipService.selectPendingFriendshipsByUid(uid);
        return ResponseEntity.ok(friendships);
    }

    @GetMapping("/backend/getRejectedFriendship")
    @ResponseBody
    public ResponseEntity<List<Friendship>> getRejectedFriendship(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<Friendship> friendships = friendshipService.selectRejectedFriendshipsByUid(uid);
        return ResponseEntity.ok(friendships);
    }

    @GetMapping("/backend/getFriendshipRequests")
    @ResponseBody
    public ResponseEntity<List<Friendship>> getFriendshipRequests(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<Friendship> friendshipRequests = friendshipService.selectFriendshipsByUid2(uid);
        return ResponseEntity.ok(friendshipRequests);
    }

    @GetMapping("/backend/getPendingFriendshipRequests")
    @ResponseBody
    public ResponseEntity<List<Friendship>> getPendingFriendshipRequests(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<Friendship> friendshipRequests = friendshipService.selectPendingFriendshipsByUid2(uid);
        return ResponseEntity.ok(friendshipRequests);
    }

    @GetMapping("/backend/getRejectedFriendshipRequests")
    @ResponseBody
    public ResponseEntity<List<Friendship>> getRejectedFriendshipRequests(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<Friendship> friendshipRequests = friendshipService.selectRejectedFriendshipsByUid2(uid);
        return ResponseEntity.ok(friendshipRequests);
    }

    @GetMapping("/backend/getAcceptedFriendshipRequests")
    @ResponseBody
    public ResponseEntity<List<Friendship>> getAcceptedFriendshipRequests(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<Friendship> friendshipRequests = friendshipService.selectAcceptedFriendshipsByUid2(uid);
        return ResponseEntity.ok(friendshipRequests);
    }

    @PostMapping("/backend/requestFriendship")
    @ResponseBody
    public ResponseEntity<Map<String, String>> requestFriendship(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires,
            @RequestParam Integer friendUid) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(friendUid + username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (friendUid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Map<String, String> result = new HashMap<>();
        if(uid == friendUid) {
            result.put("message", "You cannot send a friend request to yourself.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        if(friendshipService.selectCountByUid1AndUid2(uid, friendUid) > 0) {
            Friendship friendship = friendshipService.selectFriendshipByUid1AndUid2(uid, friendUid);
            if(friendship.getStatus().equals("pending")) {
                result.put("message", "Request already exists.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }
            if(friendship.getStatus().equals("accepted")) {
                result.put("message", "You are already friends with this user.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }
            if(friendship.getStatus().equals("rejected")) {
                friendshipService.deleteFriendshipByUid1AndUid2(uid, friendUid);
                friendshipService.insertFriendship(uid, friendUid, "pending");
                result.put("message", "success");
                return ResponseEntity.ok(result);
            }
        }
        friendshipService.insertFriendship(uid, friendUid, "pending");
        result.put("message", "success");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/backend/modifyFriendshipStatus")
    @ResponseBody
    public ResponseEntity<Map<String, String>> modifyFriendshipStatus(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires,
            @RequestParam Integer friendUid,
            @RequestParam String status) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(friendUid + status + username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (friendUid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (status == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Map<String, String> result = new HashMap<>();
        if (friendshipService.selectCountByUid1AndUid2Ordered(friendUid, uid) == 0) {
            result.put("message", "No request from this user.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        if (!status.equals("accepted") && !status.equals("rejected")) {
            result.put("message", "Invalid status.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        friendshipService.updateStatusByUid1AndUid2(uid, friendUid, status);
        result.put("message", "success");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/backend/deleteFriendship")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteFriendship(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires,
            @RequestParam Integer friendUid) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(friendUid + username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (friendUid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Map<String, String> result = new HashMap<>();
        if (friendshipService.selectCountByUid1AndUid2(uid, friendUid) == 0) {
            result.put("message", "You are not friends with this user.");
            return ResponseEntity.ok(result);
        }
        friendshipService.deleteFriendshipByUid1AndUid2(uid, friendUid);
        result.put("message", "success");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/backend/searchUserInfoByName")
    @ResponseBody
    public ResponseEntity<UserInfo> searchUserInfoByName(
            @RequestParam String username) {

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.ok(userInfoService.selectUserInfoByUid(uid));
    }

    @GetMapping("/backend/getUserInfo")
    @ResponseBody
    public ResponseEntity<UserInfo> getUserInfo(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);

        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        User user = userService.selectUserByUid(uid);
        user.setPassword("hidden");

        return ResponseEntity.ok(userInfoService.selectUserInfoByUid(uid));
    }

    @PostMapping("/backend/insertChat")
    @ResponseBody
    public ResponseEntity<Map<String, String>> insertChat(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires,
            @RequestParam Integer friendUid,
            @RequestParam String message) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(friendUid + message + username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (friendUid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (message == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Map<String, String> result = new HashMap<>();
        if (friendshipService.selectAcceptedCountByUid1AndUid2(uid, friendUid) == 0) {
            result.put("message", "You are not friends with this user.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        chatService.insertChat(uid, friendUid, message);
        if(chatSessionService.selectCountByUid1AndUid2(uid, friendUid) == 0) {
            chatSessionService.insertChatSession(uid, friendUid);
        }
        if(chatSessionService.selectCountByUid1AndUid2(friendUid, uid) == 0) {
            chatSessionService.insertChatSession(friendUid, uid);
        }
        result.put("message", "success");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/backend/deleteChat")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteChat(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires,
            @RequestParam Integer chatId) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(chatId + username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (chatId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Map<String, String> result = new HashMap<>();
        Chat chat = chatService.selectChatById(chatId);
        if (chat == null) {
            result.put("message", "Invalid chat ID.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        if (chat.getFrom_uid() != uid) {
            result.put("message", "You are not the sender of this message.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        chatService.updateVisibilityById(chatId, false);
        result.put("message", "success");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/backend/getChatHistory")
    @ResponseBody
    public ResponseEntity<List<Chat>> selectChatsByUid1AndUid2(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires,
            @RequestParam Integer friendUid,
            @RequestParam Integer limit,
            @RequestParam Integer offset) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(friendUid + limit.toString() + offset.toString() + username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (friendUid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(chatService.selectChatsByUid1AndUid2(uid, friendUid, limit, offset));
    }

    @GetMapping("/backend/getChatCount")
    @ResponseBody
    public ResponseEntity<Integer> selectChatCountByUid1AndUid2(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires,
            @RequestParam Integer friendUid) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(friendUid + username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (friendUid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (friendshipService.selectAcceptedCountByUid1AndUid2(uid, friendUid) == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(chatService.selectCountByUid1AndUid2(uid, friendUid));
    }

    @GetMapping("/backend/getChatSession")
    @ResponseBody
    public ResponseEntity<List<ChatSession>> selectChatSessionsByUid(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(chatSessionService.selectChatSessionsByUid1(uid));
    }

    @PostMapping("/backend/deleteChatSession")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteChatSession(
            @RequestParam String username,
            @RequestParam String token,
            @RequestParam Integer expires,
            @RequestParam Integer chatSessionId) {

        long currentTimestamp = Instant.now().getEpochSecond(); // current UTC timestamp in seconds
        if (currentTimestamp > expires) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String password = userService.selectPasswordByUsername(username);
        if (password == null) {
            password = userService.selectPasswordByPhone(username);
        }

        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String generatedToken = generateMD5(chatSessionId + username + expires + password);
        if (!generatedToken.equalsIgnoreCase(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Integer uid = userService.selectIdByUsername(username);
        if (uid == null) {
            uid = userService.selectIdByPhone(username);
        }

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Map<String, String> result = new HashMap<>();
        ChatSession chatSession = chatSessionService.selectChatSessionById(chatSessionId);
        if (chatSession == null) {
            result.put("message", "Invalid chat session ID.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        if (chatSession.getUid1() != uid) {
            result.put("message", "You are not the owner of this chat session.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        chatSessionService.deleteChatSessionById(chatSessionId);
        result.put("message", "success");
        return ResponseEntity.ok(result);
    }
}
