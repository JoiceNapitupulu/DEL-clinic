package klinik.main;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import klinik.mapper.PasienMapper;
import klinik.mapper.RekamMedisMapper;
import klinik.model.Dokter;
import klinik.model.Pasien;
import klinik.model.Perawat;
import klinik.model.RekamMedis;

public class Main {
    public static void main(String[] args) {
        Queue<Pasien> antreanKlinik = new LinkedList<>();
        PasienMapper pasienMapper = new PasienMapper();
        RekamMedisMapper rekamMedisMapper = new RekamMedisMapper();
        Scanner scanner = new Scanner(System.in);

        Dokter dokterAktif = new Dokter(1, "dr. Joshua", "Umum");
        Perawat perawatAktif = new Perawat(1, "Suster Joice", "Pagi");

        boolean isRunning = true;

        while (isRunning) {
            // --- TAMPILAN MENU YANG DIPERBAIKI ---
            System.out.println("\n" + "=".repeat(40));
            System.out.println("        --- CLINIC CARE HUB ---        ");
            System.out.println("=".repeat(40));
            System.out.printf("| %-2s | %-31s |\n", "1", "Pendaftaran Pasien Baru");
            System.out.printf("| %-2s | %-31s |\n", "2", "Panggil & Periksa Pasien");
            System.out.printf("| %-2s | %-31s |\n", "3", "Lihat Semua Riwayat Medis");
            System.out.printf("| %-2s | %-31s |\n", "4", "Keluar");
            System.out.println("=".repeat(40));
            System.out.print("Pilih menu: ");
            
            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    System.out.print("Masukkan Nama Pasien: ");
                    String nama = scanner.nextLine();
                    System.out.print("Masukkan Umur Pasien: ");
                    int umur = 0;
                    try {
                        umur = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Umur tidak valid. Menggunakan default 0.");
                    }

                    Pasien pasienBaru = new Pasien(0, nama, umur);
                    Pasien pasienTerdaftar = pasienMapper.insertPasien(pasienBaru);
                    
                    if (pasienTerdaftar.getId() != 0) {
                        antreanKlinik.add(pasienTerdaftar);
                        System.out.println("\n[SUKSES] Pasien " + pasienTerdaftar.getNama() + " masuk antrean.");
                    } else {
                        System.out.println("\n[ERROR] Gagal mendaftarkan pasien.");
                    }
                    break;

                case "2":
                    Pasien pasienDiperiksa = antreanKlinik.poll();
                    if (pasienDiperiksa != null) {
                        System.out.println("\n>>> MEMERIKSA PASIEN: " + pasienDiperiksa.getNama().toUpperCase() + " <<<");
                        
                        System.out.print("Suhu (C)     : ");
                        double suhu = parseDouble(scanner.nextLine());
                        System.out.print("Tensi        : ");
                        String tensi = scanner.nextLine();
                        System.out.print("Berat Badan  : ");
                        double beratBadan = parseDouble(scanner.nextLine());
                        System.out.print("Tinggi Badan : ");
                        double tinggiBadan = parseDouble(scanner.nextLine());
                        System.out.print("Keluhan      : ");
                        String keluhan = scanner.nextLine();
                        System.out.print("Diagnosis    : ");
                        String diagnosis = scanner.nextLine();
                        System.out.print("Resep Obat   : ");
                        String resepObat = scanner.nextLine();

                        RekamMedis rm = new RekamMedis(0, pasienDiperiksa, dokterAktif, perawatAktif, 
                                                       suhu, tensi, beratBadan, tinggiBadan, 
                                                       keluhan, diagnosis, resepObat);

                        rekamMedisMapper.insertRekamMedis(rm);
                    } else {
                        System.out.println("\n[INFO] Antrean kosong.");
                    }
                    break;

                case "3":
                    rekamMedisMapper.tampilkanSemuaRekamMedis();
                    break;

                case "4":
                    System.out.println("Keluar dari sistem...");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Menu tidak tersedia.");
                    break;
            }
        }
        scanner.close();
    }
    
    private static double parseDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}