package com.varsh.streamix.dao;

import com.varsh.streamix.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean addNewUser(UserDetails userDetails) {
        try {
            String sql = "INSERT INTO users (id, username, email, password, phone_number) " +
                    "VALUES (?, ?, ?, ?, ?)";

            // Generate a unique ID if not provided
            String userId = userDetails.getId() != null
                    ? userDetails.getId()
                    : UUID.randomUUID().toString();

            jdbcTemplate.update(sql,
                    userId,
                    userDetails.getUserName(),
                    userDetails.getUserEmailId(),
                    encryptPassword(userDetails.getUserPassword()),
                    userDetails.getUserPhoneNo()
            );

            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    private String encryptPassword(String rawPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password encryption failed", e);
        }
    }

    public boolean checkUserExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count > 0;
    }
}
