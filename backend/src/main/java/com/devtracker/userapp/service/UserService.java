package com.devtracker.userapp.service;

import com.devtracker.userapp.dto.request.UserRequest;
import com.devtracker.userapp.dto.response.UserSummaryResponse;
import com.devtracker.userapp.entity.User;
import com.devtracker.userapp.exception.ResourceNotFoundException;
import com.devtracker.userapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Service orchestrating transactional user operations, data conversions, and error checks
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Transactional(readOnly = true)
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User registry record not found for ID: " + id));
    }

    public User createUser(UserRequest request) {
        User user = User.builder()
                .name(request.name())
                .lastName(request.lastName())
                .email(request.email())
                .role(request.role())
                .address(request.address())
                .build();
        return userRepository.save(user);
    }

    public User updateUser(UUID id, UserRequest request) {
        User user = getUserById(id);
        user.setName(request.name());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setRole(request.role());
        user.setAddress(request.address());
        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public UserSummaryResponse getSummary() {
        long total = userRepository.count();
        long adminCount = userRepository.countByRole("A");
        long userCount = userRepository.countByRole("U");
        return new UserSummaryResponse(total, adminCount, userCount);
    }
}
