package com.fg.grow_control.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.fg.grow_control.dto.DeviceConfigurationRequestDTO;
import com.fg.grow_control.entity.DeviceType;
import com.fg.grow_control.service.ArduinoCodeTemplateService;

@RestController
@RequestMapping("/device-configuration")
@PreAuthorize("permitAll()")
public class DeviceConfigurationController {

    @Autowired
    private ArduinoCodeTemplateService arduinoCodeTemplateService;

    @PostMapping("/generate")
    public ResponseEntity<String> getConfigurationForDevices(@RequestBody DeviceConfigurationRequestDTO request) { 
        StringBuilder finalCode = new StringBuilder();

        // Obtiene el GrowCycleID y la lista de dispositivos
        Long growCycleId = request.getGrowCycleId();
        List<DeviceType> devices = request.getDevices();

        // Itera sobre la lista de dispositivos y genera el código para cada uno
        for (DeviceType deviceType : devices) {
            String code = arduinoCodeTemplateService.generateCodeForDeviceType(deviceType, growCycleId);
            finalCode.append(code).append("\n\n"); // Concatenar el código de cada dispositivo
        }

        return ResponseEntity.ok(finalCode.toString());
    }
}
