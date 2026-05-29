package com.devtracker.userapp.repository;

import com.devtracker.userapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    // Find users by classification role ("A" / "U")
    List<User> findByRole(String role);
    
    // Count active records matching role type
    long countByRole(String role);
}
