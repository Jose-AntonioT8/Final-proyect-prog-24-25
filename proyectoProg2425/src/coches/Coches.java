package coches;
import java.util.Date;

public class Coches {
    private int numBast;
    private Date fechaFab;
    private int precio;
    private String tipo;
    private int codCon;
    private int codMod;
    
    public Coches() {
        this(0, new Date(), 0, "", 0, 0);
    }
    
    public Coches(int numBast, Date fechaFab, int precio, String tipo, int codCon, int codMod) {
        this.numBast = numBast;
        this.fechaFab = fechaFab;
        this.precio = precio;
        this.tipo = tipo;
        this.codCon = codCon;
        this.codMod = codMod;
    }
    public Coches (String data){
        deserialize(data);
    }
    

    public int getNumBast() { return numBast; }
    public Date getFechaFab() { return fechaFab; }
    public int getPrecio() { return precio; }
    public String getTipo() { return tipo; }
    public int getCodCon() { return codCon; }
    public int getCodMod() { return codMod; }

    public void setNumBast(int numBast) { this.numBast = numBast; }
    public void setFechaFab(Date fechaFab) { this.fechaFab = fechaFab; }
    public void setPrecio(int precio) { this.precio = precio; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setCodCon(int codCon) { this.codCon = codCon; }
    public void setCodMod(int codMod) { this.codMod = codMod; }
    
    @Override
    public String toString() {
        return String.format("NumBast: %d, FechaFab: %s, Precio: %d, Tipo: %s, CodCon: %d, CodMod: %d", numBast, fechaFab, precio, tipo, codCon, codMod);
    }
    
    public String serialize() {
        return String.format("\"%d\";\"%s\";\"%d\";\"%s\";\"%d\";\"%d\"", numBast, fechaFab, precio, tipo, codCon, codMod);
    }
    
    public void deserialize(String data) {
        String[] datos = data.split(";");
        this.numBast = Integer.parseInt(subtractQuotes(datos[0]));
        this.fechaFab = new Date(subtractQuotes(datos[1]));
        this.precio = Integer.parseInt(subtractQuotes(datos[2]));
        this.tipo = subtractQuotes(datos[3]);
        this.codCon = Integer.parseInt(subtractQuotes(datos[4]));
        this.codMod = Integer.parseInt(subtractQuotes(datos[5]));
    }
    
    private String subtractQuotes(String data) {
        return data.substring(1, data.length() - 1);
    }
}
