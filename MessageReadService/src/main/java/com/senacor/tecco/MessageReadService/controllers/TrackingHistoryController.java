package com.senacor.tecco.MessageReadService.controllers;

import com.senacor.tecco.MessageReadService.exception.ApiNoContentException;
import com.senacor.tecco.MessageReadService.models.TrackingHistoryShort;
import com.senacor.tecco.MessageReadService.services.TrackingService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/tracking")
@RequiredArgsConstructor
@Log4j2
public class TrackingHistoryController {

    private final TrackingService trackingService;

    @GetMapping("/")
    @ApiOperation(value = "Returns a list of tracking events", notes = "Tracking history connected to a specific tracking number", nickname = "getTrackingHistory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sendungsverlauf wurde gefunden."),
            @ApiResponse(code = 404, message = "Keine Sendungen zur angegebenen Sendungsnummer gefunden.")
    })
    TrackingHistoryShort getTrackingHistory(@RequestParam(value = "trackingNumber") String trackingNumber) {
        // Query Storage Topic for trackingNumber
        TrackingHistoryShort history = trackingService.getShortTrackingHistoryByTrackingNumber(trackingNumber);
        log.info(history);
        if (history == null)
            throw new ApiNoContentException("Keine Sendungen zur angegebenen Sendungsnummer gefunden.");
        else return history;
    }
}
