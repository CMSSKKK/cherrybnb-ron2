package com.team14.cherrybnb.auth.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GithubToken {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;

    public String getAccessTokenAndType() {
        return this.tokenType + " " + this.accessToken;
    }

}
