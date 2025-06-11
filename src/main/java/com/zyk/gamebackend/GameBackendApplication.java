package com.zyk.gamebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(GameBackendApplication.class, args);
        System.out.println("🎮 ZYK 游戏后端服务器启动成功!");
        System.out.println("🌐 服务器地址: http://localhost:8080");
        System.out.println("💾 数据库控制台: http://localhost:8080/h2-console");
    }
}
