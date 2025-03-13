package com.varsh.streamix.repository;

import com.varsh.streamix.model.Role;
import com.varsh.streamix.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {
    
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserDetails u WHERE u.userEmailId = :userEmailId")
    boolean existsByUserEmailId(@Param("userEmailId") String userEmailId);
    
    @Query(value = "SELECT * FROM users u WHERE u.userEmailId = :userEmailId", nativeQuery = true)
    UserDetails findUserByEmailId(@Param("userEmailId") String userEmailId);

    @Query("SELECT COUNT(u) > 0 FROM UserDetails u WHERE u.role = :role")
    boolean existsByRole(@Param("role") Role role);
    
    UserDetails findByUserEmailId(String userEmailId);



}