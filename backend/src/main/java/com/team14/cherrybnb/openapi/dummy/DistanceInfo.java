package com.team14.cherrybnb.openapi.dummy;

import lombok.Getter;

@Getter
public class DistanceInfo {

    private static final int M_TO_KM = 1000;
    private static final int SEC_TO_MIN = 60;

    private final double distance;
    private final double duration;

    public DistanceInfo(double distance, double duration) {
        this.distance = toKm(distance);
        this.duration = toMin(duration);
    }

    private double toKm(double distance) {
        return distance / M_TO_KM;
    }

    private double toMin(double duration) {
        return duration / SEC_TO_MIN;
    }
}
