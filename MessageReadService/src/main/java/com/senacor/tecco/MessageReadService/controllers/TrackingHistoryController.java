package com.senacor.tecco.MessageReadService.controllers;

import com.senacor.tecco.MessageReadService.models.Message;
import com.senacor.tecco.MessageReadService.services.TrackingService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@RestController
@RequestMapping(value = "/tracking")
@RequiredArgsConstructor
public class TrackingHistoryController {

    private final TrackingService trackingService;

    @GetMapping
    @ApiOperation(value = "Returns a list of tracking events", notes = "Tracking history connected to a specific tracking number", nickname = "getTrackingHistory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of unordered tracking Events "),
            @ApiResponse(code = 400, message = "The request is not valid"),
            @ApiResponse(code = 401, message = "Authentication failed"),
            @ApiResponse(code = 403, message = "Not enough rights to access this resource"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    List<Message> getTrackingHistory(@RequestParam(value = "trackingNumber") String trackingNumber){
        // Query Storage Topic for trackingNumber
        return trackingService.getAllAssociatedMessages(trackingNumber);
    }
}
