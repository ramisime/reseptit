/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.reseptit;
import java.sql.*;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.reseptit.*;
import tikape.reseptit.database.*;
import tikape.reseptit.domain.*;
/**
 *
 * @author Omistaja
 */
public class Main {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        System.out.println("Hello world!");
        
        Database database = new Database("jdbc:sqlite:reseptit.db");
        AnnosDao annokset = new AnnosDao(database);
        RaakaAineDao raakaAineet = new RaakaAineDao(database);
        AnnosRaakaAineDao reseptit = new AnnosRaakaAineDao(database);

        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annokset.findAll());
            map.put("raakaAineet", raakaAineet.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/delete/:id", (req, res) -> {
            Integer id = Integer.parseInt(req.params(":id"));
            
            annokset.delete(id);
            
            res.redirect("/reseptit");
            
            return "";
        });
        
        Spark.post("/reseptit", (req, res) -> {
            String nimi = req.queryParams("resepti");
            
            Annos annos = new Annos(-1, nimi);
            annokset.save(annos);
            
            res.redirect("/reseptit");
            
            return "";
        });
        
        Spark.post("/reseptit/ainekset", (req, res) -> {
            Integer raakaAineId = Integer.parseInt(req.queryParams("ainesosa"));
            Integer annosId = Integer.parseInt(req.queryParams("annos"));
            Integer jarjestys = Integer.parseInt(req.queryParams("jarjestys"));
            Integer maara = Integer.parseInt(req.queryParams("maara"));
            String ohje = req.queryParams("ohje");
            String yksikko = req.queryParams("yksikko");
            
            AnnosRaakaAine annosRaakaAine = 
                new AnnosRaakaAine(raakaAineId, annosId, jarjestys, maara, ohje,
                    yksikko);
            
            reseptit.save(annosRaakaAine);
            
            
            res.redirect("/reseptit");
            
            return "";
        });
        
        
        
        Spark.get("/reseptit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("reseptit", annokset.findAll());
            map.put("raakaAineet", raakaAineet.findAll());

            return new ModelAndView(map, "reseptit");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAineet", raakaAineet.findAll());

            return new ModelAndView(map, "ainekset");
        }, new ThymeleafTemplateEngine());
        
        
        Spark.get("/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer id = Integer.parseInt(req.params(":id"));
            map.put("annos", annokset.findOne(id));
            map.put("ainesosat", reseptit.findAllForOne(id));

            return new ModelAndView(map, "resepti");
        }, new ThymeleafTemplateEngine());
        
        
        
        Spark.post("/ainekset/delete/:id", (req, res) -> {
            Integer id = Integer.parseInt(req.params(":id"));
            
            raakaAineet.delete(id);
            
            res.redirect("/ainekset");
            
            return "";
        });
        
        Spark.post("/ainekset", (req, res) -> {
            String nimi = req.queryParams("aine");
            
            RaakaAine raakaAine = new RaakaAine(-1, nimi);
            raakaAineet.save(raakaAine);
            
            res.redirect("/ainekset");
            
            return "";
        });
        
        Spark.get("/ainekset/tilasto", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAineet", raakaAineet.findCount());

            return new ModelAndView(map, "tilasto");
        }, new ThymeleafTemplateEngine());
    }
    /*
    public static Connection getConnection() throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }

        return DriverManager.getConnection("jdbc:sqlite:reseptit.db");
    }*/
}
