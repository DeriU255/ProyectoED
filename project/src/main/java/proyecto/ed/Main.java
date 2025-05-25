package proyecto.ed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.zxing.WriterException;
import java.io.IOException;

public class Main{
    private static final List<Reserva> reservas = new ArrayList<>();
    private static final Calendario calendario = new Calendario();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args){
        while (true) {
            System.out.println("\n--- Sistema de Gestión de Visitas al Museo ---");
            System.out.println("1. Registrar nueva reserva");
            System.out.println("2. Modificar reserva existente");
            System.out.println("3. Consultar disponibilidad");
            System.out.println("4. Generar código QR de una reserva");
            System.out.println("5. Listar reservas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1": registrarReserva(); break;
                case "2": modificarReserva(); break;
                case "3": consultarDisponibilidad(); break;
                case "4": generarQR(); break;
                case "5": listarReservas(); break;
                case "0": System.out.println("¡Hasta pronto!"); return;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private static void registrarReserva() {
        System.out.println("\n--- Nueva Reserva ---");
        List<Visitante> visitantes = new ArrayList<>();
        while (true) {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Apellidos: ");
            String apellidos = scanner.nextLine();
            System.out.print("Teléfono: ");
            String tlf = scanner.nextLine();
            System.out.print("Correo: ");
            String correo = scanner.nextLine();
            if (!Visitante.validarCorreo(correo)) {
                System.out.println("Correo no válido. Intente de nuevo.");
                continue;
            }
            visitantes.add(new Visitante(0, nombre, apellidos, tlf, correo));
            System.out.print("¿Agregar otro visitante? (s/n): ");
            if(!scanner.nextLine().trim().equalsIgnoreCase("s")) break;
        }
        System.out.print("Fecha de la visita (yyyy-MM-dd): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine(), dateFormatter);
        System.out.print("Hora de inicio (HH:mm): ");
        LocalTime hora = LocalTime.parse(scanner.nextLine(), timeFormatter);
        Calendario.ResultadoReserva resultado = calendario.consultarDisponibilidad(reservas, fecha, hora);
        if(resultado.estado == Calendario.EstadoReserva.DISPONIBLE){
            Reserva reserva = new Reserva(visitantes, fecha, hora);
            reservas.add(reserva);
            System.out.println("Reserva realizada con éxito. Código: " + reserva.getCodigoReserva());
        }else if(resultado.estado == Calendario.EstadoReserva.SUGERENCIA){
            System.out.println(resultado.mensaje);
            System.out.print("¿Reservar en la hora sugerida? (s/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
                Reserva reserva = new Reserva(visitantes, fecha, resultado.horaSugerida);
                reservas.add(reserva);
                System.out.println("Reserva realizada en hora sugerida. Código: " + reserva.getCodigoReserva());
            }else{
                System.out.println("Reserva cancelada.");
            }
        }else{
            System.out.println(resultado.mensaje);
        }
    }

    private static void modificarReserva() {
        System.out.print("\nCódigo de reserva a modificar: ");
        int codigo = Integer.parseInt(scanner.nextLine());
        Reserva reserva = buscarReservaPorCodigo(codigo);
        if(reserva == null){
            System.out.println("Reserva no encontrada.");
            return;
        }
        System.out.println("Reserva actual:");
        reserva.imprimirVisitantes();
        System.out.print("Nueva fecha (yyyy-MM-dd): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine(), dateFormatter);
        System.out.print("Nueva hora (HH:mm): ");
        LocalTime hora = LocalTime.parse(scanner.nextLine(), timeFormatter);
        Calendario.ResultadoReserva resultado = calendario.consultarDisponibilidad(reservas, fecha, hora);
        if(resultado.estado == Calendario.EstadoReserva.DISPONIBLE){
            reserva.setFechaReserva(fecha);
            reserva.setHoraReserva(hora);
            System.out.println("Reserva modificada con éxito.");
        }else{
            System.out.println(resultado.mensaje);
        }
    }

    private static void consultarDisponibilidad(){
        System.out.print("\nFecha (yyyy-MM-dd): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine(), dateFormatter);
        System.out.print("Hora inicio (HH:mm): ");
        LocalTime hora = LocalTime.parse(scanner.nextLine(), timeFormatter);
        Calendario.ResultadoReserva resultado = calendario.consultarDisponibilidad(reservas, fecha, hora);
        System.out.println(resultado.mensaje);
        if(resultado.estado == Calendario.EstadoReserva.SUGERENCIA){
            System.out.println("Hora sugerida: " + resultado.horaSugerida);
        }
    }

    private static void generarQR(){
        System.out.print("\nCódigo de reserva: ");
        int codigo = Integer.parseInt(scanner.nextLine());
        Reserva reserva = buscarReservaPorCodigo(codigo);
        if (reserva == null) {
            System.out.println("Reserva no encontrada.");
            return;
        }
        System.out.print("Ruta para guardar el QR (ej: reserva" + codigo + ".png): ");
        String ruta = scanner.nextLine();
        try{
            reserva.generarCodigoQR(ruta);
            System.out.println("QR generado correctamente en: " + ruta);
        }catch(WriterException | IOException e){
            System.out.println("Error al generar el QR: " + e.getMessage());
        }
    }

    private static void listarReservas(){
        System.out.println("\n--- Reservas registradas ---");
        for (Reserva r : reservas) {
            System.out.println("Código: " + r.getCodigoReserva() + ", Fecha: " + r.getFechaReserva() + ", Hora: " + r.getHoraReserva());
            r.imprimirVisitantes();
            System.out.println();
        }
    }

    private static Reserva buscarReservaPorCodigo(int codigo){
        for(Reserva r : reservas){
            if (r.getCodigoReserva() == codigo) return r;
        }
        return null;
    }
}