package proyecto.ed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Reserva {
    private int codigoReserva;
    private List<Visitante> listaVisitantes;
    private LocalDate fechaReserva;
    private LocalTime horaReserva;

    public Reserva(List<Visitante> listaVisitantes, LocalDate fecha, LocalTime horaReserva) {
        this.codigoReserva = this.hashCode();
        this.listaVisitantes = listaVisitantes;
        this.fechaReserva = fecha;
        this.horaReserva = horaReserva;
    }

    public int getCodigoReserva() {
        return this.codigoReserva;
    }

    public void setCodigoReserva(int codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public List<Visitante> getListaVisitantes() {
        return this.listaVisitantes;
    }

    public void setListaVisitantes(List<Visitante> listaVisitantes) {
        this.listaVisitantes = listaVisitantes;
    }

    public LocalDate getFechaReserva() {
        return this.fechaReserva;
    }

    public void setFechaReserva(LocalDate fecha) {
        this.fechaReserva = fecha;
    }

    public LocalTime getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(LocalTime horaReserva) {
        this.horaReserva = horaReserva;
    }

    public void imprimirVisitantes() {
        listaVisitantes.stream().map(n -> n.toString()).forEach(n -> System.out.println(n));
    }
}
