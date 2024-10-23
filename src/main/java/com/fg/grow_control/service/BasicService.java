package com.fg.grow_control.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class BasicService<T, ID, R extends JpaRepository<T, ID>> {

    protected final R repository;

    public BasicService(R repository) {
        this.repository = repository;
    }

    @Transactional
    public T createOrUpdate(T object) {
        return repository.save(object);
    }

    public T getById(ID id) throws EntityNotFoundException {
        Optional<T> response = repository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new EntityNotFoundException("Couldn't find entity with ID: " + id);
        }
    }

    public Page<T> getAll(Pageable pageable) {
        return repository.findAll(pageable); // Usar el método paginado del repositorio
    }
    @Transactional
    public void deleteById(ID id) throws EntityNotFoundException {
        Optional<T> response = repository.findById(id);
        if (response.isPresent()) {
            repository.delete(response.get());
        } else {
            throw new EntityNotFoundException("Couldn't delete entity with ID: " + id);
        }
    }

}
