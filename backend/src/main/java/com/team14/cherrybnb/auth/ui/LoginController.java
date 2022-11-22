package com.team14.cherrybnb.auth.ui;

import com.team14.cherrybnb.auth.application.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;


    @GetMapping("/github/callback")
    public ResponseEntity<Void> login(String code) {
        String jwt = loginService.login(code);
        log.info("code={}", code);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwt)
                .build();
    }


}
