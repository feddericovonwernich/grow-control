package com.fg.grow_control.controller;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.entity.RoleEntity;
import com.fg.grow_control.entity.UserEntity;
import com.fg.grow_control.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

public class UserEntityControllerIntegrationTest extends BasicApplicationintegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetUserByIdNotFound() {
        // ID que no existe
        Long nonExistentId = 9999L;

        // Crear la solicitud HTTP
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // Realizar la solicitud
        String url = this.createURLWithPort("/users/" + nonExistentId);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // Verificaciones
        Assertions.assertEquals(404, response.getStatusCodeValue(), "El código de estado HTTP no es 404");
        String expectedMessage = "Couldn't find entity with ID: " + nonExistentId;
        Assertions.assertTrue(response.getBody().contains(expectedMessage),
                "El cuerpo de la respuesta no contiene el mensaje esperado.");
    }

    @Test
    public void testGetUserByIdSuccess() {
        // Crear roles
        RoleEntity role = RoleEntity.builder()
                .roleName("ROLE_USER")
                .permissionList(new HashSet<>()) // No se asignan permisos por simplicidad
                .build();
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(role);

        // Crear usuario
        UserEntity user = UserEntity.builder()
                .username("testuser")
                .password("securepassword")
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roles(roles)
                .build();
        userService.createOrUpdate(user);

        // Crear la solicitud HTTP
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // Realizar la solicitud
        String url = this.createURLWithPort("/users/" + user.getId());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // Verificaciones
        Assertions.assertEquals(200, response.getStatusCodeValue(), "El código de estado HTTP no es 200");
        Assertions.assertTrue(response.getBody().contains("\"id\":" + user.getId()), "El cuerpo no contiene el ID del usuario");
        Assertions.assertTrue(response.getBody().contains("\"username\":\"testuser\""), "El cuerpo no contiene el username del usuario");
        Assertions.assertTrue(response.getBody().contains("\"isEnabled\":true"), "El cuerpo no contiene el estado del usuario");
        Assertions.assertTrue(response.getBody().contains("\"roleName\":\"ROLE_USER\""), "El cuerpo no contiene el nombre del rol asignado");
    }
}