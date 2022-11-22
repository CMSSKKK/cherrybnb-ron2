package com.team14.cherrybnb.openapi.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
@NoArgsConstructor
public class NaviResponse {

    @JsonProperty("routes")
    private List<NaviInfo> infos;

    public NaviInfo getInfo() {
        return infos.get(0);
    }
}
