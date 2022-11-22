package com.team14.cherrybnb.room.application;

import com.team14.cherrybnb.common.exception.BusinessException;
import com.team14.cherrybnb.room.domain.Room;
import com.team14.cherrybnb.room.domain.RoomRepository;
import com.team14.cherrybnb.room.dto.RoomCardResponse;
import com.team14.cherrybnb.room.dto.RoomDetailResponse;
import com.team14.cherrybnb.room.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.team14.cherrybnb.common.exception.ErrorCode.NO_SUCH_ROOM;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public Page<RoomCardResponse> getRoomsBySearchCondition(SearchCondition searchCondition, Pageable pageable) {
        Page<Room> rooms = roomRepository.findBySearchCondition(searchCondition, pageable);

        return rooms.map(room -> new RoomCardResponse(room, false));
    }

    @Transactional(readOnly = true)
    public List<RoomCardResponse> getRoomsWithinCircle(Geometry circle) {
        return roomRepository.findRoomsWithinCircle(circle)
                .stream()
                .map(room -> new RoomCardResponse(room, false))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RoomDetailResponse getRoomDetail(Long roomId) {
        Room room = roomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new BusinessException(NO_SUCH_ROOM));

        return new RoomDetailResponse(room, false);
    }
}
