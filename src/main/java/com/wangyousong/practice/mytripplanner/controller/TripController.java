package com.wangyousong.practice.mytripplanner.controller;

import com.wangyousong.practice.mytripplanner.dto.TripPlanningResponse;
import com.wangyousong.practice.mytripplanner.dto.TripRequest;
import com.wangyousong.practice.mytripplanner.sevice.impl.TripPlanningOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripPlanningOrchestrator orchestrator;

    @PostMapping("/plan")
    public ResponseEntity<TripPlanningResponse> plan(@RequestBody TripRequest request) {
        TripPlanningResponse response = orchestrator.plan(request);
        return ResponseEntity.ok(response);
    }

}
