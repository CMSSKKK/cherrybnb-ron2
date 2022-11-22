package com.team14.cherrybnb.openapi.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
public class Summary {

    private Double distance;
    private Double duration;
}
