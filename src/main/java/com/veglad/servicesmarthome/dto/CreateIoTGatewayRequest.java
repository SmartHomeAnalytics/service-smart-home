package com.veglad.servicesmarthome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateIoTGatewayRequest {

    @NotNull
    private String algorithm;

    @NotNull
    private String gatewayId;

    @NotNull
    private String certificateFilePath;
}
