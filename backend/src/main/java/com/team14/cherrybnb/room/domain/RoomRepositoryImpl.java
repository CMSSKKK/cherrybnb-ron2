package com.team14.cherrybnb.room.domain;

import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team14.cherrybnb.common.domain.QAddress;
import com.team14.cherrybnb.common.util.GeometryUtils;
import com.team14.cherrybnb.revervation.domain.QReservation;
import com.team14.cherrybnb.room.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.team14.cherrybnb.room.domain.QRoom.*;
import static com.team14.cherrybnb.common.domain.QAddress.*;
import static com.team14.cherrybnb.revervation.domain.QReservation.*;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom {

    // 위치, 가격, 일정, 인원 수를 조건으로 해당하는 숙소 리스트 조회

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Room> findBySearchCondition(SearchCondition searchCondition, Pageable pageable) {
        List<Room> rooms = jpaQueryFactory
                .selectFrom(room)

                .join(room.address, address)
                .leftJoin(reservation) // 일정
                .on(reservation.room.eq(room))

                .where(
                        withinCircle(searchCondition.getLongitude(), searchCondition.getLatitude()),
                        isReservable(searchCondition.getCheckIn(), searchCondition.getCheckOut()),
                        betweenPrice(searchCondition.getMinPrice(), searchCondition.getMaxPrice()), // 가격
                        isAcceptableGuestCount(searchCondition.getGuestCount()) // 인원

                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(rooms, pageable, 10);
    }

    private BooleanExpression betweenPrice(BigDecimal gte, BigDecimal loe) {
        if (gte == null || loe == null) {
            return null;
        }

        NumberPath<BigDecimal> n1 = room.roomPriceCondition.weekdayPrice;
        NumberPath<BigDecimal> n2 = room.roomPriceCondition.weekendPrice;
        return n1.add(n2).divide(2).between(gte, loe);
    }

    private BooleanExpression isAcceptableGuestCount(Integer guestCount) {
        if (guestCount == null) {
            return null;
        }

        return room.roomInfo.capacity.goe(guestCount);
    }

    private BooleanExpression isReservable(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkIn == null || checkOut == null) {
            return null;
        }

        QReservation qReservation = new QReservation("qReservation");

        return reservation.id.notIn(
                JPAExpressions
                        .select(qReservation.id)
                        .from(qReservation)
                        .where(
                                qReservation.checkIn.loe(checkOut).and(qReservation.checkOut.goe(checkIn))
                        )
        );
    }

    private BooleanExpression withinCircle(Double longitude, Double latitude) {
        if (longitude == null || latitude == null) {
            return null;
        }
        QAddress qAddress = new QAddress("qAddress");

        Geometry circle = GeometryUtils.createCircle(longitude, longitude, 10 );
        BooleanTemplate booleanTemplate = Expressions.booleanTemplate("function('WITHIN', {0}, {1})", address.coordinate, circle);


        return address.id.in(
                JPAExpressions.select(qAddress.id)
                        .from(qAddress)
                        .where(booleanTemplate.isTrue())
        );
    }
}
