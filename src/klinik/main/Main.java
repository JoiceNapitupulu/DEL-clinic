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

    private static final String RESET   = "\033[0m";
    private static final String BOLD    = "\033[1m";
    private static final String CYAN    = "\033[36m";
    private static final String YELLOW  = "\033[33m";
    private static final String GREEN   = "\033[32m";
    private static final String RED     = "\033[31m";
    private static final String BLUE    = "\033[34m";
    private static final String MAGENTA = "\033[35m";
    private static final String WHITE   = "\033[97m";
    private static final String GRAY    = "\033[90m";

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static String stripAnsi(String s) {
        return s.replaceAll("\033\\[[;\\d]*m", "");
    }

    private static void printLine(String color, String left, String mid, String right, int w) {
        System.out.println(color + left + mid.repeat(w) + right + RESET);
    }

    private static void printRow(String borderColor, String content, int innerWidth) {
        int visibleLen = stripAnsi(content).length();
        int padding    = Math.max(0, innerWidth - visibleLen);
        System.out.println(
            borderColor + "║ " + RESET +
            content +
            " ".repeat(padding) +
            borderColor + " ║" + RESET
        );
    }

    private static String centerText(String coloredText, int innerWidth, int visibleLen) {
        int totalPad = Math.max(0, innerWidth - visibleLen);
        int left  = totalPad / 2;
        int right = totalPad - left;
        return " ".repeat(left) + coloredText + " ".repeat(right);
    }

    private static void printBanner(Dokter dokter, Perawat perawat) {
        final int INNER = 64;
        final int LINE  = INNER + 2;

        String[] logo = {
            " \u2588\u2588\u2588\u2588\u2588\u2588\u2557 \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2557\u2588\u2588\u2557      \u2588\u2588\u2588\u2588\u2588\u2588\u2557\u2588\u2588\u2557     \u2588\u2588\u2557\u2588\u2588\u2588\u2557  \u2588\u2588\u2557\u2588\u2588\u2557 \u2588\u2588\u2588\u2588\u2588\u2588\u2557",
            " \u2588\u2588\u2554\u2550\u2550\u2588\u2588\u2557\u2588\u2588\u2554\u2550\u2550\u2550\u2550\u2557\u2588\u2588\u2551     \u2588\u2588\u2554\u2550\u2550\u2550\u2550\u2557\u2588\u2588\u2551     \u2588\u2588\u2551\u2588\u2588\u2588\u2588\u2557 \u2588\u2588\u2551\u2588\u2588\u2551\u2588\u2588\u2554\u2550\u2550\u2550\u2550\u2557",
            " \u2588\u2588\u2551  \u2588\u2588\u2551\u2588\u2588\u2588\u2588\u2588\u2557  \u2588\u2588\u2551     \u2588\u2588\u2551     \u2588\u2588\u2551     \u2588\u2588\u2551\u2588\u2588\u2554\u2588\u2588\u2557\u2588\u2588\u2551\u2588\u2588\u2551\u2588\u2588\u2551     ",
            " \u2588\u2588\u2551  \u2588\u2588\u2551\u2588\u2588\u2554\u2550\u2550\u2557  \u2588\u2588\u2551     \u2588\u2588\u2551     \u2588\u2588\u2551     \u2588\u2588\u2551\u2588\u2588\u2551\u255a\u2588\u2588\u2588\u2588\u2551\u2588\u2588\u2551\u2588\u2588\u2551     ",
            " \u2588\u2588\u2588\u2588\u2588\u2588\u2554\u255d\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2557\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2557\u255a\u2588\u2588\u2588\u2588\u2588\u2588\u2557\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2557\u2588\u2588\u2551\u2588\u2588\u2551 \u255a\u2588\u2588\u2588\u2551\u2588\u2588\u2551\u255a\u2588\u2588\u2588\u2588\u2588\u2588\u2557",
            " \u255a\u2550\u2550\u2550\u2550\u2550\u255d \u255a\u2550\u2550\u2550\u2550\u2550\u2550\u255d\u255a\u2550\u2550\u2550\u2550\u2550\u2550\u255d \u255a\u2550\u2550\u2550\u2550\u2550\u255d\u255a\u2550\u2550\u2550\u2550\u2550\u2550\u255d\u255a\u2550\u255d\u255a\u2550\u255d  \u255a\u2550\u2550\u255d\u255a\u2550\u255d \u255a\u2550\u2550\u2550\u2550\u2550\u255d"
        };

        printLine(CYAN, "╔", "═", "╗", LINE);
        for (String line : logo) {
            printRow(CYAN, MAGENTA + BOLD + line + RESET, INNER);
        }
        printLine(CYAN, "╠", "═", "╣", LINE);

        String title    = GREEN + BOLD + "D E L  C L I N I C  M A N A G E M E N T" + RESET;
        int    titleLen = "D E L  C L I N I C  M A N A G E M E N T".length();
        printRow(CYAN, centerText(title, INNER, titleLen), INNER);

        printLine(CYAN, "╠", "═", "╣", LINE);

        String dokterLine  = GRAY + "  Dokter  : " + WHITE + dokter.getNama()  + " (" + dokter.getSpesialisasi() + ")" + RESET;
        String perawatLine = GRAY + "  Perawat : " + WHITE + perawat.getNama() + " (Shift " + perawat.getShift() + ")" + RESET;
        printRow(CYAN, dokterLine,  INNER);
        printRow(CYAN, perawatLine, INNER);

        printLine(CYAN, "╚", "═", "╝", LINE);
    }

    private static void printMenu(Queue<Pasien> antrean) {
        final int INNER = 38;
        final int LINE  = INNER + 2;

        System.out.println();
        printLine(YELLOW, "╔", "═", "╗", LINE);
        String menuTitle    = WHITE + BOLD + "MENU UTAMA" + RESET;
        int    menuTitleLen = "MENU UTAMA".length();
        printRow(YELLOW, centerText(menuTitle, INNER, menuTitleLen), INNER);
        printLine(YELLOW, "╠", "═", "╣", LINE);

        printRow(YELLOW, " " + GREEN   + "[1]" + RESET + " Pendaftaran Pasien Baru",  INNER);
        printRow(YELLOW, " " + BLUE    + "[2]" + RESET + " Panggil & Periksa Pasien", INNER);
        printRow(YELLOW, " " + CYAN    + "[3]" + RESET + " Lihat Semua Rekam Medis",  INNER);
        printRow(YELLOW, " " + MAGENTA + "[4]" + RESET + " Ganti Shift Perawat",      INNER);
        printRow(YELLOW, " " + RED     + "[5]" + RESET + " Keluar",                   INNER);

        printLine(YELLOW, "╚", "═", "╝", LINE);

        String info = antrean.isEmpty()
            ? RED + "Kosong" + RESET
            : GREEN + BOLD + antrean.size() + " Pasien" + RESET;
        System.out.println("\n  Antrean saat ini: " + info);
        System.out.print(GREEN + "  ➤ Pilih menu: " + WHITE);
    }

    public static void main(String[] args) {
        Queue<Pasien> antreanKlinik       = new LinkedList<>();
        PasienMapper pasienMapper         = new PasienMapper();
        RekamMedisMapper rekamMedisMapper = new RekamMedisMapper();
        Scanner scanner = new Scanner(System.in);

        Dokter  dokterAktif  = new Dokter(1, "dr. Joshua", "Umum");
        Perawat perawatAktif = new Perawat(1, "Suster Joice", "Pagi");

        boolean isRunning = true;
        while (isRunning) {
            clearScreen();
            printBanner(dokterAktif, perawatAktif);
            printMenu(antreanKlinik);

            String pilihan = scanner.nextLine().trim();
            System.out.print(RESET);

            switch (pilihan) {
                case "1":
                    clearScreen();
                    System.out.println(GREEN + "\n  === PENDAFTARAN PASIEN BARU ===" + RESET);
                    System.out.print(GRAY + "  │ Nama Pasien : " + WHITE); String nama = scanner.nextLine();
                    System.out.print(GRAY + "  │ Umur Pasien : " + WHITE); String umurStr = scanner.nextLine();
                    try {
                        Pasien p = pasienMapper.insertPasien(new Pasien(0, nama, Integer.parseInt(umurStr)));
                        if (p.getId() != 0) {
                            antreanKlinik.add(p);
                            System.out.println(GREEN + "\n  ✔ Pasien berhasil didaftarkan ke antrean.");
                        }
                    } catch (Exception e) {
                        System.out.println(RED + "\n  ✘ Error: Input tidak valid.");
                    }
                    System.out.print(GRAY + "\n  Tekan Enter..."); scanner.nextLine();
                    break;

                case "2":
                    clearScreen();
                    Pasien psk = antreanKlinik.poll();
                    if (psk != null) {
                        System.out.println(BLUE + "\n  === PEMERIKSAAN: " + psk.getNama() + " ===" + RESET);
                        System.out.print(GRAY + "  │ Keluhan   : " + WHITE); String kel = scanner.nextLine();
                        System.out.print(GRAY + "  │ Diagnosis : " + WHITE); String diag = scanner.nextLine();
                        rekamMedisMapper.insertRekamMedis(new RekamMedis(0, psk, dokterAktif, perawatAktif,
                            36.5, "120/80", 60.0, 170.0, kel, diag, "Paracetamol"));
                        System.out.println(GREEN + "\n  ✔ Data rekam medis disimpan.");
                    } else {
                        System.out.println(RED + "\n  ✘ Antrean kosong!");
                    }
                    System.out.print(GRAY + "\n  Tekan Enter..."); scanner.nextLine();
                    break;

                case "3":
                    clearScreen();
                    System.out.println(CYAN + "\n  === RIWAYAT REKAM MEDIS ===" + RESET);
                    rekamMedisMapper.tampilkanSemuaRekamMedis();
                    System.out.print(GRAY + "\n  Tekan Enter..."); scanner.nextLine();
                    break;

                case "4":
                    clearScreen();
                    System.out.println(MAGENTA + "\n  === GANTI SHIFT PERAWAT ===" + RESET);
                    System.out.println("  1. Suster Joice (Pagi)\n  2. Suster Winda (Siang)\n  3. Suster Rahel (Malam)");
                    System.out.print(GRAY + "\n  Pilih (1-3): " + WHITE);
                    String s = scanner.nextLine();
                    if      (s.equals("1")) perawatAktif = new Perawat(1, "Suster Joice", "Pagi");
                    else if (s.equals("2")) perawatAktif = new Perawat(2, "Suster Winda", "Siang");
                    else if (s.equals("3")) perawatAktif = new Perawat(3, "Suster Rahel", "Malam");
                    System.out.println(GREEN + "\n  ✔ Petugas shift diperbarui.");
                    System.out.print(GRAY + "\n  Tekan Enter..."); scanner.nextLine();
                    break;

                case "5":
                    isRunning = false;
                    System.out.println(CYAN + "\n  Terima kasih telah menggunakan DEL CLINIC! 👋\n" + RESET);
                    break;

                default:
                    System.out.println(RED + "  ✘ Menu tidak tersedia.");
                    try { Thread.sleep(800); } catch (Exception e) {}
                    break;
            }
        }
        scanner.close();
    }
}