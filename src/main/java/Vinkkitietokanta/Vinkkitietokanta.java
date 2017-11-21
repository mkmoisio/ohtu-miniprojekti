/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Tämä hoitaa tiedon muokkaamisen oikeanlaiseksi niin tietokannalle, kuin
 * tietokannasta.
 */
public class Vinkkitietokanta implements VinkkitietokantaRajapinta {

    Connection conn = null;
    
    public Vinkkitietokanta(String tkPath){
        //Liitä tietokanta        
        try{
            conn=DriverManager.getConnection(tkPath);
            System.out.println("Connection established");
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException ex){System.err.println(ex.getMessage());};
        }
    }
    
    private void tallennaTietokanta(){    
    }
    
    public boolean tietokantaliitetty(){
        if(conn==null) return false;
        return true;
    }

    @Override
    public boolean poistaKirja(String otsikko) {
        //DELETE FROM vinkki where otsikko = 'surkea päivä'    
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean lisaaKirja(String kirjoittaja, String otsikko) {
        //INSERT INTO vinkki VALUES ('hermanni', 'surkea päivä', 'kirja')
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> haeKaikkiString() {
        //"SELECT * FROM vinkki"
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Vinkki> haeKaikki() {
        //"SELECT * FROM vinkki"
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
