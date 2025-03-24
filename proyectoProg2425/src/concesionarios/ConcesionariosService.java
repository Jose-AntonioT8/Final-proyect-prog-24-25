package concesionarios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;



import dataset.DataSetInterface;
public class ConcesionariosService implements DataSetInterface {
    Connection conn;
    public ConcesionariosService(Connection conn){
        this.conn = conn;
    }

    public ArrayList<Concesionario> requestAll() throws SQLException{
        Statement statement = null;
        ArrayList<Concesionario> result = new ArrayList<Concesionario>();
        statement = this.conn.createStatement();   
        String sql = "SELECT codcon, numemp, nomcon, dircon, tlfcon FROM concesionario";
        // Ejecución de la consulta
        ResultSet querySet = statement.executeQuery(sql);
        // Recorrido del resultado de la consulta
        while(querySet.next()) {
            int codcon = querySet.getInt("codcon");
            int numemp = querySet.getInt("numemp");
            String nomcon = querySet.getString("nomcon");
            String dircon = querySet.getString("dircon");
            String tlfncon = querySet.getString("tlfcon");
            result.add(new Concesionario(codcon, numemp, nomcon, dircon, tlfncon));
        } 
        statement.close();    
        return result;
    }

    public Concesionario requestById(int id) throws SQLException{
        Statement statement = null;
        Concesionario result = null;
        statement = this.conn.createStatement();    
        String sql = String.format("SELECT codcon, numemp, nomcon, dircon, tlfcon FROM concesionario WHERE codcon=%d", id);
        // Ejecución de la consulta
        ResultSet querySet = statement.executeQuery(sql);
        // Recorrido del resultado de la consulta
        if(querySet.next()) {
            int codcon = querySet.getInt("codcon");
            int numemp = querySet.getInt("numemp");
            String nomcon = querySet.getString("nomcon");
            String dircon = querySet.getString("dircon");
            String tlfncon = querySet.getString("tlfcon");
            result = new Concesionario(codcon, numemp, nomcon, dircon, tlfncon);
        }
        statement.close();    
        return result;
    }

    public int create(Concesionario c) throws SQLException{
        String sqlaux = String.format("INSERT INTO concesionario VALUES (?, ?, ?, ?, ?)");
        PreparedStatement prepst = this.conn.prepareStatement(sqlaux, Statement.RETURN_GENERATED_KEYS);
        prepst.setInt(1, c.getId());
        prepst.setInt(2, c.getNumeemp());
        prepst.setString(3, c.getnomcom());
        prepst.setString(4, c.getdircon());
        prepst.setString(5, c.gettlfnCon());
       
        prepst.execute();

        return c.getId();
        
    }

    public int update(Concesionario c) throws SQLException{
        int id=c.getId();
        int numemp = c.getNumeemp();
        String nomcon = c.getnomcom();
        String dircon = c.getdircon();
        String tlfncon = c.gettlfnCon();
        Statement statement = null;
        statement = this.conn.createStatement();    
        String sql = String.format("UPDATE concesionario SET codcon = '%d', numemp = '%d', nomcon = '%s', dircon = '%s', tlfcon = '%s' WHERE codcon=%d", id, numemp, nomcon, dircon, tlfncon, id);
        // Ejecución de la consulta
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        if (affectedRows == 0)
            throw new SQLException("Creating concesionario failed, no rows affected.");
        else
            return affectedRows;
    }

    public boolean delete(int id) throws SQLException{
        Statement statement = null;
        statement = this.conn.createStatement();    
        String sql = String.format("DELETE FROM concesionario WHERE codcon=%d", id);
        // Ejecución de la consulta
        int result = statement.executeUpdate(sql);
        statement.close();
        return result==1;
    }



    @Override
    public void importFromCSV(String file) throws Exception {
        BufferedReader br = null;
        PreparedStatement prep = null;
        try {
            br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            String line = "";
            while((line=br.readLine())!= null){
                Concesionario al = new Concesionario(line);
                String sql = "INSERT INTO concesionario VALUES (?, ?, ?, ?, ?)";
                prep = this.conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                prep.setInt(1, al.getId());
                prep.setInt(2, al.getNumeemp());
                prep.setString(3, al.getnomcom());
                prep.setString(4, al.getdircon());
                prep.setString(5, al.gettlfnCon());
                prep.execute();
            }    
        } catch (IOException e) {
            throw new Exception("Ocurrión un error de E/S"+ e.toString());
        } catch (SQLTimeoutException e){
            throw new Exception("Ocurrión un error al acceder a la base de datos"+ e.toString());
        } catch (SQLException e){
            throw new Exception("Ocurrión un error al acceder a la base de datos"+ e.toString());
        } catch (Exception e){
            throw new Exception("Ocurrión un error "+ e.toString());
        } finally {
            if(prep != null)
                prep.close();
            if(br != null)
                br.close();
        }

        
    }

    @Override
    public void exportToCSV(String file) throws Exception {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8));
            ArrayList<Concesionario> Concesionarios = this.requestAll();
            for(Concesionario al:Concesionarios){
                bw.write(al.serialize()+"\n");
            }
            bw.close();
        } catch(IOException e){
            throw new Exception("Ocurrión un error de E/S "+ e.toString());
        } catch(SQLException e){
            throw new Exception("Ocurrión un error al acceder a la base de datos "+ e.toString());
        }catch (Exception e) {
            throw new Exception("Ocurrión un error "+ e.toString());
        } finally {
            if(bw!=null)
                bw.close();
        }
        
    }

}
