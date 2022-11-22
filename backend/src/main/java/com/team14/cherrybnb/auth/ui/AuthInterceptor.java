package com.team14.cherrybnb.auth.ui;

import com.team14.cherrybnb.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//
//        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
//        String jwt = token.replaceFirst("Bearer ", "");
//        String memberId = jwtProvider.verifyToken(jwt);

        // memberId 가 db에 있으면

        return true;
    }
}
