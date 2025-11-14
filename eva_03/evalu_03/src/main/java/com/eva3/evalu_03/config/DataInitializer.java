package com.eva3.evalu_03.config;

import com.eva3.evalu_03.entity.*;
import com.eva3.evalu_03.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final MesaRepository mesaRepository;
    private final PlatoRepository platoRepository;
    private final InsumoRepository insumoRepository;
    private final ClienteRepository clienteRepository;
    private final ProveedorRepository proveedorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("Iniciando carga de datos iniciales...");

        // Crear roles si no existen
        crearRoles();

        // Crear usuarios de prueba
        crearUsuarios();

        // Crear mesas
        crearMesas();

        // Crear platos
        crearPlatos();

        // Crear insumos
        crearInsumos();

        // Crear clientes
        crearClientes();

        // Crear proveedores
        crearProveedores();

        log.info("Carga de datos iniciales completada!");
    }

    private void crearRoles() {
        if (rolRepository.count() == 0) {
            rolRepository.save(new Rol("ROLE_ADMIN"));
            rolRepository.save(new Rol("ROLE_MOZO"));
            rolRepository.save(new Rol("ROLE_COCINERO"));
            rolRepository.save(new Rol("ROLE_CAJERO"));
            log.info("Roles creados");
        }
    }

    private void crearUsuarios() {
        if (usuarioRepository.count() == 0) {
            // Admin
            Usuario admin = new Usuario();
            admin.setNombreUsuario("admin");
            admin.setContrasena(passwordEncoder.encode("password123"));
            admin.setEstado(true);
            Set<Rol> rolesAdmin = new HashSet<>();
            rolesAdmin.add(rolRepository.findByNombre("ROLE_ADMIN").orElseThrow());
            admin.setRoles(rolesAdmin);
            usuarioRepository.save(admin);

            // Mozo
            Usuario mozo = new Usuario();
            mozo.setNombreUsuario("mozo1");
            mozo.setContrasena(passwordEncoder.encode("password123"));
            mozo.setEstado(true);
            Set<Rol> rolesMozo = new HashSet<>();
            rolesMozo.add(rolRepository.findByNombre("ROLE_MOZO").orElseThrow());
            mozo.setRoles(rolesMozo);
            usuarioRepository.save(mozo);

            // Cocinero
            Usuario cocinero = new Usuario();
            cocinero.setNombreUsuario("cocinero1");
            cocinero.setContrasena(passwordEncoder.encode("password123"));
            cocinero.setEstado(true);
            Set<Rol> rolesCocinero = new HashSet<>();
            rolesCocinero.add(rolRepository.findByNombre("ROLE_COCINERO").orElseThrow());
            cocinero.setRoles(rolesCocinero);
            usuarioRepository.save(cocinero);

            // Cajero
            Usuario cajero = new Usuario();
            cajero.setNombreUsuario("cajero1");
            cajero.setContrasena(passwordEncoder.encode("password123"));
            cajero.setEstado(true);
            Set<Rol> rolesCajero = new HashSet<>();
            rolesCajero.add(rolRepository.findByNombre("ROLE_CAJERO").orElseThrow());
            cajero.setRoles(rolesCajero);
            usuarioRepository.save(cajero);

            log.info("Usuarios creados");
        }
    }

    private void crearMesas() {
        if (mesaRepository.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                Mesa mesa = new Mesa();
                mesa.setNumero(i);
                mesa.setCapacidad(i <= 2 ? 2 : i <= 4 ? 4 : i <= 6 ? 6 : 8);
                mesa.setEstado(Mesa.EstadoMesa.DISPONIBLE);
                mesaRepository.save(mesa);
            }
            log.info("Mesas creadas");
        }
    }

    private void crearPlatos() {
        if (platoRepository.count() == 0) {
            // Entradas
            platoRepository.save(crearPlato("Ensalada César", Plato.TipoPlato.ENTRADA, 18.00,
                    "Lechuga fresca, pollo grillado, crutones y aderezo césar"));
            platoRepository.save(crearPlato("Tequeños", Plato.TipoPlato.ENTRADA, 15.00,
                    "6 unidades de tequeños rellenos de queso"));
            platoRepository.save(crearPlato("Causa Limeña", Plato.TipoPlato.ENTRADA, 20.00,
                    "Causa rellena de pollo o atún"));

            // Fondos
            platoRepository.save(crearPlato("Lomo Saltado", Plato.TipoPlato.FONDO, 35.00,
                    "Lomo fino salteado con cebolla, tomate y papas fritas"));
            platoRepository.save(crearPlato("Arroz con Pollo", Plato.TipoPlato.FONDO, 28.00,
                    "Arroz con pollo, papa a la huancaína y ensalada"));
            platoRepository.save(crearPlato("Tallarines a lo Alfredo", Plato.TipoPlato.FONDO, 30.00,
                    "Tallarines en salsa alfredo con pollo o carne"));
            platoRepository.save(crearPlato("Ceviche Mixto", Plato.TipoPlato.FONDO, 38.00,
                    "Pescado, camarones y pulpo en leche de tigre"));
            platoRepository.save(crearPlato("Ají de Gallina", Plato.TipoPlato.FONDO, 32.00,
                    "Pollo deshilachado en salsa de ají amarillo"));

            // Postres
            platoRepository.save(crearPlato("Suspiro Limeño", Plato.TipoPlato.POSTRE, 12.00,
                    "Postre tradicional peruano con manjar blanco"));
            platoRepository.save(crearPlato("Tres Leches", Plato.TipoPlato.POSTRE, 14.00,
                    "Torta bañada en tres leches"));
            platoRepository.save(crearPlato("Tiramisu", Plato.TipoPlato.POSTRE, 16.00,
                    "Postre italiano con café y mascarpone"));

            // Bebidas
            platoRepository.save(crearPlato("Chicha Morada", Plato.TipoPlato.BEBIDA, 8.00,
                    "Bebida tradicional peruana - Jarra 1L"));
            platoRepository.save(crearPlato("Limonada", Plato.TipoPlato.BEBIDA, 7.00,
                    "Limonada natural - Jarra 1L"));
            platoRepository.save(crearPlato("Inca Kola", Plato.TipoPlato.BEBIDA, 5.00,
                    "Gaseosa 500ml"));
            platoRepository.save(crearPlato("Agua Mineral", Plato.TipoPlato.BEBIDA, 4.00,
                    "Agua San Luis 625ml"));

            log.info("Platos creados");
        }
    }

    private Plato crearPlato(String nombre, Plato.TipoPlato tipo, double precio, String descripcion) {
        Plato plato = new Plato();
        plato.setNombre(nombre);
        plato.setTipo(tipo);
        plato.setPrecio(BigDecimal.valueOf(precio));
        plato.setDescripcion(descripcion);
        plato.setEstado(true);
        return plato;
    }

    private void crearInsumos() {
        if (insumoRepository.count() == 0) {
            insumoRepository.save(crearInsumo("Arroz Blanco", "kg", 100.00, 20.00, 3.50));
            insumoRepository.save(crearInsumo("Pollo Entero", "kg", 50.00, 10.00, 12.00));
            insumoRepository.save(crearInsumo("Lomo de Res", "kg", 30.00, 5.00, 28.00));
            insumoRepository.save(crearInsumo("Fideos Tallarín", "kg", 40.00, 10.00, 4.50));
            insumoRepository.save(crearInsumo("Papa Blanca", "kg", 80.00, 15.00, 2.00));
            insumoRepository.save(crearInsumo("Cebolla Roja", "kg", 30.00, 10.00, 2.50));
            insumoRepository.save(crearInsumo("Tomate", "kg", 25.00, 8.00, 3.00));
            insumoRepository.save(crearInsumo("Lechuga", "unidad", 20.00, 5.00, 1.50));
            insumoRepository.save(crearInsumo("Queso Fresco", "kg", 15.00, 5.00, 18.00));
            insumoRepository.save(crearInsumo("Leche Evaporada", "lata", 30.00, 10.00, 4.20));
            insumoRepository.save(crearInsumo("Aceite Vegetal", "litro", 20.00, 5.00, 8.50));
            insumoRepository.save(crearInsumo("Sal", "kg", 25.00, 5.00, 1.00));

            log.info("Insumos creados");
        }
    }

    private Insumo crearInsumo(String nombre, String unidad, double stock, double stockMin, double precio) {
        Insumo insumo = new Insumo();
        insumo.setNombre(nombre);
        insumo.setUnidadMedida(unidad);
        insumo.setStock(BigDecimal.valueOf(stock));
        insumo.setStockMinimo(BigDecimal.valueOf(stockMin));
        insumo.setPrecioCompra(BigDecimal.valueOf(precio));
        insumo.setEstado(true);
        return insumo;
    }

    private void crearClientes() {
        if (clienteRepository.count() == 0) {
            clienteRepository.save(crearCliente("72345678", "Juan Carlos", "Pérez García",
                    "987654321", "jperez@email.com"));
            clienteRepository.save(crearCliente("71234567", "María Elena", "Rodríguez López",
                    "976543210", "mrodriguez@email.com"));
            clienteRepository.save(crearCliente("73456789", "Pedro Luis", "González Torres",
                    "965432109", "pgonzalez@email.com"));
            clienteRepository.save(crearCliente("74567890", "Ana Sofía", "Martínez Flores",
                    "954321098", "amartinez@email.com"));
            clienteRepository.save(crearCliente("75678901", "Carlos Alberto", "Sánchez Quispe",
                    "943210987", "csanchez@email.com"));

            log.info("Clientes creados");
        }
    }

    private Cliente crearCliente(String dni, String nombres, String apellidos, String telefono, String correo) {
        Cliente cliente = new Cliente();
        cliente.setDni(dni);
        cliente.setNombres(nombres);
        cliente.setApellidos(apellidos);
        cliente.setTelefono(telefono);
        cliente.setCorreo(correo);
        cliente.setEstado(true);
        return cliente;
    }

    private void crearProveedores() {
        if (proveedorRepository.count() == 0) {
            proveedorRepository.save(crearProveedor("20123456789", "Distribuidora de Alimentos SAC",
                    "014567890", "ventas@alimentos.com", "Av. Los Alimentos 123, Lima"));
            proveedorRepository.save(crearProveedor("20234567890", "Carnes y Aves del Peru EIRL",
                    "014567891", "ventas@carnesyaves.com", "Jr. Las Carnes 456, Lima"));
            proveedorRepository.save(crearProveedor("20345678901", "Verduras Frescas SAC",
                    "014567892", "ventas@verduras.com", "Av. Agricultura 789, Lima"));

            log.info("Proveedores creados");
        }
    }

    private Proveedor crearProveedor(String ruc, String nombre, String telefono, String correo, String direccion) {
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc(ruc);
        proveedor.setNombre(nombre);
        proveedor.setTelefono(telefono);
        proveedor.setCorreo(correo);
        proveedor.setDireccion(direccion);
        return proveedor;
    }
}
