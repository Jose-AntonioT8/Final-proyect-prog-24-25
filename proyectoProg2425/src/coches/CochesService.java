package coches;

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
import java.util.Date;
import dataset.DataSetInterface;

public class CochesService implements DataSetInterface {
    Connection conn;

    public CochesService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Coches> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Coches> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT numBast, fechFab, precoc, tipcoc, codCon, codMod FROM coche";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int numBast = querySet.getInt("numBast");
            Date fechaFab = querySet.getDate("fechFab");
            int precoc = querySet.getInt("precoc");
            String tipcoc = querySet.getString("tipcoc");
            int codCon = querySet.getInt("codCon");
            int codMod = querySet.getInt("codMod");
            result.add(new Coches(numBast, fechaFab, precoc, tipcoc, codCon, codMod));
        }
        statement.close();
        return result;
    }

    public Coches requestById(int id) throws SQLException {
        Statement statement = null;
        Coches result = null;
        statement = this.conn.createStatement();
        String sql = String.format("SELECT numBast, fechFab, precoc, tipcoc, codCon, codMod FROM coche WHERE numBast=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int numBast = querySet.getInt("numBast");
            Date fechaFab = querySet.getDate("fechFab");
            int precoc = querySet.getInt("precoc");
            String tipcoc = querySet.getString("tipcoc");
            int codCon = querySet.getInt("codCon");
            int codMod = querySet.getInt("codMod");
            result = new Coches(numBast, fechaFab, precoc, tipcoc, codCon, codMod);
        }
        statement.close();
        return result;
    }

    public int create(Coches c) throws SQLException {
        String sql = "INSERT INTO coche VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement prepst = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        prepst.setInt(1, c.getNumBast());
        prepst.setDate(2, new java.sql.Date(c.getFechaFab().getTime()));
        prepst.setInt(3, c.getPrecio());
        prepst.setString(4, c.getTipo());
        prepst.setInt(5, c.getCodCon());
        prepst.setInt(6, c.getCodMod());
        prepst.execute();
       return c.getNumBast();
    }

    public int update(Coches c) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format("UPDATE coche SET fechFab = '%s', precoc = '%d', tipcoc = '%s', codCon = '%d', codMod = '%d' WHERE numBast=%d",
                new java.sql.Date(c.getFechaFab().getTime()), c.getPrecio(), c.getTipo(), c.getCodCon(), c.getCodMod(), c.getNumBast());
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        if (affectedRows == 0)
            throw new SQLException("Updating coche failed, no rows affected.");
        else
            return affectedRows;
    }

    public boolean delete(int id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format("DELETE FROM coche WHERE numBast=%d", id);
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
                Coches al = new Coches(line);
                String sql = "INSERT INTO coche VALUES (?, ?, ?, ?, ?, ?)";
                prep = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                prep.setInt(1, al.getNumBast());
                prep.setDate(2, new java.sql.Date(al.getFechaFab().getTime()));
                prep.setInt(3, al.getPrecio());
                prep.setString(4, al.getTipo());
                prep.setInt(5, al.getCodCon());
                prep.setInt(6, al.getCodMod());
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
            ArrayList<Coches> coches = this.requestAll();
            for (Coches al : coches) {
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