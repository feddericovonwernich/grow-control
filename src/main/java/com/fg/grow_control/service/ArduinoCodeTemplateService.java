package com.fg.grow_control.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fg.grow_control.entity.ArduinoCodeTemplate;
import com.fg.grow_control.entity.DeviceType;
import com.fg.grow_control.entity.GrowCycleSetting;
import com.fg.grow_control.repository.ArduinoCodeTemplateRepository;

@Service
public class ArduinoCodeTemplateService extends BasicService<ArduinoCodeTemplate, Long, ArduinoCodeTemplateRepository> {

    public ArduinoCodeTemplateService(ArduinoCodeTemplateRepository repository) {
        super(repository);
    }

    @Autowired
    private ArduinoCodeTemplateRepository arduinoCodeTemplateRepository;

    @Autowired
    private GrowCycleSettingService growCycleSettingService;  // Servicio para gestionar GrowCycleSetting

    public String generateCodeForDeviceType(DeviceType deviceType, Long growCycleId) {
        // Busca la plantilla para este tipo de dispositivo
        ArduinoCodeTemplate arduinoCodeTemplate = arduinoCodeTemplateRepository.findByDeviceType(deviceType)
                .orElseThrow(() -> new RuntimeException("No template found for device type"));

        // Obtiene el template inicial
        String generatedCode = arduinoCodeTemplate.getTemplate();
        // Reemplaza las variables del template con los valores de configuración
        for (String var : arduinoCodeTemplate.getTemplateVars()) {
            // Busca el valor de configuración en base al nombre de la variable y al GrowCycleID
            GrowCycleSetting growCycleSetting = growCycleSettingService.findByGrowCycleIdAndSettingName(growCycleId, var)
                    .orElseThrow(() -> new RuntimeException("No configuration found for variable: " + var));

            // Reemplaza la variable en el template con el valor real obtenido
            generatedCode = generatedCode.replace("${" + var + "}", growCycleSetting.getValue());
        }
        return generatedCode;
    }    
}