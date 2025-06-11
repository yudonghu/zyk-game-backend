package com.zyk.gamebackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "🎮 欢迎来到ZYK游戏后端服务器!");
        response.put("status", "运行中");
        response.put("time", LocalDateTime.now());
        response.put("version", "1.0.0");
        return response;
    }

    @GetMapping("/api/test")
    public Map<String, Object> test() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "API测试成功!");
        response.put("data", "服务器正常运行");
        return response;
    }

    @GetMapping("/api/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("database", "H2 - Connected");
        response.put("server", "Spring Boot - Running");
        return response;
    }
}