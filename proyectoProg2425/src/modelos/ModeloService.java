package modelos;

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

public class ModeloService implements DataSetInterface {
    Connection conn;

    public ModeloService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Modelo> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Modelo> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT codmod, nommod, codMar FROM modelo";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int codmod = querySet.getInt("codmod");
            String nommod = querySet.getString("nommod");
            int codMar = querySet.getInt("codMar");
            result.add(new Modelo(codmod, nommod, codMar));
        }
        statement.close();
        return result;
    }

    public Modelo requestById(int codmod) throws SQLException {
        Statement statement = null;
        Modelo result = null;
        statement = this.conn.createStatement();
        String sql = String.format("SELECT codmod, nommod, codMar FROM modelo WHERE codmod=%d", codmod);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int idModelo = querySet.getInt("codmod");
            String nommod = querySet.getString("nommod");
            int codMar = querySet.getInt("codMar");
            result = new Modelo(idModelo, nommod, codMar);
        }
        statement.close();
        return result;
    }

    public int create(Modelo m) throws SQLException {
        String sql = "INSERT INTO modelo VALUES (?, ?, ?)";
        PreparedStatement prepst = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        prepst.setInt(1, m.getId());
        prepst.setString(2, m.getNombre());
        prepst.setInt(3, m.getCodMar());
        prepst.execute();
        return m.getId();
    }

    public int update(Modelo m) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format("UPDATE modelo SET nommod = '%s', codMar = '%d' WHERE codmod=%d", m.getNombre(), m.getCodMar(), m.getId());
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        if (affectedRows == 0)
            throw new SQLException("Updating modelo failed, no rows affected.");
        else
            return affectedRows;
    }

    public boolean delete(int codmod) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format("DELETE FROM modelo WHERE codmod=%d", codmod);
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
                Modelo al = new Modelo(line);
                String sql = "INSERT INTO modelo VALUES (?, ?, ?)";
                prep = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                prep.setInt(1, al.getId());
                prep.setString(2, al.getNombre());
                prep.setInt(3, al.getCodMar());
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
            ArrayList<Modelo> modelos = this.requestAll();
            for (Modelo al : modelos) {
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