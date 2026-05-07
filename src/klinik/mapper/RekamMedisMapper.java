package klinik.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import klinik.main.DatabaseConfig;
import klinik.model.RekamMedis;

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
            System.out.println("\n[SUKSES] Rekam medis berhasil disimpan.");
            
        } catch (SQLException e) {
            System.err.println("Gagal insert rekam medis: " + e.getMessage());
        }
    }

    public void tampilkanSemuaRekamMedis() {
        String sql = "SELECT rm.id, p.nama AS nama_pasien, p.umur, d.nama AS nama_dokter, " +
                     "rm.suhu, rm.tensi, rm.keluhan, rm.diagnosis, rm.resep_obat " +
                     "FROM rekam_medis rm " +
                     "JOIN pasien p ON rm.pasien_id = p.id " +
                     "JOIN dokter d ON rm.dokter_id = d.id " +
                     "ORDER BY rm.id ASC";
                     
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            // --- HEADER TABEL ---
            System.out.println("\n" + "=".repeat(110));
            System.out.printf("| %-3s | %-15s | %-4s | %-15s | %-20s | %-20s |\n", 
                              "ID", "PASIEN", "UMUR", "DOKTER", "KELUHAN", "DIAGNOSIS");
            System.out.println("-".repeat(110));
            
            boolean adaData = false;
            while (rs.next()) {
                adaData = true;
                System.out.printf("| %-3d | %-15s | %-4d | %-15s | %-20s | %-20s |\n", 
                                  rs.getInt("id"), 
                                  rs.getString("nama_pasien"), 
                                  rs.getInt("umur"),
                                  rs.getString("nama_dokter"),
                                  (rs.getString("keluhan").length() > 20 ? rs.getString("keluhan").substring(0,17) + "..." : rs.getString("keluhan")),
                                  (rs.getString("diagnosis").length() > 20 ? rs.getString("diagnosis").substring(0,17) + "..." : rs.getString("diagnosis")));
            }
            
            if (!adaData) {
                System.out.println("|" + " ".repeat(45) + "TIDAK ADA DATA" + " ".repeat(49) + "|");
            }
            
            System.out.println("=".repeat(110));
            
        } catch (SQLException e) {
            System.err.println("Gagal menampilkan rekam medis: " + e.getMessage());
        }
    }
}