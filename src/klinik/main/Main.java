package klinik.main;

import klinik.mapper.PasienMapper;
import klinik.mapper.RekamMedisMapper;
import klinik.model.Dokter;
import klinik.model.Pasien;
import klinik.model.Perawat;
import klinik.model.RekamMedis;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    // ─── ANSI Color Codes ───────────────────────────────────────────────────────
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

    // ─── UI Helpers ─────────────────────────────────────────────────────────────

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void printLine(String color, String left, String mid, String right, int width) {
        System.out.print(color + left);
        System.out.print(mid.repeat(width));
        System.out.println(right + RESET);
    }

    private static void printRow(String color, String content, int totalWidth) {
        // totalWidth = inner width (tidak termasuk border kiri-kanan)
        int contentLen = stripAnsi(content).length();
        int padding = totalWidth - contentLen;
        System.out.println(color + "│" + RESET + " " + content + " ".repeat(Math.max(0, padding - 1)) + color + "│" + RESET);
    }

    private static String stripAnsi(String s) {
        return s.replaceAll("\033\\[[;\\d]*m", "");
    }

    private static void printDivider(String color, int width) {
        System.out.println(color + "├" + "─".repeat(width) + "┤" + RESET);
    }

    // ─── Tampilan Header / Banner ────────────────────────────────────────────────

    private static void printBanner(Dokter dokter, Perawat perawat) {
        int w = 52; // lebar dalam box
        System.out.println();
        printLine(CYAN, "╔", "═", "╗", w);

        String[] logo = {
            "  ██████╗██╗     ██╗███╗   ██╗██╗ ██████╗  ",
            " ██╔════╝██║     ██║████╗  ██║██║██╔════╝  ",
            " ██║     ██║     ██║██╔██╗ ██║██║██║       ",
            " ██║     ██║     ██║██║╚██╗██║██║██║       ",
            " ╚██████╗███████╗██║██║ ╚████║██║╚██████╗  ",
            "  ╚═════╝╚══════╝╚═╝╚═╝  ╚═══╝╚═╝ ╚═════╝  "
        };
        for (String line : logo) {
            printRow(CYAN, MAGENTA + BOLD + line + RESET, w);
        }
        printRow(CYAN, centerText(GREEN + BOLD + "C A R E   H U B   S Y S T E M" + RESET, w, 30), w);
        System.out.println(CYAN + "║" + RESET + "  " + GRAY + "─".repeat(w - 3) + RESET + "  " + CYAN + "║" + RESET);
        printRow(CYAN, GRAY + "  \uD83D\uDC64 Dokter  : " + WHITE + dokter.getNama() + " (" + dokter.getSpesialisasi() + ")" + RESET, w);
        printRow(CYAN, GRAY + "  \uD83C\uDFE5 Perawat : " + WHITE + perawat.getNama() + " (Shift " + perawat.getShift() + ")" + RESET, w);
        printLine(CYAN, "╚", "═", "╝", w);
        System.out.println();
    }

    private static String centerText(String coloredText, int totalWidth, int visibleLen) {
        int pad = (totalWidth - visibleLen) / 2;
        return " ".repeat(Math.max(0, pad)) + coloredText + " ".repeat(Math.max(0, totalWidth - visibleLen - pad));
    }

    // ─── Tampilan Menu Utama ─────────────────────────────────────────────────────

    private static void printMenu(Queue<Pasien> antrean) {
        int w = 39;
        printLine(YELLOW, "┌", "─", "┐", w);
        printRow(YELLOW, centerText(WHITE + BOLD + "MENU UTAMA" + RESET, w - 1, 10), w - 1);
        printDivider(YELLOW, w);
        printRow(YELLOW, "  " + GREEN  + "[1]" + RESET + WHITE + " Pendaftaran Pasien Baru" + RESET, w - 1);
        printRow(YELLOW, "  " + BLUE   + "[2]" + RESET + WHITE + " Panggil & Periksa Pasien" + RESET, w - 1);
        printRow(YELLOW, "  " + CYAN   + "[3]" + RESET + WHITE + " Lihat Semua Rekam Medis" + RESET, w - 1);
        printRow(YELLOW, "  " + RED    + "[4]" + RESET + WHITE + " Keluar" + RESET, w - 1);
        printLine(YELLOW, "└", "─", "┘", w);
        String antreanInfo = antrean.isEmpty()
            ? GRAY + "  Antrean saat ini: " + RED + "Kosong" + RESET
            : GRAY + "  Antrean saat ini: " + GREEN + BOLD + antrean.size() + " pasien" + RESET;
        System.out.println(antreanInfo);
        System.out.println();
        System.out.print(GREEN + "  ➤ Pilih menu: " + WHITE);
    }

    // ─── Tampilan Section Header ─────────────────────────────────────────────────

    private static void printSectionHeader(String title, String color) {
        System.out.println();
        System.out.println(color + "┌─── " + BOLD + title + RESET + color + " " + "─".repeat(Math.max(0, 45 - title.length())) + "┐" + RESET);
        System.out.println();
    }

    private static void printSectionFooter(String color) {
        System.out.println();
        System.out.println(color + "└" + "─".repeat(50) + "┘" + RESET);
        System.out.println();
    }

    // ─── Input Helper dengan Label ───────────────────────────────────────────────

    private static String inputField(Scanner sc, String label) {
        System.out.print(GRAY + "  │  " + YELLOW + label + RESET + WHITE);
        String val = sc.nextLine();
        System.out.print(RESET);
        return val;
    }

    // ─── Pesan Status ────────────────────────────────────────────────────────────

    private static void printSuccess(String msg) {
        System.out.println("\n  " + GREEN + "✔ " + BOLD + msg + RESET);
    }

    private static void printError(String msg) {
        System.out.println("\n  " + RED + "✘ " + msg + RESET);
    }

    private static void printInfo(String msg) {
        System.out.println("\n  " + BLUE + "ℹ " + msg + RESET);
    }

    private static void pause(Scanner sc) {
        System.out.println();
        System.out.print(GRAY + "  Tekan Enter untuk melanjutkan..." + RESET);
        sc.nextLine();
    }

    // ─── Main ────────────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        Queue<Pasien> antreanKlinik = new LinkedList<>();
        PasienMapper pasienMapper = new PasienMapper();
        RekamMedisMapper rekamMedisMapper = new RekamMedisMapper();
        Scanner scanner = new Scanner(System.in);

        Dokter dokterAktif = new Dokter(1, "dr. Joshua", "Umum");
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
                    printSectionHeader("PENDAFTARAN PASIEN BARU", GREEN);

                    String nama = inputField(scanner, "Nama Pasien    : ");

                    int umur = 0;
                    String umurStr = inputField(scanner, "Umur Pasien    : ");
                    try {
                        umur = Integer.parseInt(umurStr);
                    } catch (NumberFormatException e) {
                        printError("Umur tidak valid. Menggunakan default 0.");
                    }

                    Pasien pasienBaru = new Pasien(0, nama, umur);
                    Pasien pasienTerdaftar = pasienMapper.insertPasien(pasienBaru);

                    if (pasienTerdaftar.getId() != 0) {
                        antreanKlinik.add(pasienTerdaftar);
                        printSuccess("Pasien " + WHITE + BOLD + pasienTerdaftar.getNama() + RESET + GREEN + " berhasil didaftarkan!");
                        printInfo("ID Pasien: " + WHITE + BOLD + "#" + pasienTerdaftar.getId() + RESET + BLUE + "  |  Posisi antrean: #" + antreanKlinik.size());
                    } else {
                        printError("Gagal mendaftarkan pasien ke database.");
                    }
                    printSectionFooter(GREEN);
                    pause(scanner);
                    break;

                case "2":
                    clearScreen();
                    printSectionHeader("PEMERIKSAAN PASIEN", BLUE);

                    Pasien pasienDiperiksa = antreanKlinik.poll();
                    if (pasienDiperiksa != null) {
                        System.out.println("  " + BLUE + BOLD + "Pasien: " + WHITE + pasienDiperiksa.getNama() + RESET);
                        System.out.println();
                        System.out.println("  " + GRAY + "── Data Vital (diisi Perawat) ──────────────────");
                        System.out.println();

                        String suhuStr    = inputField(scanner, "Suhu (°C)      : ");
                        String tensi      = inputField(scanner, "Tensi          : ");
                        String bbStr      = inputField(scanner, "Berat Badan    : ");
                        String tbStr      = inputField(scanner, "Tinggi Badan   : ");
                        String keluhan    = inputField(scanner, "Keluhan        : ");

                        System.out.println();
                        System.out.println("  " + GRAY + "── Data Medis (diisi Dokter) ───────────────────");
                        System.out.println();

                        String diagnosis  = inputField(scanner, "Diagnosis      : ");
                        String resepObat  = inputField(scanner, "Resep Obat     : ");

                        double suhu       = parseDouble(suhuStr);
                        double beratBadan = parseDouble(bbStr);
                        double tinggiBadan = parseDouble(tbStr);

                        RekamMedis rm = new RekamMedis(
                            0,
                            pasienDiperiksa,
                            dokterAktif,
                            perawatAktif,
                            suhu,
                            tensi,
                            beratBadan,
                            tinggiBadan,
                            keluhan,
                            diagnosis,
                            resepObat
                        );

                        rekamMedisMapper.insertRekamMedis(rm);
                        printSuccess("Rekam medis berhasil disimpan.");
                    } else {
                        printError("Antrean kosong. Tidak ada pasien yang dapat dipanggil.");
                    }
                    printSectionFooter(BLUE);
                    pause(scanner);
                    break;

                case "3":
                    clearScreen();
                    printSectionHeader("RIWAYAT REKAM MEDIS", CYAN);
                    System.out.println();
                    rekamMedisMapper.tampilkanSemuaRekamMedis();
                    printSectionFooter(CYAN);
                    pause(scanner);
                    break;

                case "4":
                    clearScreen();
                    System.out.println();
                    System.out.println(CYAN + "  ╔══════════════════════════════════╗" + RESET);
                    System.out.println(CYAN + "  ║" + RESET + GREEN + BOLD + "   Terima kasih! Sampai jumpa. 👋  " + RESET + CYAN + "║" + RESET);
                    System.out.println(CYAN + "  ╚══════════════════════════════════╝" + RESET);
                    System.out.println();
                    isRunning = false;
                    break;

                default:
                    printError("Menu tidak tersedia. Silakan pilih 1–4.");
                    pause(scanner);
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