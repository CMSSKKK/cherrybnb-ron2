package com.team14.cherrybnb.auth.application;

import com.team14.cherrybnb.auth.github.GithubToken;
import com.team14.cherrybnb.auth.github.GithubTokenService;
import com.team14.cherrybnb.auth.github.GithubUser;
import com.team14.cherrybnb.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final GithubTokenService githubTokenService;
    private final JwtProvider jwtProvider;

    public String login(String code) {
        GithubToken githubToken = githubTokenService.getAccessToken(code);
        GithubUser userInfo = githubTokenService.getUserInfo(githubToken.getAccessTokenAndType());

        // db 저장 로직
        return jwtProvider.generateToken(userInfo.getGithubId());
    }

}
