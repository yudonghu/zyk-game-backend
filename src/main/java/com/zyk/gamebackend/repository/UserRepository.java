package com.zyk.gamebackend.repository;

import com.zyk.gamebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 根据userId查找用户
    Optional<User> findByUserId(String userId);

    // 根据用户名查找用户
    Optional<User> findByUsername(String username);

    // 检查userId是否存在
    boolean existsByUserId(String userId);

    // 检查用户名是否存在
    boolean existsByUsername(String username);
}