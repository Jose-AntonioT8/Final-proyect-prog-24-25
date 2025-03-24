package segundamano;

public class SegundaMano {
    private int kilometraje;
    private int numPropietarios;
    private int numBast;

    public SegundaMano() {
        this(0, 0, 0);
    }
    public SegundaMano(String data){
        deserialize(data);
    }

    public SegundaMano(int kilometraje, int numPropietarios, int numBast) {
        this.kilometraje = kilometraje;
        this.numPropietarios = numPropietarios;
        this.numBast = numBast;
    }

    public int getKilometraje() { return kilometraje; }
    public int getNumPropietarios() { return numPropietarios; }
    public int getNumBast() { return numBast; }

    public void setKilometraje(int kilometraje) { this.kilometraje = kilometraje; }
    public void setNumPropietarios(int numPropietarios) { this.numPropietarios = numPropietarios; }
    public void setNumBast(int numBast) { this.numBast = numBast; }
    
    @Override
    public String toString() {
        return String.format("Kilometraje: %d, Número de Propietarios: %d, Número de Bastidor: %d", kilometraje, numPropietarios, numBast);
    }
    
    public String serialize() {
        return String.format("\"%d\";\"%d\";\"%d\"", kilometraje, numPropietarios, numBast);
    }
    
    public void deserialize(String data) {
        String[] datos = data.split(";");
        this.kilometraje = Integer.parseInt(subtractQuotes(datos[0]));
        this.numPropietarios = Integer.parseInt(subtractQuotes(datos[1]));
        this.numBast = Integer.parseInt(subtractQuotes(datos[2]));
    }
    
    private String subtractQuotes(String data) {
        return data.substring(1, data.length() - 1);
    }
}