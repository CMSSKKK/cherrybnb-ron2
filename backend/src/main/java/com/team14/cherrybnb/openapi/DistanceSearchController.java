package com.team14.cherrybnb.openapi;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.team14.cherrybnb.openapi.dummy.DistanceInfoResponse;
import com.team14.cherrybnb.openapi.dummy.Position;
import com.team14.cherrybnb.openapi.kakao.DistanceSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/distance")
@Slf4j
public class DistanceSearchController {

    private final DistanceSearchService distanceSearchService;

    public DistanceSearchController(DistanceSearchService distanceSearchService) {
        this.distanceSearchService = distanceSearchService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<DistanceInfoResponse>> getDurations(Position position) throws JsonProcessingException {
        log.info("position={}, {}", position.getX(), position.getY());
        List<DistanceInfoResponse> distanceInfoResponses = distanceSearchService.searchDistrictInfo(position);
        log.info("distanceinfo={}", distanceInfoResponses);
        return ResponseEntity.ok(distanceInfoResponses);
    }


}
