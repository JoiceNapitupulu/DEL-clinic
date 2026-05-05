package klinik.mapper;

import klinik.main.DatabaseConfig;
import klinik.model.RekamMedis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RekamMedisMapper {

    public void insertRekamMedis(RekamMedis rm) {
        String sql = "INSERT INTO rekam_medis (pasien_id, dokter_id, perawat_id, suhu, tensi, berat_badan, tinggi_badan, keluhan, diagnosis, resep_obat) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, rm.getPasien().getId());
            pstmt.setInt(2, rm.getDokter().getId());
            pstmt.setInt(3, rm.getPerawat().getId());
            pstmt.setDouble(4, rm.getSuhu());
            pstmt.setString(5, rm.getTensi());
            pstmt.setDouble(6, rm.getBeratBadan());
            pstmt.setDouble(7, rm.getTinggiBadan());
            pstmt.setString(8, rm.getKeluhan());
            pstmt.setString(9, rm.getDiagnosis());
            pstmt.setString(10, rm.getResepObat());
            
            pstmt.executeUpdate();
            System.out.println("Rekam medis berhasil disimpan.");
            
        } catch (SQLException e) {
            System.err.println("Gagal insert rekam medis: " + e.getMessage());
        }
    }

    public void tampilkanSemuaRekamMedis() {
        String sql = "SELECT rm.id, p.nama AS nama_pasien, p.umur, d.nama AS nama_dokter, d.spesialisasi, " +
                     "pr.nama AS nama_perawat, pr.shift, rm.suhu, rm.tensi, rm.berat_badan, rm.tinggi_badan, " +
                     "rm.keluhan, rm.diagnosis, rm.resep_obat " +
                     "FROM rekam_medis rm " +
                     "JOIN pasien p ON rm.pasien_id = p.id " +
                     "JOIN dokter d ON rm.dokter_id = d.id " +
                     "JOIN perawat pr ON rm.perawat_id = pr.id " +
                     "ORDER BY rm.id ASC";
                     
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            System.out.println("\n=== RIWAYAT REKAM MEDIS ===");
            while (rs.next()) {
                System.out.println("ID Rekam Medis : " + rs.getInt("id"));
                System.out.println("Pasien         : " + rs.getString("nama_pasien") + " (Umur: " + rs.getInt("umur") + ")");
                System.out.println("Dokter         : " + rs.getString("nama_dokter") + " (Spesialis: " + rs.getString("spesialisasi") + ")");
                System.out.println("Perawat        : " + rs.getString("nama_perawat") + " (Shift: " + rs.getString("shift") + ")");
                System.out.println("Vital Sign     : Suhu " + rs.getDouble("suhu") + "C, Tensi " + rs.getString("tensi") +
                                   ", BB " + rs.getDouble("berat_badan") + "kg, TB " + rs.getDouble("tinggi_badan") + "cm");
                System.out.println("Keluhan        : " + rs.getString("keluhan"));
                System.out.println("Diagnosis      : " + rs.getString("diagnosis"));
                System.out.println("Resep Obat     : " + rs.getString("resep_obat"));
                System.out.println("-------------------------------------------------");
            }
            
        } catch (SQLException e) {
            System.err.println("Gagal menampilkan rekam medis: " + e.getMessage());
        }
    }
}
