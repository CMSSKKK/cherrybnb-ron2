package com.team14.cherrybnb.revervation.dto;

import com.team14.cherrybnb.auth.domain.Member;
import com.team14.cherrybnb.revervation.domain.Reservation;
import com.team14.cherrybnb.revervation.domain.ReservationState;
import com.team14.cherrybnb.room.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReservationRequest {

    private Long roomId;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private int guestCount;

    private BigDecimal totalPrice;

    public Reservation toEntity(Member member, Room room) {
        return Reservation.of(this.checkIn, this.checkOut, this.guestCount,
                this.totalPrice, ReservationState.COMPLETE, room, member);
    }
}
