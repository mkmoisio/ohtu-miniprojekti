/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apuviritykset;

/**
 *
 * @author mikkomo
 */
public class Validator {
    
    public static boolean kirjavinkinSyoteOk(String kirjoittaja, String otsikko) {
        return (!kirjoittaja.isEmpty() && !otsikko.isEmpty());
    }
}
