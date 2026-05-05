package klinik.model;

public class RekamMedis {
    private int id;
    private Pasien pasien;
    private Dokter dokter;
    private Perawat perawat;
    private double suhu;
    private String tensi;
    private double beratBadan;
    private double tinggiBadan;
    private String keluhan;
    private String diagnosis;
    private String resepObat;

    public RekamMedis(int id, Pasien pasien, Dokter dokter, Perawat perawat, double suhu, String tensi, double beratBadan, double tinggiBadan, String keluhan, String diagnosis, String resepObat) {
        this.id = id;
        this.pasien = pasien;
        this.dokter = dokter;
        this.perawat = perawat;
        this.suhu = suhu;
        this.tensi = tensi;
        this.beratBadan = beratBadan;
        this.tinggiBadan = tinggiBadan;
        this.keluhan = keluhan;
        this.diagnosis = diagnosis;
        this.resepObat = resepObat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pasien getPasien() {
        return pasien;
    }

    public void setPasien(Pasien pasien) {
        this.pasien = pasien;
    }

    public Dokter getDokter() {
        return dokter;
    }

    public void setDokter(Dokter dokter) {
        this.dokter = dokter;
    }

    public Perawat getPerawat() {
        return perawat;
    }

    public void setPerawat(Perawat perawat) {
        this.perawat = perawat;
    }

    public double getSuhu() {
        return suhu;
    }

    public void setSuhu(double suhu) {
        this.suhu = suhu;
    }

    public String getTensi() {
        return tensi;
    }

    public void setTensi(String tensi) {
        this.tensi = tensi;
    }

    public double getBeratBadan() {
        return beratBadan;
    }

    public void setBeratBadan(double beratBadan) {
        this.beratBadan = beratBadan;
    }

    public double getTinggiBadan() {
        return tinggiBadan;
    }

    public void setTinggiBadan(double tinggiBadan) {
        this.tinggiBadan = tinggiBadan;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getResepObat() {
        return resepObat;
    }

    public void setResepObat(String resepObat) {
        this.resepObat = resepObat;
    }
}
