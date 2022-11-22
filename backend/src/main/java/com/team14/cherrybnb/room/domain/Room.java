package com.team14.cherrybnb.room.domain;

import com.team14.cherrybnb.auth.domain.Member;
import com.team14.cherrybnb.common.domain.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString(exclude = {"address", "member", "reviews", "roomImages"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    private String name;

    @Embedded
    private RoomInfo roomInfo;

    private String description;

    @Embedded
    private RoomPriceCondition roomPriceCondition;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<RoomImage> roomImages = new ArrayList<>();

    public Room(String name, RoomInfo roomInfo, String description,
                RoomPriceCondition roomPriceCondition, Address address, Member member) {

        this.name = name;
        this.roomInfo = roomInfo;
        this.description = description;
        this.roomPriceCondition = roomPriceCondition;
        this.address = address;
        this.member = member;
    }

    public BigDecimal calculateRating() {
        return new BigDecimal(this.reviews.stream()
                .mapToInt(Review::getStarRating)
                .sum())
                .divide(BigDecimal.valueOf(this.reviews.size()), 2, RoundingMode.FLOOR);
    }

    public BigDecimal calculateAveragePerDay() {
        return this.getRoomPriceCondition().calculateAvgPricePerDay();
    }

    public int getReviewCount() {
        return reviews.size();
    }

    public BigDecimal getTotalPrice() {
        return roomPriceCondition.calculateTotalPrice();
    }
}
