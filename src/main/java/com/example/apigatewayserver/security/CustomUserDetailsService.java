package com.example.apigatewayserver.security;

import com.example.apigatewayserver.dto.UserResponseDTO;
import com.example.apigatewayserver.feign.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserInterface userInterface;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserResponseDTO userResponseDTO = userInterface.getUserByUsername(username).getBody();
        assert userResponseDTO != null;
        List<GrantedAuthority> grantedAuthorities = userResponseDTO.getRoles()
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new User(username, userResponseDTO.getPassword(), true, true, true, true, grantedAuthorities);
    }
}
