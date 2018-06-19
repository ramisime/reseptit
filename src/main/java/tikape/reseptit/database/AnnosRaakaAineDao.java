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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.reseptit.database.Database;
import tikape.reseptit.domain.AnnosRaakaAine;

public class AnnosRaakaAineDao implements Dao<AnnosRaakaAine, Integer> {

    private Database database;

    public AnnosRaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public AnnosRaakaAine findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        
    }

    @Override
    public List<AnnosRaakaAine> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<AnnosRaakaAine> findAllForOne(Integer key) throws SQLException {
        String query = "SELECT b.nimi, a.maara, a.yksikko, \n" +
            "CASE WHEN a.ohje IS NULL THEN ' ' ELSE a.ohje end as ohje\n" +
            "FROM AnnosRaakaAine as a, RaakaAine as b\n" +
            "WHERE a.raaka_aine_id=b.id and a.annos_id=?\n" +
            "ORDER BY a.jarjestys;";
        
        List<AnnosRaakaAine> annoksenRaakaAineet = new ArrayList<>();
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, key);
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                annoksenRaakaAineet.add(new AnnosRaakaAine(result.getString("nimi"),
                    result.getInt("maara"), result.getString("yksikko"),
                    result.getString("ohje")));
            }
        }
        
        return annoksenRaakaAineet;
        
    }

    @Override
    public void save(AnnosRaakaAine annosRaakaAine) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO AnnosRaakaAine (raaka_aine_id, annos_id, "
                            + "jarjestys, maara, ohje, yksikko) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, annosRaakaAine.getRaakaAineId());
            stmt.setInt(2, annosRaakaAine.getAnnosId());
            stmt.setInt(3, annosRaakaAine.getJarjestys());
            stmt.setInt(4, annosRaakaAine.getMaara());
            stmt.setString(5, annosRaakaAine.getOhje());
            stmt.setString(6, annosRaakaAine.getYksikko());
            stmt.executeUpdate();
        }

    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
