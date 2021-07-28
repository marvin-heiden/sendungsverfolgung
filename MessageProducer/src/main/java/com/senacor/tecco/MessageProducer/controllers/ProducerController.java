package com.senacor.tecco.MessageProducer.controllers;

import com.senacor.tecco.MessageProducer.services.ProducerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/producer")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @PostMapping
    @ApiOperation(value = "Creates a new random event message", notes = "Produce a valid event message and send it to the input topic", nickname = "sendSingleMessage")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Event message has been created. "),
            @ApiResponse(code = 400, message = "The request is not valid"),
            @ApiResponse(code = 401, message = "Authentication failed"),
            @ApiResponse(code = 403, message = "Not enough rights to access this resource"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    void sendSingleMessage(){
        producerService.sendSingleMessage();
    }

    @GetMapping
    @ApiOperation(value = "Creates a number of new random event message", notes = "Produce valid event messages and send them to the input topic", nickname = "sendMultipleMessages")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Event messages have been created. "),
            @ApiResponse(code = 400, message = "The request is not valid"),
            @ApiResponse(code = 401, message = "Authentication failed"),
            @ApiResponse(code = 403, message = "Not enough rights to access this resource"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    void sendMultipleMessages(){
        // TODO
        producerService.sendMessageSeries();
    }
}
