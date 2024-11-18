package com.fg.grow_control.controller;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.dto.DeviceConfigurationRequestDTO;
import com.fg.grow_control.entity.*;
import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.entity.schedule.RangeSchedule;
import com.fg.grow_control.entity.schedule.ScheduleType;
import com.fg.grow_control.repository.*;
import com.fg.grow_control.service.CodeTemplateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fg.grow_control.service.GrowCycleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.Commit;
import java.util.List;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class DeviceConfigurationControllerIntegrationTest extends BasicApplicationintegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    @Autowired
    private CodeTemplateService codeTemplateService;

    @Autowired
    private GrowCycleSettingValueRepository growCycleSettingValueRepository;

    @Autowired
    private GrowCycleService growCycleService;

    @Autowired
    private SettingRepository settingRepository;

    @Test
    @Commit
    public void testGenerateCodeForDevices() throws Exception {
        // Crear entidades de prueba para DeviceType y CodeTemplate
        DeviceType device1 = deviceTypeRepository.save(DeviceType.builder()
            .name("Sensor de Temperatura")
            .build());

        DeviceType device2 = deviceTypeRepository.save(DeviceType.builder()
            .name("Sensor de Humedad")
            .build());

        codeTemplateService.createOrUpdate(CodeTemplate.builder()
             .deviceType(device1)
             .template("Codigo para Sensor de Temperatura: ${tempValue}")
             .templateVars(List.of("tempValue"))
             .build());

        codeTemplateService.createOrUpdate(CodeTemplate.builder()
             .deviceType(device2)
             .template("Codigo para Sensor de Humedad: ${humidityValue}")
             .templateVars(List.of("humidityValue"))
             .build());

        SimpleTimestamp simpleTimestampInit = SimpleTimestamp.builder()
                .day(24)
                .month(9)
                .year(2024)
                .hour(10)
                .minutes(30)
                .seconds(45)
                .build();

        SimpleTimestamp simpleTimestampFinal = SimpleTimestamp.builder()
                .day(28)
                .month(10)
                .year(2025)
                .hour(10)
                .minutes(30)
                .seconds(45)
                .build();

        RangeSchedule rangeSchedule = RangeSchedule.builder()
                 .type(ScheduleType.FIXED) // o ScheduleType.RELATIVE
                 .start(simpleTimestampInit)
                 .end(simpleTimestampFinal)
                 .build();

        // Crear y guardar GrowCycle y Setting para los valores de reemplazo
        GrowCycle growCycle = growCycleService.createOrUpdate(GrowCycle.builder()
                .description("Ciclo de Crecimiento 1")
                .rangeSchedule(rangeSchedule)
                .build()
        );

        Setting tempSetting = settingRepository.save(
            Setting.builder()
                .name("tempValue")
                .build()
        );

        Setting humiditySetting = settingRepository.save(
            Setting.builder()
                .name("humidityValue")
                .build()
        );

        // Crear y guardar GrowCycleSettingValues para asociar los valores de configuración
        growCycleSettingValueRepository.save(GrowCycleSettingValue.builder()
            .growCycle(growCycle)
            .setting(tempSetting)
            .value("25°C")
            .build());

        growCycleSettingValueRepository.save(GrowCycleSettingValue.builder()
            .growCycle(growCycle)
            .setting(humiditySetting)
            .value("60%")
            .build());

        // Crear el DTO de solicitud
        DeviceConfigurationRequestDTO requestDTO = DeviceConfigurationRequestDTO.builder()
            .growCycleId(growCycle.getId())
            .devices(List.of(device1, device2))
            .build();

        // Convertir el requestDTO a JSON
        String jsonRequest = objectMapper.writeValueAsString(requestDTO);

        // Configurar encabezados de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crear HttpEntity con la solicitud JSON y los encabezados
        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

        // Hacer una solicitud POST al endpoint de generación de código
        ResponseEntity<String> response = restTemplate.exchange(
            createURLWithPort("/deviceConfiguration/generate"),
            HttpMethod.POST, entity, String.class);

        // Verificar que la respuesta sea 200 OK
        assertEquals(200, response.getStatusCodeValue());

        // Validar que el cuerpo de la respuesta contiene los valores de reemplazo
        String expectedCodeForDevice1 = "Codigo para Sensor de Temperatura: 25°C";
        String expectedCodeForDevice2 = "Codigo para Sensor de Humedad: 60%";

        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains(expectedCodeForDevice1));
        assertTrue(response.getBody().contains(expectedCodeForDevice2));
    }
}