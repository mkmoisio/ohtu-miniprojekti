/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toiminnot.muu;

import apuviritykset.vakiot.Vakiot;
import toiminnot.Operaatio;

/**
 *
 * @author mikkomo
 */
public class Lopetus extends Operaatio {

    public Lopetus() {
        super();
    }

    @Override
    public void suorita() {
       System.exit(Vakiot.EXIT_STATUS_OK);
    }
    
}
