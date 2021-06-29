package sample;

public class Persona {

    String nombre, forma, margen, densidad;
    int edad, birads;

    public Persona(){
    }

    public Persona (String nombre, int edad, int birads, String forma, String margen, String densidad) {
        this.nombre = nombre;
        this.birads = birads;
        this.forma = forma;
        this.margen = margen;
        this.densidad = densidad;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public String getMargen() {
        return margen;
    }

    public void setMargen(String margen) {
        this.margen = margen;
    }

    public String getDensidad() {
        return densidad;
    }

    public void setDensidad(String densidad) {
        this.densidad = densidad;
    }


    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getBirads() {
        return birads;
    }

    public void setBirads(int birads) {
        this.birads = birads;
    }
}
