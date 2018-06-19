 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.reseptit.database;

import java.sql.SQLException;
import java.util.List;
import tikape.reseptit.domain.Annos;
import java.sql.*;
import java.util.ArrayList;

public class AnnosDao implements Dao<Annos, Integer> {

    private Database database;

    public AnnosDao(Database database) {
        this.database = database;
    }

    @Override
    public Annos findOne(Integer key) throws SQLException{
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, nimi FROM Annos WHERE id = ?");
            stmt.setInt(1, key);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }

            return new Annos(result.getInt("id"), result.getString("nimi"));
        }
    }

    @Override
    public void save(Annos annos) throws SQLException {
        
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Annos (nimi) VALUES (?)");
            stmt.setString(1, annos.getNimi());
            stmt.executeUpdate();
        }

    }

    @Override
    public List<Annos> findAll() throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Annos");
            
            ResultSet rs = stmt.executeQuery();
        
            List<Annos> annokset = new ArrayList<>();
        
            while (rs.next()) {
                Annos a = 
                    new Annos(rs.getInt("id"),rs.getString("nimi"));
            
            annokset.add(a);
            
            }
        
        return annokset;
        
        }
        
    }
    

    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Annos WHERE id = ?");
            PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM AnnosRaakaAine "
                    + "WHERE annos_id = ?");

            stmt.setInt(1, key);
            stmt2.setInt(1, key);
            stmt.executeUpdate();
            stmt2.executeUpdate();
            stmt.close();
            stmt2.close();
            conn.close();
        };
        
    }

}
