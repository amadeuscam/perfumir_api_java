package com.amadeuscam.perfumir_api;

import com.amadeuscam.perfumir_api.entities.Role;
import com.amadeuscam.perfumir_api.entities.User;
import com.amadeuscam.perfumir_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PerfumirApiApplication {


    public static void main(String[] args) {
        SpringApplication.run(PerfumirApiApplication.class, args);
    }


}
