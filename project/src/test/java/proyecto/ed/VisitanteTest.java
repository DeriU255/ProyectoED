package proyecto.ed;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VisitanteTest {
    @Test
    void testValidarCorreoValido() {
        assertTrue(Visitante.validarCorreo("test@email.com"));
    }

    @Test
    void testValidarCorreoInvalido() {
        assertFalse(Visitante.validarCorreo("testemail.com"));
        assertFalse(Visitante.validarCorreo("test@"));
        assertFalse(Visitante.validarCorreo("@email.com"));
    }

    @Test
    void testGettersSetters() {
        Visitante v = new Visitante(1, "Ana", "López", "987654321", "ana@mail.com");
        v.setNombre("Ana María");
        v.setApellidos("López Pérez");
        v.setTlf("111222333");
        v.setCorreo("nuevo@mail.com");
        assertEquals("Ana María", v.getNombre());
        assertEquals("López Pérez", v.getApellidos());
        assertEquals("111222333", v.getTlf());
        assertEquals("nuevo@mail.com", v.getCorreo());
    }
}
