package com.amadeuscam.perfumir_api.services.impl;

import com.amadeuscam.perfumir_api.repository.UserRepository;
import com.amadeuscam.perfumir_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            /**
             * Retrieves a full User object upon provided email.
             *
             * In case of unavailable user matching the provided email {@link UsernameNotFoundException} will be
             * thrown.
             */
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
}
