package com.devtracker.userapp.component;

import com.devtracker.userapp.entity.User;
import com.devtracker.userapp.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Automatically seeds the H2 database with sample admin and regular users upon server boot
 */
@Component
public class DataInitializer {

    private final UserRepository userRepository;

    @Autowired
    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void initData() {
        if (userRepository.count() == 0) {
            // Seed 2 Administrators (Role "A")
            userRepository.save(User.builder()
                    .name("Sarah")
                    .lastName("Connor")
                    .email("sarah.connor@resistance.net")
                    .role("A")
                    .address("101 Safehouse Way, Mojave, CA")
                    .build());

            userRepository.save(User.builder()
                    .name("T-800")
                    .lastName("Model 101")
                    .email("cyberdyne.systems@cyberdyne.org")
                    .role("A")
                    .address("100 Computer History Museum Dr, Mountain View, CA")
                    .build());

            // Seed 3 Regular Users (Role "U")
            userRepository.save(User.builder()
                    .name("John")
                    .lastName("Connor")
                    .email("john.connor@resistance.net")
                    .role("U")
                    .address("Bunker 12, High Desert, CA")
                    .build());

            userRepository.save(User.builder()
                    .name("Marcus")
                    .lastName("Wright")
                    .email("marcus.wright@projectangel.org")
                    .role("U")
                    .address("88 Cyberdyne Cyber Lane, San Francisco, CA")
                    .build());

            userRepository.save(User.builder()
                    .name("Kyle")
                    .lastName("Reese")
                    .email("kyle.reese@resistance.net")
                    .role("U")
                    .address("Grid Sector 4, Tech-Ruins Area, Los Angeles, CA")
                    .build());

            System.out.println("DataInitializer: Successfully seeded database with 2 admin and 3 regular users.");
        }
    }
}
