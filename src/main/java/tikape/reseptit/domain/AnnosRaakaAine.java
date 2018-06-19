/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.reseptit.domain;

/**
 *
 * @author Omistaja
 */
public class AnnosRaakaAine {
    
    private Integer raakaAineId;
    private Integer annosId;
    private Integer jarjestys;
    private Integer maara;
    private String ohje;
    private String yksikko;
    private String nimi;

    public AnnosRaakaAine() {
    }

    public AnnosRaakaAine(Integer raakaAineId, Integer annosId,
            Integer jarjestys, Integer maara, String ohje, String yksikko) {
        this.raakaAineId = raakaAineId;
        this.annosId=annosId;
        this.jarjestys=jarjestys;
        this.maara=maara;
        this.ohje = ohje;
        this.yksikko=yksikko;
    }
    
    public AnnosRaakaAine(String nimi, Integer maara, String yksikko, String ohje) {
        this.nimi=nimi;
        this.maara=maara;
        this.ohje = ohje;
        this.yksikko=yksikko;
    }

    public Integer getRaakaAineId() {
        return raakaAineId;
    }

    public void setRaakaAineId(Integer raakaAineId) {
        this.raakaAineId = raakaAineId;
    }
    
    public Integer getAnnosId() {
        return annosId;
    }

    public void setAnnosId(Integer annosId) {
        this.annosId = annosId;
    }
    
    public Integer getJarjestys() {
        return jarjestys;
    }

    public void setJarjestys(Integer jarjestys) {
        this.jarjestys = jarjestys;
    }
    
    public Integer getMaara() {
        return maara;
    }

    public void setMaara(Integer maara) {
        this.maara = maara;
    }

    public String getOhje() {
        return ohje;
    }

    public void setOhje(String ohje) {
        this.ohje = ohje;
    }
    
    public String getYksikko() {
        return yksikko;
    }
    
    public void setYksikko(String yksikko) {
        this.yksikko = yksikko;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public void setNimi(String nimi) {
        this.nimi=nimi;
    }
    
}
