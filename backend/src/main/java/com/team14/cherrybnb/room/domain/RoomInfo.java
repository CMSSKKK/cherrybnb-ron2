package com.team14.cherrybnb.room.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomInfo {

    private int capacity;

    private int roomCount;

    private int bedCount;

    private int restroomCount;
}
