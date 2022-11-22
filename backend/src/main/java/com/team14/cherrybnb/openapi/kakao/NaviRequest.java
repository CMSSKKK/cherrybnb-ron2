package com.team14.cherrybnb.openapi.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NaviRequest {

    private static final String DELIMITER = ",";

    private String origin;
    private String destination;
    private boolean summary;

    public static NaviRequest of(double originX, double originY, double destinationX, double destinationY) {
        String origin = originX + DELIMITER + originY;
        String destination = destinationX + DELIMITER + destinationY;
        return new NaviRequest(origin, destination, true);
    }

}
