/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Tämä hoitaa tiedon muokkaamisen oikeanlaiseksi niin tietokannalle, kuin
 * tietokannasta.
 */
public class Vinkkitietokanta implements VinkkitietokantaRajapinta {

    Connection conn = null;
    Statement stmt = null;
     
    public Vinkkitietokanta(String tkPath){
        //Liitä tietokanta        
        try{
            conn=DriverManager.getConnection(tkPath);
            //System.out.println("tietokanta liitetty");
            stmt = conn.createStatement();            
            //System.out.println("statement luotu");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    
    //Sulje yhteydet tietokantaan
    public void sulje(){
        try{
        if(stmt!=null)
            conn.close();
        }catch(SQLException se){
        }
        try{
           if(conn!=null)
              conn.close();
        }catch(SQLException se){
           se.printStackTrace();
        }
    }
    
    //Tallentuuko suoraan vai pitääkö erikseen kutsua jotain tallenna tietokanta?
    private void tallennaTietokanta(){    
    }
    
    public boolean tietokantaliitetty(){
        if(conn==null) return false;
        return true;
    }

    @Override
    public boolean poistaKirja(String otsikko) {
        //Toivotaan, että syöte on kunnollista
        //DELETE FROM vinkki where otsikko = 'surkea päivä'   
        try{
            String sql = "DELETE FROM vinkki where otsikko = '"+otsikko+"'";
            stmt.executeUpdate(sql);
        }catch(SQLException se){
            se.printStackTrace();
        }
        //Kirjan poistaminen antaa nyt aina true:n tarvitaan joku
        //tapa tarkistaa, että onnistuuko poisto todella
        return true;
    }

    @Override
    public boolean lisaaKirja(String kirjoittaja, String otsikko) {
        //INSERT INTO vinkki VALUES ('hermanni', 'surkea päivä', 'kirja')
        //Toivotaan, että syöte on kunnollista
        try{
            String sql = "INSERT INTO vinkki VALUES ('"+kirjoittaja+"','"+otsikko+"', 'kirja')";
            stmt.executeUpdate(sql);
        }catch(SQLException se){
            System.out.println(se.toString());
        }
        //Kirjan lisääminen antaa nyt aina true:n tarvitaan joku
        //tapa tarkistaa, että onnistuuko poisto todella
        return true;
    }

    @Override
    public List<String> haeKaikkiString() {
        List<String> lista = new ArrayList<>();
        try{
            String sql = "SELECT * FROM Vinkki";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String kirjoittaja  = rs.getString("kirjoittaja");
                String otsikko = rs.getString("otsikko");
                Vinkki vinkki = new Vinkki(kirjoittaja,otsikko);
                lista.add(vinkki.toString());
            }
            rs.close();
        }catch(SQLException se){
            System.out.println(se.toString());
        }
        
        return lista;
        //"SELECT * FROM vinkki"
    }

    @Override
    public List<Vinkki> haeKaikki() {
        //Pasta koodia siivoa joskus 
        List<Vinkki> lista = new ArrayList<>();
        try{
            String sql = "SELECT * FROM vinkki";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String kirjoittaja  = rs.getString("kirjoittaja");
                String otsikko = rs.getString("otsikko");
                Vinkki vinkki = new Vinkki(kirjoittaja,otsikko);
                lista.add(vinkki);
            }
            rs.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return lista;
    }

}
