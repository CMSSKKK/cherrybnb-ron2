package com.team14.cherrybnb.revervation.domain;

import com.team14.cherrybnb.auth.domain.Member;
import com.team14.cherrybnb.room.domain.Room;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private int guestCount;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private ReservationState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Reservation of(LocalDateTime checkIn, LocalDateTime checkOut,
                       int guestCount, BigDecimal totalPrice,
                       ReservationState state, Room room, Member member) {

        return new Reservation(null, checkIn, checkOut, guestCount, totalPrice, state, room, member);
    }

    public void cancel() {
        this.state = ReservationState.CANCEL;
    }
}
