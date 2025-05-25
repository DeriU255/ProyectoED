package proyecto.ed;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {
    private Reserva reserva;
    private List<Visitante> visitantes;
    private LocalDate fecha;
    private LocalTime hora;

    @BeforeEach
    void setUp() {
        visitantes = new ArrayList<>();
        visitantes.add(new Visitante(1, "Juan", "Pérez", "123456789", "juan@mail.com"));
        fecha = LocalDate.of(2025, 5, 25);
        hora = LocalTime.of(10, 0);
        reserva = new Reserva(visitantes, fecha, hora);
    }

    @Test
    void testGetters() {
        assertEquals(visitantes, reserva.getListaVisitantes());
        assertEquals(fecha, reserva.getFechaReserva());
        assertEquals(hora, reserva.getHoraReserva());
    }

    @Test
    void testSetters() {
        LocalDate nuevaFecha = LocalDate.of(2025, 6, 1);
        LocalTime nuevaHora = LocalTime.of(12, 0);
        reserva.setFechaReserva(nuevaFecha);
        reserva.setHoraReserva(nuevaHora);
        assertEquals(nuevaFecha, reserva.getFechaReserva());
        assertEquals(nuevaHora, reserva.getHoraReserva());
    }

    @Test
    void testImprimirVisitantes() {
        assertDoesNotThrow(() -> reserva.imprimirVisitantes());
    }
}
