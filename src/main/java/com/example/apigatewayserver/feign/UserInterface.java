package com.example.apigatewayserver.feign;

import com.example.apigatewayserver.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "order-service")
@Service
@RequestMapping("/api")
public interface UserInterface {

    @RequestMapping(value = "/users/get-user-by-username/{username}")
    ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username);
}
