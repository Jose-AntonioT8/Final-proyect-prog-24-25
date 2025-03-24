package nuevo;

public class Nuevo {
    private int garantia;
    private int numBast;

    public Nuevo() {
        this(0, 0);
    }

    public Nuevo(int garantia, int numBast) {
        this.garantia = garantia;
        this.numBast = numBast;
    }

    public Nuevo(String data){
        deserialize(data);
    }

    public int getGarantia() { return garantia; }
    public int getNumBast() { return numBast; }

    public void setGarantia(int garantia) { this.garantia = garantia; }
    public void setNumBast(int numBast) { this.numBast = numBast; }
    
    @Override
    public String toString() {
        return String.format("Garantía: %d, Número de Bastidor: %d", garantia, numBast);
    }
    
    public String serialize() {
        return String.format("\"%d\";\"%d\"", garantia, numBast);
    }
    
    public void deserialize(String data) {
        String[] datos = data.split(";");
        this.garantia = Integer.parseInt(subtractQuotes(datos[0]));
        this.numBast = Integer.parseInt(subtractQuotes(datos[1]));
    }
    
    private String subtractQuotes(String data) {
        return data.substring(1, data.length() - 1);
    }
}