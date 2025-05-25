package proyecto.ed;

import java.util.regex.Pattern;

public class Visitante {
    private int iIdVisitante;
    private String nombre;
    private String apellidos;
    private String tlf;
    private String correo;
    
    public Visitante(int iIdVisitante, String nombre, String apellidos, String tlf, String correo) {
        this.iIdVisitante = iIdVisitante;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.tlf = tlf;
        this.correo = correo;
    }

    public int getIdVisitante() {
        return this.iIdVisitante;
    }

    public void setiIdVisitante(int iIdVisitante) {
        this.iIdVisitante = iIdVisitante;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTlf() {
        return this.tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString(){
        return String.format("Nombre: %s %s%n\tTeléfono: %s%n\tCorreo: %s", this.nombre, this.apellidos, this.tlf, this.correo);
    }

    public static boolean validarCorreo(String correo){
        return Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", correo);
    }
}
