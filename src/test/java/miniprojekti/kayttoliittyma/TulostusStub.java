/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniprojekti.kayttoliittyma;

import io.TulostusRajapinta;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author mikkomo
 */
public class TulostusStub implements TulostusRajapinta {

    private final Deque<String> tulosteet;

    public TulostusStub() {
        this.tulosteet = new ArrayDeque();
    }

    @Override
    public void println(String x) {
        this.tulosteet.add(x);
    }

    public String pop() {
        return this.tulosteet.pop();
    }

    public List<String> tulosteetListana() {
        List<String> lista = new ArrayList();

        for (Iterator<String> s = this.tulosteet.iterator(); s.hasNext();) {
            lista.add(s.next().trim());
        }
        return lista;
    }

    public boolean tulosteSisaltaa(String string) {
        return this.tulosteetListana().contains(string.trim());
    }

    public void nollaa() {
        this.tulosteet.clear();
    }

    @Override
    public void print(String x) {
        this.tulosteet.add(x);
    }
    
    public void kaikki() {
        for (String s : this.tulosteetListana()) {
            System.out.println(s);
        }
    }
}
