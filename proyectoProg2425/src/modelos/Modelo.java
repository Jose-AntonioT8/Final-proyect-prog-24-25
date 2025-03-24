package modelos;

public class Modelo {
    private int id;
    private String nombre;
    private int codMar;

    public Modelo() {
        this(0, "", 0);
    }

    public Modelo(int id, String nombre, int codMar) {
        this.id = id;
        this.nombre = nombre;
        this.codMar = codMar;
    }

    public Modelo(String data){
        deserialize(data);
    }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getCodMar() { return codMar; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCodMar(int codMar) { this.codMar = codMar; }
    
    @Override
    public String toString() {
        return String.format("ID: %d, Nombre: %s, Código de Marca: %d", id, nombre, codMar);
    }
    
    public String serialize() {
        return String.format("\"%d\";\"%s\";\"%d\"", id, nombre, codMar);
    }
    
    public void deserialize(String data) {
        String[] datos = data.split(";");
        this.id = Integer.parseInt(subtractQuotes(datos[0]));
        this.nombre = subtractQuotes(datos[1]);
        this.codMar = Integer.parseInt(subtractQuotes(datos[2]));
    }
    
    private String subtractQuotes(String data) {
        return data.substring(1, data.length() - 1);
    }
}
