package com.team14.cherrybnb.auth.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GithubUser {

    @JsonProperty("login")
    private String githubId;

    @JsonProperty("name")
    private String username;

    @JsonProperty("avatar_url")
    private String imagePath;



}
