package marcas;

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

public class MarcaService implements DataSetInterface {
    Connection conn;

    public MarcaService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Marca> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Marca> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT codmar, nommar FROM marca";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int codmar = querySet.getInt("codmar");
            String nommar = querySet.getString("nommar");
            result.add(new Marca(codmar, nommar));
        }
        statement.close();
        return result;
    }

    public Marca requestById(int codmar) throws SQLException {
        Statement statement = null;
        Marca result = null;
        statement = this.conn.createStatement();
        String sql = String.format("SELECT codmar, nommar FROM marca WHERE codmar=%d", codmar);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int idMarca = querySet.getInt("codmar");
            String nommar = querySet.getString("nommar");
            result = new Marca(idMarca, nommar);
        }
        statement.close();
        return result;
    }

    public int create(Marca m) throws SQLException {
        String sql = "INSERT INTO marca VALUES (?, ?)";
        PreparedStatement prepst = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        prepst.setInt(1, m.getId());
        prepst.setString(2, m.getNombre());
        prepst.execute();
        return m.getId();
    }

    public int update(Marca m) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format("UPDATE marca SET nommar = '%s' WHERE codmar=%d", m.getNombre(), m.getId());
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        if (affectedRows == 0)
            throw new SQLException("Updating marca failed, no rows affected.");
        else
            return affectedRows;
    }

    public boolean delete(int codmar) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format("DELETE FROM marca WHERE codmar=%d", codmar);
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
                Marca al = new Marca(line);
                String sql = "INSERT INTO marca VALUES (?, ?)";
                prep = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                prep.setInt(1, al.getId());
                prep.setString(2, al.getNombre());
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
            ArrayList<Marca> marcas = this.requestAll();
            for (Marca al : marcas) {
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