package concesionarios;

public class Concesionario {
    private int id;
    private int numemp;
    private String NomCon;
    private String dircon;
    private String tlfnCon;

    public Concesionario(){
        this(0,0,"","","");
    }
    public Concesionario (Concesionario c){
        this.id=c.id;
        this.numemp=c.numemp;
        this.NomCon=c.NomCon;
        this.dircon=c.dircon;
        this.tlfnCon=c.tlfnCon;
    }

    public Concesionario (String data){
        deserialize(data);
    }
    public Concesionario(int id, int numemp, String nomcon,String dircon, String tlfncon ){
        this.id=id;
        this.NomCon=nomcon;
        this.numemp=numemp;
        this.dircon=dircon;
        this.tlfnCon=tlfncon;
    }
    public int getId(){
        return this.id;
    }
    public int getNumeemp(){
        return this.numemp;
    }
    public String getnomcom(){
        return this.NomCon;
    }
    public String getdircon(){
        return dircon;
    }
    public String gettlfnCon(){
        return tlfnCon;
    }
    public void setid(int id){
        this.id=id;
    }
    public void setNumemo(int numemp){
        this.numemp=numemp;
    }
    public void setNomCon(String NomCon){
        this.NomCon=NomCon;
    }
    public void setDirCon(String dircon){
        this.dircon=dircon;
    }
    public void settlfnCon(String tlfnCon){
        this.tlfnCon=tlfnCon;
    }
    @Override
    public String toString() {
        return String.format("ID: %d, Numero de empleados: %d, Nombre del concesionario: %s, Direccion del concesionario: %s, Numero de telefono: %s", this.id, this.numemp, this.NomCon, this.dircon, this.tlfnCon );
    }
    
    public String serialize() {
        return String.format("\"%d\";\"%d\";\"%s\";\"%s\";\"%s\"", this.id, this.numemp, this.NomCon, this.dircon, this.tlfnCon );
    }
    private String substractQuotes(String data){
        return data.substring(1, data.length()-1);
    }
    public void deserialize(String data) {
        String[] datos = data.split(";");
        
        this.id = Integer.parseInt(datos[0].substring(1, datos[0].length()-1));
        this.numemp = Integer.parseInt(datos[1].substring(1, datos[1].length()-1));
        this.NomCon = this.substractQuotes(datos[2]);
        this.dircon = this.substractQuotes(datos[3]);
        this.tlfnCon = this.substractQuotes(datos[4]);

    }

}
