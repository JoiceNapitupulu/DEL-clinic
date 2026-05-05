-- Buat tabel Orang yang bertindak sebagai tabel dasar (opsional, karena Data Mapper bisa langsung tabel per entitas)
-- Dalam desain ini, kita buat tabel terpisah untuk setiap peran (Pasien, Dokter, Perawat)
-- untuk menyederhanakan ORM tanpa framework.

CREATE TABLE pasien (
    id SERIAL PRIMARY KEY,
    nama VARCHAR(255) NOT NULL,
    umur INT NOT NULL
);

CREATE TABLE dokter (
    id SERIAL PRIMARY KEY,
    nama VARCHAR(255) NOT NULL,
    spesialisasi VARCHAR(255) NOT NULL
);

CREATE TABLE perawat (
    id SERIAL PRIMARY KEY,
    nama VARCHAR(255) NOT NULL,
    shift VARCHAR(50) NOT NULL
);

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
    CONSTRAINT fk_pasien FOREIGN KEY (pasien_id) REFERENCES pasien(id),
    CONSTRAINT fk_dokter FOREIGN KEY (dokter_id) REFERENCES dokter(id),
    CONSTRAINT fk_perawat FOREIGN KEY (perawat_id) REFERENCES perawat(id)
);

-- Insert dummy data untuk dokter dan perawat agar ID 1 tersedia untuk simulasi
INSERT INTO dokter (nama, spesialisasi) VALUES 
('dr. Joshua', 'Umum');

INSERT INTO perawat (nama, shift) VALUES 
('Suster Joice', 'Pagi'), ('Suster Winda', 'Siang'), ('Suster Rahel', 'Malam');
