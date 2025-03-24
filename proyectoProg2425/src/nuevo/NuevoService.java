package nuevo;

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

public class NuevoService implements DataSetInterface {
    Connection conn;

    public NuevoService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Nuevo> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Nuevo> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT garantia, numBast FROM nuevo";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int garantia = querySet.getInt("garantia");
            int numBast = querySet.getInt("numBast");
            result.add(new Nuevo(garantia, numBast));
        }
        statement.close();
        return result;
    }

    public Nuevo requestById(int id) throws SQLException {
        Statement statement = null;
        Nuevo result = null;
        statement = this.conn.createStatement();
        String sql = String.format("SELECT garantia, numBast FROM nuevo WHERE numBast=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int garantia = querySet.getInt("garantia");
            int numBast = querySet.getInt("numBast");
            result = new Nuevo(garantia, numBast);
        }
        statement.close();
        return result;
    }

    public int create(Nuevo n) throws SQLException {
        String sql = "INSERT INTO nuevo VALUES (?, ?)";
        PreparedStatement prepst = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        prepst.setInt(1, n.getGarantia());
        prepst.setInt(2, n.getNumBast());
        prepst.execute();
        return n.getNumBast();
    }

    public int update(Nuevo n) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format("UPDATE nuevo SET garantia = '%d' WHERE numBast=%d", n.getGarantia(), n.getNumBast());
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        if (affectedRows == 0)
            throw new SQLException("Updating nuevo failed, no rows affected.");
        else
            return affectedRows;
    }

    public boolean delete(int id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format("DELETE FROM nuevo WHERE numBast=%d", id);
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
                Nuevo al = new Nuevo(line);
                String sql = "INSERT INTO nuevo VALUES (?, ?)";
                prep = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                prep.setInt(1, al.getGarantia());
                prep.setInt(2, al.getNumBast());
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
            ArrayList<Nuevo> nuevos = this.requestAll();
            for (Nuevo al : nuevos) {
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