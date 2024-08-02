package com.demo.filter;

import com.demo.constant.JwtConst;
import com.demo.domain.JwtToken;
import com.demo.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.token.secret:ziyaotest123}")
    private String JWT_TOKEN_SECRET;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserDetailsService customUserDetailsService;

    private RequestMatcher requestMatcher = new RequestHeaderRequestMatcher(JwtConst.JWT_HEADER_NAME);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = request.getHeader(JwtConst.JWT_HEADER_NAME);
            Jws<Claims> jws = JwtTokenUtil.claimsParam(token, JWT_TOKEN_SECRET);
            JwtToken identity = objectMapper.convertValue(jws.getBody().get(JwtConst.JwtParams.IDENTITY.name()), JwtToken.class);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(identity.getUserId());
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (Exception ignored) {
        }
        filterChain.doFilter(request, response);
    }
}
