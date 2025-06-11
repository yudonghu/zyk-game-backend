package com.zyk.gamebackend.service;

import com.zyk.gamebackend.entity.User;
import com.zyk.gamebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 用户注册
    public User registerUser(String username, String password) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("用户名已存在");
        }

        // 生成唯一的userId
        String userId;
        do {
            userId = "USER_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (userRepository.existsByUserId(userId));

        // 创建新用户
        User user = new User(userId, username, password);
        return userRepository.save(user);
    }

    // 用户登录
    public User loginUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("用户不存在");
        }

        User user = userOpt.get();
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }

        // 更新最后登录时间
        user.setLastLogin(LocalDateTime.now());
        return userRepository.save(user);
    }

    // 根据ID查找用户
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // 根据userId查找用户
    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    // 根据用户名查找用户
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 获取所有用户
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 更新用户信息
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // 删除用户
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 根据userId删除用户
    public void deleteByUserId(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new RuntimeException("用户不存在");
        }
    }
}