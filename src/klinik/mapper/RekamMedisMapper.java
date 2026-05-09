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
                     "pr.nama AS nama_perawat, " +
                     "rm.keluhan, rm.diagnosis " +
                     "FROM rekam_medis rm " +
                     "JOIN pasien  p  ON rm.pasien_id  = p.id  " +
                     "JOIN dokter  d  ON rm.dokter_id  = d.id  " +
                     "JOIN perawat pr ON rm.perawat_id = pr.id " +
                     "ORDER BY rm.id ASC";

        final int W_ID      = 3;
        final int W_PASIEN  = 18;
        final int W_UMUR    = 4;
        final int W_DOKTER  = 15;
        final int W_PERAWAT = 15;
        final int W_KELUHAN = 18;
        final int W_DIAG    = 18;

        final int TOTAL = W_ID + W_PASIEN + W_UMUR + W_DOKTER + W_PERAWAT + W_KELUHAN + W_DIAG
                          + (3 * 7) + 4;

        String LINE_THICK = "═".repeat(TOTAL);
        String LINE_THIN  = "─".repeat(TOTAL);

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n╔" + LINE_THICK + "╗");
            System.out.printf("║ %-" + W_ID      + "s │ %-" + W_PASIEN  + "s │ %-" + W_UMUR +
                              "s │ %-" + W_DOKTER + "s │ %-" + W_PERAWAT + "s │ %-" + W_KELUHAN +
                              "s │ %-" + W_DIAG   + "s ║\n",
                              "ID", "PASIEN", "UMUR", "DOKTER", "PERAWAT", "KELUHAN", "DIAGNOSIS");
            System.out.println("╠" + LINE_THIN + "╣");

            boolean adaData = false;
            while (rs.next()) {
                adaData = true;
                System.out.printf("║ %-" + W_ID      + "d │ %-" + W_PASIEN  + "s │ %-" + W_UMUR +
                                  "d │ %-" + W_DOKTER + "s │ %-" + W_PERAWAT + "s │ %-" + W_KELUHAN +
                                  "s │ %-" + W_DIAG   + "s ║\n",
                                  rs.getInt("id"),
                                  trunc(rs.getString("nama_pasien"),  W_PASIEN),
                                  rs.getInt("umur"),
                                  trunc(rs.getString("nama_dokter"),  W_DOKTER),
                                  trunc(rs.getString("nama_perawat"), W_PERAWAT),
                                  trunc(rs.getString("keluhan"),      W_KELUHAN),
                                  trunc(rs.getString("diagnosis"),    W_DIAG));
            }

            if (!adaData) {
                int padTotal = TOTAL - 2;
                int msgLen   = "TIDAK ADA DATA".length();
                int padLeft  = (padTotal - msgLen) / 2;
                int padRight = padTotal - msgLen - padLeft;
                System.out.println("║" + " ".repeat(padLeft) + "TIDAK ADA DATA" + " ".repeat(padRight) + "║");
            }

            System.out.println("╚" + LINE_THICK + "╝");

        } catch (SQLException e) {
            System.err.println("Gagal menampilkan rekam medis: " + e.getMessage());
        }
    }

    private String trunc(String s, int maxLen) {
        if (s == null) return "-".repeat(Math.min(1, maxLen));
        if (s.length() <= maxLen) return s;
        return s.substring(0, maxLen - 3) + "...";
    }
}