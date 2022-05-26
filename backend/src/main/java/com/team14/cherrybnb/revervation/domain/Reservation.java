package com.team14.cherrybnb.revervation.domain;

import com.team14.cherrybnb.room.domain.Room;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private int guestCount;

    private BigDecimal totalPrice;

    private boolean deleteYn;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
