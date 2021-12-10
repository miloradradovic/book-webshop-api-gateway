package com.example.apigatewayserver.security.auth;

import com.example.apigatewayserver.security.TokenUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;

    private final UserDetailsService userDetailsService;

    public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService) {
        this.tokenUtils = tokenHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String username;
        String authToken = tokenUtils.getToken(httpServletRequest);

        if (authToken != null) {
            // gets the username from token
            username = tokenUtils.getUsernameFromToken(authToken);

            if (username != null) {
                // find user by username
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // is token valid
                if (tokenUtils.validateToken(authToken, userDetails)) {
                    // create authentication
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                    authentication.setToken(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // pass the request to the next filter
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
