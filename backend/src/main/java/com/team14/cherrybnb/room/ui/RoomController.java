package com.team14.cherrybnb.room.ui;

import com.team14.cherrybnb.room.application.RoomService;
import com.team14.cherrybnb.room.dto.PricesResponse;
import com.team14.cherrybnb.room.dto.RoomCardResponse;
import com.team14.cherrybnb.room.dto.RoomDetailResponse;
import com.team14.cherrybnb.room.dto.SearchCondition;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @ApiOperation(
            value = "특정 숙소 상세 정보 보기",
            notes = "특정 숙소 상세 정보를 보여준다.",
            produces = "application/json",
            response = RoomDetailResponse.class
    )
    @GetMapping("/{id}")
    public RoomDetailResponse getRoomDetail(@PathVariable("id") Long roomId) {
        return roomService.getRoomDetail(roomId);
    }

    // TODO pageable
    @ApiOperation(
            value = "숙소 목록 조회",
            notes = "숙소 목록을 조회한다.",
            produces = "application/json",
            response = RoomCardResponse.class
    )
    @GetMapping
    public Page<RoomCardResponse> getRooms(SearchCondition searchCondition, Pageable pageable) {
        return roomService.getRoomsBySearchCondition(searchCondition, pageable);
    }

    @ApiOperation(
            value = "특정 지역에 속한 숙소 목록 조회",
            notes = "특정 지역에 속한 숙소 목록을 조회한다.",
            produces = "application/json",
            response = RoomCardResponse.class
    )
    @GetMapping("/region")
    public ResponseEntity<List<RoomCardResponse>> getRoomsByRegionId(@RequestParam Long id) {
        // TODO
        return null;
    }

    @GetMapping("/within")
    public ResponseEntity<List<RoomCardResponse>> getRoomsWithinCircle(Geometry geometry) {
        return ResponseEntity.ok(roomService.getRoomsWithinCircle(geometry));
    }

    @ApiOperation(
            value = "1박 숙박 가격대별 빈도값 조회",
            notes = "1박 숙박 가격대별 빈도값들을 조회한다.",
            produces = "application/json",
            response = PricesResponse.class
    )
    @GetMapping("/prices")
    public ResponseEntity<PricesResponse> getPrices() {
        return null;
    }
}
