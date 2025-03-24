package marcas;

public class Marca {
    private int id;
    private String nombre;

    public Marca() {
        this(0, "");
    }
    public Marca (String data){
        deserialize(data);
    }

    public Marca(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { 
        return id; 
    }
    public String getNombre() { 
        return nombre; 
    }

    public void setId(int id) { 
        this.id = id; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Nombre: %s", id, nombre);
    }
    
    public String serialize() {
        return String.format("\"%d\";\"%s\"", id, nombre);
    }
    
    public void deserialize(String data) {
        String[] datos = data.split(";");
        this.id = Integer.parseInt(subtractQuotes(datos[0]));
        this.nombre = subtractQuotes(datos[1]);
    }
    
    private String subtractQuotes(String data) {
        return data.substring(1, data.length() - 1);
    }
}