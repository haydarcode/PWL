package com.portalpolinema.backend.berita;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Body JSON untuk Create/Update berita. */
@Getter
@Setter
@NoArgsConstructor
public class BeritaRequest {
    private String judul;
    private String deskripsi;
    private Berita.Kategori kategori; // Akademik | Kemahasiswaan | Umum
    private LocalDate tanggal; // format: yyyy-MM-dd
    private String file; // opsional, contoh: /uploads/berita/nama.jpg
}
