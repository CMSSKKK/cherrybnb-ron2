package com.team14.cherrybnb.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SearchCondition {

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Integer guestCount;

    private Double longitude;

    private Double latitude;
}
