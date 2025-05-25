package proyecto.ed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.WriterException;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.io.IOException;

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

    public void generarCodigoQR(String rutaArchivo) throws WriterException, IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("Código de reserva: ").append(this.codigoReserva).append("\n");
        sb.append("Fecha: ").append(this.fechaReserva).append("\n");
        sb.append("Hora: ").append(this.horaReserva).append("\n");
        sb.append("Visitantes: ");
        for (Visitante v : this.listaVisitantes){
            sb.append(v.getNombre()).append(" ").append(v.getApellidos()).append(", ");
        }
        String datosQR = sb.toString();

        int width = 300;
        int height = 300;
        String imageFormat = "PNG";

        BitMatrix matrix = new MultiFormatWriter().encode(datosQR, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(rutaArchivo);
        MatrixToImageWriter.writeToPath(matrix, imageFormat, path);
    }
}
