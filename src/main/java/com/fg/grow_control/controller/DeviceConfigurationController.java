package com.fg.grow_control.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.fg.grow_control.dto.DeviceConfigurationRequestDTO;
import com.fg.grow_control.entity.DeviceType;
import com.fg.grow_control.service.CodeTemplateService;

@RestController
@RequestMapping("/deviceConfiguration")
@PreAuthorize("permitAll()")
public class DeviceConfigurationController {

    @Autowired
    private CodeTemplateService codeTemplateService;

    @PostMapping("/generate")
    public ResponseEntity<String> getConfigurationForDevices(@RequestBody DeviceConfigurationRequestDTO request) { 

    // Obtiene el GrowCycleID y la lista de dispositivos desde el DTO
    Long growCycleId = request.getGrowCycleId();
    List<DeviceType> devices = request.getDevices();

    // Llama al servicio para generar el código
    String finalCode = codeTemplateService.generateCodeForDevices(devices, growCycleId);
        
    return ResponseEntity.ok(finalCode);
    }
}
