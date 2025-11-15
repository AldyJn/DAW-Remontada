package com.tecsup.backend_spring.controller;

import com.tecsup.backend_spring.model.Categoria;
import com.tecsup.backend_spring.model.Producto;
import com.tecsup.backend_spring.repository.CategoriaRepository;
import com.tecsup.backend_spring.repository.ProductoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoRepository prodRepo;
    private final CategoriaRepository catRepo;

    public ProductoController(ProductoRepository prodRepo, CategoriaRepository catRepo) {
        this.prodRepo = prodRepo;
        this.catRepo = catRepo;
    }

    @GetMapping
    public List<Producto> listarProductos() {
        return prodRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        return prodRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        if (producto.getCategoria() != null && producto.getCategoria().getId() != null) {
            Categoria categoria = catRepo.findById(producto.getCategoria().getId()).orElse(null);
            producto.setCategoria(categoria);
        }

        Producto nuevo = prodRepo.save(producto);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return prodRepo.findById(id)
                .map(pExistente -> {
                    pExistente.setNombre(producto.getNombre());
                    pExistente.setPrecio(producto.getPrecio());
                    pExistente.setStock(producto.getStock());

                    if (producto.getCategoria() != null && producto.getCategoria().getId() != null) {
                        Categoria categoria = catRepo.findById(producto.getCategoria().getId()).orElse(null);
                        pExistente.setCategoria(categoria);
                    }

                    Producto actualizado = prodRepo.save(pExistente);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        return prodRepo.findById(id)
                .map(p -> {
                    prodRepo.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
