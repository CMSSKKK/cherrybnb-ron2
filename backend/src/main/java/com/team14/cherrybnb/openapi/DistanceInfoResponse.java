package com.team14.cherrybnb.openapi;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DistanceInfoResponse {

    private String name;
    private String distance;
    private String duration;

}