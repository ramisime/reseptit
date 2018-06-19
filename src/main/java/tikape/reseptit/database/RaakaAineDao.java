/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.reseptit.database;

/**
 *
 * @author Omistaja
 */
import java.sql.SQLException;
import java.util.List;
import tikape.reseptit.domain.RaakaAine;
import java.sql.*;
import java.util.ArrayList;

public class RaakaAineDao implements Dao<RaakaAine, Integer> {

    private Database database;

    public RaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public RaakaAine findOne(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, nimi FROM RaakaAine WHERE id = ?");
            stmt.setInt(1, key);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }

            return new RaakaAine(result.getInt("id"), result.getString("nimi"));
        }
    }

    @Override
    public void save(RaakaAine raakaAine) throws SQLException {
        
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine (nimi) VALUES (?)");
            stmt.setString(1, raakaAine.getNimi());
            stmt.executeUpdate();
        }

    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine");
            
            ResultSet rs = stmt.executeQuery();
        
            List<RaakaAine> annokset = new ArrayList<>();
        
            while (rs.next()) {
                RaakaAine a = 
                    new RaakaAine(rs.getInt("id"),rs.getString("nimi"));
            
            annokset.add(a);
            
            }
        
        return annokset;
        
        }
        
    }
    
    public List<RaakaAine> findCount() throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT a.nimi, count(annos_id) as lkm\n" +
                "FROM RaakaAine as a LEFT JOIN\n" +
                "AnnosRaakaAine as b on a.id=b.raaka_aine_id\n" +
                "GROUP BY a.nimi\n" +
                "ORDER BY count(*) DESC;");
            
            ResultSet rs = stmt.executeQuery();
        
            List<RaakaAine> annokset = new ArrayList<>();
        
            while (rs.next()) {
                RaakaAine a = 
                    new RaakaAine(rs.getString("nimi"),rs.getInt("lkm"));
            
            annokset.add(a);
            
            }
        
        return annokset;
        
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaAine WHERE id = ?");
            PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM AnnosRaakaAine "
                    + "WHERE raaka_aine_id = ?");

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
