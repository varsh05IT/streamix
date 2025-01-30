package com.varsh.streamix.repository;

import com.varsh.streamix.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, String> {
	boolean existsByUserEmailId(String userEmailId);
}
