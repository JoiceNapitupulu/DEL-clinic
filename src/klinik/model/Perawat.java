package klinik.model;

public class Perawat extends Orang {
    private String shift;

    public Perawat(int id, String nama, String shift) {
        super(id, nama);
        this.shift = shift;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
