package proyecto.ed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class Calendario {
    private List<Reserva> listaReservas;

    public Calendario(){
        this.listaReservas = null;
    }

    public void consultarDisponibilidad(LocalDate fecha, LocalTime horaInicio){
        listaReservas.stream().filter(f -> fecha != f.getFechaReserva()).collect(Collectors.toList());
    }
}
