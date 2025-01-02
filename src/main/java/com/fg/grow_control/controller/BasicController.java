package com.fg.grow_control.controller;

import com.fg.grow_control.service.BasicService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class BasicController<T, ID, R extends JpaRepository<T, ID>, S extends BasicService<T, ID, R>> {

    @Autowired
    protected final S service;

    public BasicController(S service) {
        this.service = service;
    }

    @Value("${pagination.default-page}")
    public int defaultPage;

    @Value("${pagination.default-size}")
    public int defaultSize;

    @GetMapping
    public ResponseEntity<Page<T>> getAll(@RequestParam(required = false) Integer page,
                                          @RequestParam(required = false) Integer size) {
        int pageNumber = (page != null) ? page : defaultPage;
        int pageSize = (size != null) ? size : defaultSize;

        Page<T> entities = service.getAll(pageNumber, pageSize);

        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable ID id) {
        T entity = service.getById(id);
        if (entity != null) {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<T> createOrUpdate(@RequestBody T entity) {
        T createdEntity = service.createOrUpdate(entity);
        return new ResponseEntity<>(createdEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
