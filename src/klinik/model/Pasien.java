package klinik.model;

public class Pasien extends Orang {
    private int umur;

    public Pasien(int id, String nama, int umur) {
        super(id, nama);
        this.umur = umur;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }
}
