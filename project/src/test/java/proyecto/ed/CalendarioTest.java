package proyecto.ed;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class CalendarioTest {
    private Calendario calendario;
    private List<Reserva> reservas;
    private LocalDate fecha;
    private LocalTime hora;

    @BeforeEach
    void setUp() {
        calendario = new Calendario();
        reservas = new ArrayList<>();
        fecha = LocalDate.of(2025, 5, 25);
        hora = LocalTime.of(10, 0);
    }

    @Test
    void testDisponibilidadSinReservas() {
        Calendario.ResultadoReserva res = calendario.consultarDisponibilidad(reservas, fecha, hora);
        assertEquals(Calendario.EstadoReserva.DISPONIBLE, res.estado);
        assertEquals(hora, res.horaSugerida);
    }

    @Test
    void testNoDisponibleConSolapamiento() {
        Reserva reservaMock = Mockito.mock(Reserva.class);
        Mockito.when(reservaMock.getFechaReserva()).thenReturn(fecha);
        Mockito.when(reservaMock.getHoraReserva()).thenReturn(hora);
        reservas.add(reservaMock);
        Calendario.ResultadoReserva res = calendario.consultarDisponibilidad(reservas, fecha, hora);
        assertNotEquals(Calendario.EstadoReserva.DISPONIBLE, res.estado);
    }

    @Test
    void testSugerenciaHoraLibre() {
        Reserva reservaMock = Mockito.mock(Reserva.class);
        Mockito.when(reservaMock.getFechaReserva()).thenReturn(fecha);
        Mockito.when(reservaMock.getHoraReserva()).thenReturn(hora);
        reservas.add(reservaMock);
        Calendario.ResultadoReserva res = calendario.consultarDisponibilidad(reservas, fecha, LocalTime.of(10, 0));
        assertTrue(res.estado == Calendario.EstadoReserva.SUGERENCIA || res.estado == Calendario.EstadoReserva.NO_DISPONIBLE);
    }
}
