package com.vatika.secureapi;

import com.vatika.secureapi.entity.Role;
import com.vatika.secureapi.entity.User;
import com.vatika.secureapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seed(UserRepository repo) {
        return args -> {
            if (repo.count() > 0) return;

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            User alice = new User();
            alice.setUsername("alice");
            alice.setPasswordHash(encoder.encode("password"));
            alice.setRole(Role.CLIENT);
            alice.setClientId("acme");
            repo.save(alice);

            User sam = new User();
            sam.setUsername("sam");
            sam.setPasswordHash(encoder.encode("password"));
            sam.setRole(Role.STAFF);
            sam.setClientId("internal");
            repo.save(sam);
        };
    }
}
