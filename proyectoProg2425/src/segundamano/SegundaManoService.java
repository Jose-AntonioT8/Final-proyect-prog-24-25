package segundamano;

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

public class SegundaManoService implements DataSetInterface {
    Connection conn;

    public SegundaManoService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<SegundaMano> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<SegundaMano> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT kilometraje, numPropietarios, numBast FROM segundaMano";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int kilometraje = querySet.getInt("kilometraje");
            int numPropietarios = querySet.getInt("numPropietarios");
            int numBast = querySet.getInt("numBast");
            result.add(new SegundaMano(kilometraje, numPropietarios, numBast));
        }
        statement.close();
        return result;
    }

    public SegundaMano requestById(int numBast) throws SQLException {
        Statement statement = null;
        SegundaMano result = null;
        statement = this.conn.createStatement();
        String sql = String.format("SELECT kilometraje, numPropietarios, numBast FROM segundaMano WHERE numBast=%d", numBast);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int kilometraje = querySet.getInt("kilometraje");
            int numPropietarios = querySet.getInt("numPropietarios");
            result = new SegundaMano(kilometraje, numPropietarios, numBast);
        }
        statement.close();
        return result;
    }

    public int create(SegundaMano s) throws SQLException {
        String sql = "INSERT INTO segundaMano VALUES (?, ?, ?)";
        PreparedStatement prepst = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        prepst.setInt(1, s.getKilometraje());
        prepst.setInt(2, s.getNumPropietarios());
        prepst.setInt(3, s.getNumBast());
        prepst.execute();
       return s.getNumBast();
    }

    public int update(SegundaMano s) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format("UPDATE segundaMano SET kilometraje = '%d', numPropietarios = '%d' WHERE numBast=%d", s.getKilometraje(), s.getNumPropietarios(), s.getNumBast());
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        if (affectedRows == 0)
            throw new SQLException("Updating segundaMano failed, no rows affected.");
        else
            return affectedRows;
    }

    public boolean delete(int numBast) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format("DELETE FROM segundaMano WHERE numBast=%d", numBast);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }

    @Override
    public void importFromCSV(String file) throws Exception {
        BufferedReader br = null;
        PreparedStatement prep = null;
        try {
            br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            String line = "";
            while ((line = br.readLine()) != null) {
                SegundaMano al = new SegundaMano(line);
                String sql = "INSERT INTO segundaMano VALUES (?, ?, ?)";
                prep = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                prep.setInt(1, al.getKilometraje());
                prep.setInt(2, al.getNumPropietarios());
                prep.setInt(3, al.getNumBast());
                prep.execute();
            }
        } catch (IOException e) {
            throw new Exception("Ocurrió un error de E/S" + e.toString());
        } catch (SQLTimeoutException e) {
            throw new Exception("Ocurrió un error al acceder a la base de datos" + e.toString());
        } catch (SQLException e) {
            throw new Exception("Ocurrió un error al acceder a la base de datos" + e.toString());
        } catch (Exception e) {
            throw new Exception("Ocurrió un error " + e.toString());
        } finally {
            if (prep != null)
                prep.close();
            if (br != null)
                br.close();
        }
    }

    @Override
    public void exportToCSV(String file) throws Exception {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8));
            ArrayList<SegundaMano> segundaManos = this.requestAll();
            for (SegundaMano al : segundaManos) {
                bw.write(al.serialize() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            throw new Exception("Ocurrió un error de E/S " + e.toString());
        } catch (SQLException e) {
            throw new Exception("Ocurrió un error al acceder a la base de datos " + e.toString());
        } catch (Exception e) {
            throw new Exception("Ocurrió un error " + e.toString());
        } finally {
            if (bw != null)
                bw.close();
        }
    }
}