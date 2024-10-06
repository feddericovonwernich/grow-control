package com.fg.grow_control.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fg.grow_control.entity.CodeTemplate;
import com.fg.grow_control.entity.DeviceType;
import com.fg.grow_control.entity.GrowCycleSettingValue;
import com.fg.grow_control.repository.CodeTemplateRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CodeTemplateService extends BasicService<CodeTemplate, Long, CodeTemplateRepository> {

    public CodeTemplateService(CodeTemplateRepository repository) {
        super(repository);
    }

    @Autowired
    private CodeTemplateRepository codeTemplateRepository;

    @Autowired
    private GrowCycleSettingValueService growCycleSettingValueService;

    public String generateCodeForDeviceType(DeviceType deviceType, Long growCycleId) throws EntityNotFoundException {
        // Busca el template para este tipo de dispositivo
        CodeTemplate codeTemplate = codeTemplateRepository.findByDeviceType(deviceType)
                .orElseThrow(() -> new EntityNotFoundException("No template found for device type: " + deviceType.getName()));

        // Obtiene el template inicial
        String generatedCode = codeTemplate.getTemplate();
        // Reemplaza las variables del template con los valores de configuración
        for (String var : codeTemplate.getTemplateVars()) {
            // Busca el valor de configuración en base al nombre de la variable y al GrowCycleID
            GrowCycleSettingValue growCycleSettingValue = growCycleSettingValueService.findByGrowCycleIdAndSettingName(growCycleId, var)
                    .orElseThrow(() -> new EntityNotFoundException("No configuration found for variable: " + var + " in GrowCycle ID: " + growCycleId));

            // Reemplaza la variable en el template con el valor real obtenido
            generatedCode = generatedCode.replace("${" + var + "}", growCycleSettingValue.getValue());
        }
        return generatedCode;
    }
    
    public String generateCodeForDevices(List<DeviceType> devices, Long growCycleId) throws EntityNotFoundException {
        StringBuilder finalCode = new StringBuilder();

        // Itera sobre la lista de dispositivos y genera el código para cada uno
        for (DeviceType deviceType : devices) {
            String code = generateCodeForDeviceType(deviceType, growCycleId);
            finalCode.append(code).append("\n\n"); // Concatenar el código de cada dispositivo
        }

        return finalCode.toString();  // Devuelve el código concatenado
    }
}