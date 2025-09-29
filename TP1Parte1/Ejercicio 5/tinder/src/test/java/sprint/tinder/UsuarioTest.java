package sprint.tinder;

import org.junit.jupiter.api.*;
import sprint.tinder.entities.Usuario;
import sprint.tinder.entities.Zona;
import sprint.tinder.errors.ErrorServicio;
import sprint.tinder.services.UsuarioServicio;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioTest {

    private static UsuarioServicio usuarioServicio;
    private static Zona zonaTest;

    @BeforeAll
    public static void setup() {
        System.out.println("Iniciando tests de Usuario");
        usuarioServicio = new UsuarioServicio();
        // Creamos una zona de prueba
        zonaTest = new Zona();
        zonaTest.setId("1");
        zonaTest.setNombre("Zona Test");
    }

    @Test
    @Order(1)
    public void testValidarUsuarioCorrecto() {
        System.out.println("Validacion de usuario");
        assertDoesNotThrow(() -> {
            usuarioServicio.validar("Martina", "Nahman", "martina@mail.com", "123456", "123456", zonaTest);
        });
    }

    @Test
    @Order(2)
    public void testValidarUsuarioSinNombre() {
        System.out.println("Validar usuario sin nombre");
        Exception ex = assertThrows(ErrorServicio.class, () -> {
            usuarioServicio.validar("", "Nahman", "martina@mail.com", "123456", "123456", zonaTest);
        });
        assertEquals("El nombre es obligatorio", ex.getMessage());
    }

    @Test
    @Order(3)
    public void testValidarUsuarioClavesDiferentes() {
        System.out.println("Validar usuario claves diferentes");
        Exception ex = assertThrows(ErrorServicio.class, () -> {
            usuarioServicio.validar("Martina", "Nahman", "martina@mail.com", "123456", "123457", zonaTest);
        });
        assertEquals("Las claves deben coincidir", ex.getMessage());
    }

    @Test
    @Order(4)
    public void testLoginSinEmail() {
        System.out.println("Validar login sin email");
        Exception ex = assertThrows(ErrorServicio.class, () -> {
            usuarioServicio.login("", "123456");
        });
        assertEquals("Debe indicar el usuario", ex.getMessage());
    }

    @Test
    @Order(5)
    public void testLoginSinClave() {
        System.out.println("Validar login sin clave");
        Exception ex = assertThrows(ErrorServicio.class, () -> {
            usuarioServicio.login("martina@mail.com", "");
        });
        assertEquals("Debe indicar la clave", ex.getMessage());
    }

//    @Test
//    @Order(6)
//    public void testHabilitarDeshabilitar() {
//        Usuario u = new Usuario();
//        u.setId("123");
//        u.setAlta(new Date());
//
//        assertDoesNotThrow(() -> {
//            usuarioServicio.deshabilitar("123"); // Debe setear fecha de baja
//            usuarioServicio.habilitar("123");   // Debe limpiar fecha de baja
//        });
//    }
}
