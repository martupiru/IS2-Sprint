package sprint.tinder;

import org.junit.jupiter.api.*;
import sprint.tinder.entities.Foto;
import sprint.tinder.entities.Usuario;
import sprint.tinder.enumerations.Sexo;
import sprint.tinder.enumerations.Tipo;
import sprint.tinder.entities.Mascota;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MascotaTest {

    private Mascota mascota;

    @BeforeEach
    public void setUp() {
        mascota = new Mascota();
    }

    @Test
    @Order(1)
    public void testSetAndGetNombre() {
        String nombre = "Firulais";
        mascota.setNombre(nombre);

        assertEquals(nombre, mascota.getNombre());
    }

    @Test
    @Order(2)
    public void testSetAndGetSexo() {
        mascota.setSexo(Sexo.MACHO);
        assertEquals(Sexo.MACHO, mascota.getSexo());

        mascota.setSexo(Sexo.HEMBRA);
        assertEquals(Sexo.HEMBRA, mascota.getSexo());
    }

    @Test
    @Order(3)
    public void testSetAndGetTipo() {
        mascota.setTipo(Tipo.PERRO);
        assertEquals(Tipo.PERRO, mascota.getTipo());

        mascota.setTipo(Tipo.GATO);
        assertEquals(Tipo.GATO, mascota.getTipo());
    }

    @Test
    @Order(4)
    public void testSetAndGetFechas() {
        Date ahora = new Date();
        mascota.setAlta(ahora);
        mascota.setBaja(ahora);

        assertNotNull(mascota.getAlta());
        assertNotNull(mascota.getBaja());
        assertEquals(ahora, mascota.getAlta());
        assertEquals(ahora, mascota.getBaja());
    }

    @Test
    @Order(5)
    public void testFlagEliminado() {
        mascota.setEliminado(true);
        assertTrue(mascota.isEliminado());

        mascota.setEliminado(false);
        assertFalse(mascota.isEliminado());
    }

    @Test
    @Order(6)
    public void testSetAndGetFoto() {
        Foto foto = new Foto();
        foto.setId("123");

        mascota.setFoto(foto);

        assertNotNull(mascota.getFoto());
        assertEquals("123", mascota.getFoto().getId());
    }

    @Test
    @Order(7)
    public void testConstructorAllArgs() {
        Date fecha = new Date();
        Foto foto = new Foto();
        Usuario usuario = new Usuario();

        Mascota m = new Mascota(
                "abc123",
                "Luna",
                usuario,
                Sexo.HEMBRA,
                Tipo.GATO,
                fecha,
                null,
                foto,
                false
        );

        assertEquals("abc123", m.getId());
        assertEquals("Luna", m.getNombre());
        assertEquals(usuario, m.getUsuario());
        assertEquals(Sexo.HEMBRA, m.getSexo());
        assertEquals(Tipo.GATO, m.getTipo());
        assertEquals(fecha, m.getAlta());
        assertNull(m.getBaja());
        assertEquals(foto, m.getFoto());
        assertFalse(m.isEliminado());
    }
}
