package com.demo.security;

import com.demo.entity.AdmUser;
import com.demo.repository.AdmUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdmUserRepository admUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        AdmUser admUser = admUserRepository.findByUserId(userId);
        if (admUser == null) {
            throw new AccessDeniedException("該帳號不存在");
        }

        return new CustomUserDetails(admUser);
    }
}
