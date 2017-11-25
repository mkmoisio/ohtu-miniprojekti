/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vinkkitietokanta;

public enum LukuStatus{
    KAIKKI(-1), LUETTU(1), LUKEMATTOMAT(0);
    private final int id;
    LukuStatus(int id) { this.id = id; }
    public int getValue() { return id; }
}
