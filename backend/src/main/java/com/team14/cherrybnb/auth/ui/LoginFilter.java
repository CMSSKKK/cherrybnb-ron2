package com.team14.cherrybnb.auth.ui;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("로그인 처리 중...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

//        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
//        String jwt = token.replaceFirst("Bearer ", "");
//        String memberId = jwtProvider.verifyToken(jwt);

        // memberId 가 db에 있으면
        chain.doFilter(request, response);
    }
}
