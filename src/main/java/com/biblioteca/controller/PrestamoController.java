package com.biblioteca.controller;

import com.biblioteca.entity.Prestamo;
import com.biblioteca.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping
    public List<Prestamo> list() {
        return prestamoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> getById(@PathVariable Long id) {
        return prestamoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public static class PrestamoRequest {
        public Long libroId;
        public Long usuarioId;

        public Long getLibroId() { return libroId; }
        public Long getUsuarioId() { return usuarioId; }
    }

    @PostMapping
    public ResponseEntity<Prestamo> create(@RequestBody PrestamoRequest req) {
        Prestamo saved = prestamoService.crearPrestamo(req.getLibroId(), req.getUsuarioId());
        return ResponseEntity.created(URI.create("/api/prestamos/" + saved.getId())).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        prestamoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
