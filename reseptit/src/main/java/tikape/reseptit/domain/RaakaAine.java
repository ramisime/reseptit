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
public class RaakaAine {
    private Integer id;
    private String nimi;
    private Integer lkm;

    public RaakaAine() {
    }

    public RaakaAine(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }
    
    public RaakaAine(String nimi, Integer lkm) {
        this.nimi=nimi;
        this.lkm=lkm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public Integer getLkm() {
        return lkm;
    }
    
    public void setLkm(Integer lkm) {
        this.lkm=lkm;
    }
}
