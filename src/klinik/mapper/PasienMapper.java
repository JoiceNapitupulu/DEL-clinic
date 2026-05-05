package klinik.mapper;

import klinik.main.DatabaseConfig;
import klinik.model.Pasien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasienMapper {

    public Pasien insertPasien(Pasien pasien) {
        String sql = "INSERT INTO pasien (nama, umur) VALUES (?, ?) RETURNING id";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, pasien.getNama());
            pstmt.setInt(2, pasien.getUmur());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int generatedId = rs.getInt("id");
                    pasien.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal insert pasien: " + e.getMessage());
        }
        
        return pasien;
    }
}
