# DEL Clinic Management System

DEL Clinic Management adalah sebuah aplikasi *console-based* berbasis Java yang digunakan untuk mengelola antrean pasien dan rekam medis di sebuah klinik. Aplikasi ini menerapkan prinsip Object-Oriented Programming (OOP) tingkat lanjut, Java Collection Framework (Queue) untuk antrean, dan pure JDBC dengan arsitektur *Data Mapper* untuk menyimpan data secara persisten ke database PostgreSQL.

## Fitur Utama

1. **Pendaftaran Pasien Baru**: Menambahkan pasien baru ke dalam antrean (Queue).
2. **Panggil & Periksa Pasien**: Memanggil pasien dari antrean terdepan, mendata keluhan dan diagnosis, lalu menyimpannya sebagai Rekam Medis.
3. **Lihat Semua Rekam Medis**: Menampilkan riwayat rekam medis seluruh pasien dari database.
4. **Ganti Shift Perawat**: Mengubah perawat yang sedang berjaga (Pagi, Siang, Malam).

## Persyaratan Sistem

- **Java Development Kit (JDK)** 17 atau lebih baru
- **PostgreSQL** Server
- **PostgreSQL JDBC Driver** (diletakkan di dalam folder `lib/` atau dikonfigurasi pada classpath)

## Cara Instalasi dan Konfigurasi

1. **Siapkan Database:**
   - Buat database baru di PostgreSQL (misal: `del_clinic`).
   - Eksekusi file `database.sql` pada database tersebut untuk membuat tabel `pasien`, `dokter`, `perawat`, dan `rekam_medis`, serta mengisi data awal dokter dan perawat.
2. **Konfigurasi Koneksi (Opsional):**
   - Pastikan username, password, dan URL JDBC di dalam program (pada `klinik.driver` atau kelas *Connection/Mapper*) sudah sesuai dengan konfigurasi PostgreSQL Anda.
3. **Kompilasi dan Jalankan:**
   - Kompilasi program dengan menyertakan JDBC driver.
   - Jalankan `klinik.main.Main`.

---

## Contoh Input dan Output (Terminal)

Saat pertama kali dijalankan, sistem akan menampilkan Banner dan Menu Utama.

```text
╔════════════════════════════════════════════════════════════════╗
║                                                                ║
║      D E L  C L I N I C  M A N A G E M E N T                   ║
║                                                                ║
╠════════════════════════════════════════════════════════════════╣
║   Dokter  : dr. Joshua (Umum)                                  ║
║   Perawat : Suster Joice (Shift Pagi)                          ║
╚════════════════════════════════════════════════════════════════╝

╔════════════════════════════════════════╗
║               MENU UTAMA               ║
╠════════════════════════════════════════╣
║  [1] Pendaftaran Pasien Baru           ║
║  [2] Panggil & Periksa Pasien          ║
║  [3] Lihat Semua Rekam Medis           ║
║  [4] Ganti Shift Perawat               ║
║  [5] Keluar                            ║
╚════════════════════════════════════════╝

  Antrean saat ini: Kosong
  ➤ Pilih menu: 1
```

### 1. Pendaftaran Pasien Baru
**Input:**
```text
  === PENDAFTARAN PASIEN BARU ===
  │ Nama Pasien : Budi Santoso
  │ Umur Pasien : 25
```
**Output:**
```text
  ✔ Pasien berhasil didaftarkan ke antrean.

  Tekan Enter...
```

### 2. Memeriksa Pasien (Panggil Antrean)
*Setelah kembali ke menu utama, antrean menjadi 1 Pasien.*
**Input:**
```text
  ➤ Pilih menu: 2
  
  === PEMERIKSAAN: Budi Santoso ===
  │ Keluhan   : Sakit kepala dan demam
  │ Diagnosis : Gejala Tipes
```
**Output:**
```text
  ✔ Data rekam medis disimpan.

  Tekan Enter...
```

### 3. Melihat Semua Rekam Medis
**Input:**
```text
  ➤ Pilih menu: 3
```
**Output:**
```text
  === RIWAYAT REKAM MEDIS ===
  ID RM: 1 | Pasien: Budi Santoso (25 thn)
  Dokter Pemeriksa : dr. Joshua
  Perawat          : Suster Joice
  Keluhan          : Sakit kepala dan demam
  Diagnosis        : Gejala Tipes
  Resep Obat       : Paracetamol
  --------------------------------------------------

  Tekan Enter...
```

### 4. Ganti Shift Perawat
**Input:**
```text
  ➤ Pilih menu: 4

  === GANTI SHIFT PERAWAT ===
  1. Suster Joice (Pagi)
  2. Suster Winda (Siang)
  3. Suster Rahel (Malam)

  Pilih (1-3): 2
```
**Output:**
```text
  ✔ Petugas shift diperbarui.

  Tekan Enter...
```
*(Saat kembali ke menu utama, nama Perawat akan berubah menjadi Suster Winda dengan Shift Siang)*
