package com.team14.cherrybnb.openapi;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        return ResponseEntity.ok(distanceInfoResponses);
    }


}
