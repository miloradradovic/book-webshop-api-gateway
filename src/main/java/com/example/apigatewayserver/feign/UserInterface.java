package com.example.apigatewayserver.feign;

import com.example.apigatewayserver.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "auth-service")
@Service
@RequestMapping("/api")
public interface UserInterface {

    @GetMapping(value = "/users/get-user-by-username/{username}")
    ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username);
}
