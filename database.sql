-- ==============================================================================
-- 1. MEMBERSIHKAN DATABASE (Mencegah Data Double & Reset ID ke 1)
-- ==============================================================================
DROP TABLE IF EXISTS rekam_medis CASCADE;
DROP TABLE IF EXISTS pasien CASCADE;
DROP TABLE IF EXISTS dokter CASCADE;
DROP TABLE IF EXISTS perawat CASCADE;

-- ==============================================================================
-- 2. MEMBUAT ULANG STRUKTUR TABEL (Sesuai dengan Model & Mapper Java)
-- ==============================================================================

-- Tabel Pasien
CREATE TABLE pasien (
    id SERIAL PRIMARY KEY,
    nama VARCHAR(255) NOT NULL,
    umur INT NOT NULL
);

-- Tabel Dokter
CREATE TABLE dokter (
    id SERIAL PRIMARY KEY,
    nama VARCHAR(255) NOT NULL,
    spesialisasi VARCHAR(255) NOT NULL
);

-- Tabel Perawat
CREATE TABLE perawat (
    id SERIAL PRIMARY KEY,
    nama VARCHAR(255) NOT NULL,
    shift VARCHAR(50) NOT NULL
);

-- Tabel Rekam Medis (Sudah termasuk kolom untuk Surat Sakit & Foreign Keys)
CREATE TABLE rekam_medis (
    id SERIAL PRIMARY KEY,
    pasien_id INT NOT NULL,
    dokter_id INT NOT NULL,
    perawat_id INT NOT NULL,
    suhu DOUBLE PRECISION,
    tensi VARCHAR(50),
    berat_badan DOUBLE PRECISION,
    tinggi_badan DOUBLE PRECISION,
    keluhan TEXT,
    diagnosis TEXT,
    resep_obat TEXT,
    
    -- Relasi antar tabel
    CONSTRAINT fk_pasien FOREIGN KEY (pasien_id) REFERENCES pasien(id) ON DELETE CASCADE,
    CONSTRAINT fk_dokter FOREIGN KEY (dokter_id) REFERENCES dokter(id) ON DELETE CASCADE,
    CONSTRAINT fk_perawat FOREIGN KEY (perawat_id) REFERENCES perawat(id) ON DELETE CASCADE
);

-- ==============================================================================
-- 3. MEMASUKKAN DATA AWAL (Hanya 1x agar ID Pas & Tidak Double)
-- ==============================================================================

-- Data Dokter Aktif (Sesuai dengan Dokter di Main.java)
INSERT INTO dokter (nama, spesialisasi) VALUES 
('dr. Joshua', 'Umum');

-- Data Perawat Aktif (Sesuai dengan Perawat di Main.java)
INSERT INTO perawat (nama, shift) VALUES 
('Suster Joice', 'Pagi'), 
('Suster Winda', 'Siang'), 
('Suster Rahel', 'Malam');

-- ==============================================================================
-- 4. VERIFIKASI (Opsional: Menampilkan hasil akhir tabel)
-- ==============================================================================
SELECT * FROM dokter;
SELECT * FROM perawat;