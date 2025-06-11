package com.zyk.gamebackend.controller;

import com.zyk.gamebackend.entity.User;
import com.zyk.gamebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 用户注册
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            String username = request.get("username");
            String password = request.get("password");

            // 验证输入
            if (username == null || username.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "用户名不能为空");
                return ResponseEntity.badRequest().body(response);
            }

            if (password == null || password.length() < 6) {
                response.put("success", false);
                response.put("message", "密码至少6位");
                return ResponseEntity.badRequest().body(response);
            }

            User user = userService.registerUser(username, password);
            response.put("success", true);
            response.put("message", "注册成功");
            response.put("user", createUserResponse(user));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 用户登录
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            String username = request.get("username");
            String password = request.get("password");

            User user = userService.loginUser(username, password);
            response.put("success", true);
            response.put("message", "登录成功");
            response.put("user", createUserResponse(user));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 根据ID获取用户信息
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> userOpt = userService.findById(id);
        if (userOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "用户不存在");
            return ResponseEntity.notFound().build();
        }

        response.put("success", true);
        response.put("user", createUserResponse(userOpt.get()));
        return ResponseEntity.ok(response);
    }

    // 根据userId获取用户信息
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getUserByUserId(@PathVariable String userId) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> userOpt = userService.findByUserId(userId);
        if (userOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "用户不存在");
            return ResponseEntity.notFound().build();
        }

        response.put("success", true);
        response.put("user", createUserResponse(userOpt.get()));
        return ResponseEntity.ok(response);
    }

    // 获取所有用户
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();

        List<User> users = userService.getAllUsers();
        response.put("success", true);
        response.put("count", users.size());
        response.put("users", users.stream().map(this::createUserResponse).toList());

        return ResponseEntity.ok(response);
    }

    // 删除用户（根据ID）
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            userService.deleteUser(id);
            response.put("success", true);
            response.put("message", "用户删除成功");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 删除用户（根据userId）
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUserByUserId(@PathVariable String userId) {
        Map<String, Object> response = new HashMap<>();

        try {
            userService.deleteByUserId(userId);
            response.put("success", true);
            response.put("message", "用户删除成功");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 创建用户响应对象（不包含密码）
    private Map<String, Object> createUserResponse(User user) {
        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("id", user.getId());
        userResponse.put("userId", user.getUserId());
        userResponse.put("username", user.getUsername());
        userResponse.put("createdAt", user.getCreatedAt());
        userResponse.put("lastLogin", user.getLastLogin());
        return userResponse;
    }
}