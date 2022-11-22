package com.team14.cherrybnb.room.domain;

import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long>, RoomRepositoryCustom {

    // TODO
    // 위치, 가격, 일정, 인원 수를 조건으로 해당하는 숙소 리스트 조회


    // TODO
    /**
     * 검색 지역 문자열을 기준으로 숙소 리스트 조회하기 (Elastic Search)
     */

    /**
     * 특정 지점을 기준으로 일정 반경 내에 위치한 숙소 리스트 조회하기
     * @param circle
     */
    @Query("select r from Room r join fetch r.address a " +
            "where within(a.coordinate, :circle) = true")
    List<Room> findRoomsWithinCircle(Geometry circle);

    /**
     * 숙박 시설의 상세 정보 조회
     * @param roomId
     */
    @Query("select r from Room r join fetch r.roomImages " +
            "join fetch r.member " +
            "join fetch r.address " +
            "where r.id = :roomId")
    Optional<Room> findByRoomId(@Param("roomId") Long roomId);
}
