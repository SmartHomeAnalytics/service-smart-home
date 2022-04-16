package com.veglad.servicesmarthome.controller;

import com.veglad.servicesmarthome.dto.CreateIoTGatewayRequest;
import com.veglad.servicesmarthome.service.IotCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/iot")
public class IotController {

    private final IotCoreService ioTCoreService;

    public IotController(IotCoreService ioTCoreService) {
        this.ioTCoreService = ioTCoreService;
    }

    @PostMapping("/gateway")
    public void createIoTGateway(@RequestBody @Valid CreateIoTGatewayRequest createIoTGatewayRequest) {
        try {
            ioTCoreService.createIoTGateway(
                    createIoTGatewayRequest.getAlgorithm(),
                    createIoTGatewayRequest.getGatewayId(),
                    createIoTGatewayRequest.getCertificateFilePath()
            );
        } catch (Exception e) {
            log.error("Error while creating IoT gateway with data: {}", createIoTGatewayRequest.toString(), e);
        }
    }
}
