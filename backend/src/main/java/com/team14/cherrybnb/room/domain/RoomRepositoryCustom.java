package com.team14.cherrybnb.room.domain;

import com.team14.cherrybnb.room.dto.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomRepositoryCustom {

    Page<Room> findBySearchCondition(SearchCondition searchCondition, Pageable pageable);
}
