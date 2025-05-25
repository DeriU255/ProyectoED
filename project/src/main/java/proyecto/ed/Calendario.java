package proyecto.ed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class Calendario {

    public enum EstadoReserva {
        DISPONIBLE, SUGERENCIA, NO_DISPONIBLE
    }

    public static class ResultadoReserva {
        public final EstadoReserva estado;
        public final LocalTime horaSugerida;
        public final String mensaje;

        public ResultadoReserva(EstadoReserva estado, LocalTime horaSugerida, String mensaje) {
            this.estado = estado;
            this.horaSugerida = horaSugerida;
            this.mensaje = mensaje;
        }
    }

    public ResultadoReserva consultarDisponibilidad(List<Reserva> reservas, LocalDate fecha, LocalTime horaInicio) {
        final int DURACION_MINUTOS = 90;
        LocalTime horaFin = horaInicio.plusMinutes(DURACION_MINUTOS);

        //Filtrar reservas del mismo día
        List<Reserva> reservasDia = reservas.stream()
            .filter(r -> r.getFechaReserva().equals(fecha))
            .collect(Collectors.toList());

        //Comprobar si hay solapamiento
        boolean disponible = reservasDia.stream().noneMatch(r -> {
            LocalTime inicio = r.getHoraReserva();
            LocalTime fin = inicio.plusMinutes(DURACION_MINUTOS);
            return !(horaFin.isBefore(inicio) || horaInicio.isAfter(fin));
        });

        if (disponible) {
            return new ResultadoReserva(EstadoReserva.DISPONIBLE, horaInicio, "Espacio disponible.");
        }

        //Buscar otra hora libre en el día (ejemplo: cada 30 minutos de 9:00 a 18:00)
        LocalTime hora = LocalTime.of(9, 0);
        LocalTime cierre = LocalTime.of(18, 0);
        while (!hora.plusMinutes(DURACION_MINUTOS).isAfter(cierre)) {
            LocalTime horaActual = hora;
            LocalTime posibleFin = horaActual.plusMinutes(DURACION_MINUTOS);
            boolean libre = reservasDia.stream().noneMatch(r -> {
                LocalTime inicio = r.getHoraReserva();
                LocalTime fin = inicio.plusMinutes(DURACION_MINUTOS);
                return !(posibleFin.isBefore(inicio) || horaActual.isAfter(fin));
            });
            if(libre){
                return new ResultadoReserva(EstadoReserva.SUGERENCIA, horaActual, "No disponible, pero libre a las " + horaActual);
            }
            hora = hora.plusMinutes(30);
        }

        return new ResultadoReserva(EstadoReserva.NO_DISPONIBLE, null, "No hay horas libres ese día.");
    }
}
