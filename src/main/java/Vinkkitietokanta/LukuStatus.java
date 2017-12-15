package Vinkkitietokanta;

public enum LukuStatus{
    KAIKKI(-1), LUETTU(1), LUKEMATTOMAT(0);
    private final int id;
    LukuStatus(int id) { this.id = id; }
    public int getValue() { return id; }
}
