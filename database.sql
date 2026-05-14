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
-- MEMASUKKAN DATA AWAL (Hanya 1x agar ID Pas & Tidak Double)
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
-- VERIFIKASI (Opsional: Menampilkan hasil akhir tabel)
-- ==============================================================================
SELECT * FROM dokter;
SELECT * FROM perawat;
SELECT * FROM pasien;
SELECT * FROM rekam_medis;

-- ==============================================================================
-- QUERY TAMBAHAN: Menampilkan Rekam Medis Lengkap dengan Join
-- ==============================================================================
SELECT 
    rm.id AS id_rm,
    p.nama AS nama_pasien,
    p.umur,
    d.nama AS nama_dokter,
    pr.nama AS nama_perawat,
    rm.keluhan,
    rm.diagnosis,
    rm.resep_obat
FROM rekam_medis rm
JOIN pasien p ON rm.pasien_id = p.id
JOIN dokter d ON rm.dokter_id = d.id
JOIN perawat pr ON rm.perawat_id = pr.id;