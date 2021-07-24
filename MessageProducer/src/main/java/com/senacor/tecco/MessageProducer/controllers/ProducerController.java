package com.senacor.tecco.MessageProducer.controllers;

import com.senacor.tecco.MessageProducer.services.ProducerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tracking")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @GetMapping
    @ApiOperation(value = "Returns a list of tracking events", notes = "Tracking history connected to a specific tracking number", nickname = "getTrackingHistory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of unordered tracking Events "),
            @ApiResponse(code = 400, message = "The request is not valid"),
            @ApiResponse(code = 401, message = "Authentication failed"),
            @ApiResponse(code = 403, message = "Not enough rights to access this resource"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    void sendSingleMessage(){
        producerService.sendSingleMessage();
    }
}
