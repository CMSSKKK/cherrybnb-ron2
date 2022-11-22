package com.team14.cherrybnb.room.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomPriceCondition {

    private BigDecimal weekdayPrice;

    private BigDecimal weekendPrice;

    private BigDecimal weeklyDiscount;

    private BigDecimal cleaningFee;

    public static RoomPriceCondition of(BigDecimal weekdayPrice, BigDecimal weekendPrice,
                                        BigDecimal weeklyDiscount, BigDecimal cleaningFee) {

        return new RoomPriceCondition(weekdayPrice, weekendPrice, weeklyDiscount, cleaningFee);
    }

    public BigDecimal calculateAvgPricePerDay() {
        return weekdayPrice
                .add(weekendPrice)
                .divide(new BigDecimal(2), 2, RoundingMode.FLOOR);
    }

    public BigDecimal calculateTotalPrice() {
        return null;
    }
}
