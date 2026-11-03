# Backend Portal Polinema

## Menjalankan aplikasi
1. Pastikan PostgreSQL aktif dan database `portal_polinema` tersedia.
2. Periksa kredensial database di `src/main/resources/application.properties`.
3. Jalankan aplikasi dengan JDK 21 atau lebih baru (21/25/26 semuanya bisa):
   - Windows: `mvnw.cmd spring-boot:run`
   - Linux/macOS: `./mvnw spring-boot:run`

## Akun demo login lokal
Saat `app.bootstrap-admin.enabled=true`, aplikasi membuat akun demo jika email belum ada:
- Email: `admin@polinema.ac.id`
- Password: `Admin123!`
- Role: `super_admin`

Akun dibuat dengan BCrypt dan tidak dibuat ulang jika email sudah terdaftar.
**Penting:** ini hanya untuk pengujian lokal. Ubah password dan set `app.bootstrap-admin.enabled=false` sebelum deployment.

## Pengujian Login dan CRUD
Buka `test.http` di VS Code dengan ekstensi REST Client.
Jalankan request secara berurutan dari atas: Login, Read, Create, Read detail, Update, Delete.
Token dan ID user baru diambil otomatis dari respons request sebelumnya.

## Catatan
- Endpoint `/api/berita` pada kode saat ini hanya menyediakan GET, detail, slug, increment view, dan daftar approval request. Belum ada POST/PUT/DELETE Berita.
- Untuk demonstrasi CRUD yang memang tersedia, file `test.http` menggunakan resource `/api/user`.
- Field password pada entity `User` disembunyikan dari respons JSON.

## Langkah cepat (Windows)
1. Install JDK 21+ dan PostgreSQL. Pastikan service PostgreSQL berjalan.
2. Buat database: jalankan `create_database.sql` (atau `CREATE DATABASE portal_polinema;`).
3. Jika user/password PostgreSQL bukan `postgres` / `12345678`, ubah di `application.properties`
   atau set environment variable `DB_USERNAME` dan `DB_PASSWORD`.
4. Double-klik `jalankan.bat` (atau `./jalankan.sh`). Tunggu tulisan `Started BackendApplication`
   dan `Akun demo admin berhasil dibuat`. Jangan tutup jendela ini.
5. Buka folder ini di VS Code, install ekstensi REST Client, buka `test.http`, klik **Send Request**
   mulai dari request 1 (LOGIN) lalu berurutan sampai DELETE.

## Jika muncul "connection was rejected"
Backend belum jalan atau crash. Lihat jendela backend: error `5432` berarti PostgreSQL belum aktif,
`password authentication failed` berarti password DB salah, `does not exist` berarti database belum dibuat.

## Catatan penting
- Jalankan HANYA lewat `jalankan.bat` (atau `mvnw.cmd spring-boot:run`). Jangan pakai tombol Run di VS Code,
  karena memakai compiler lain dan bisa menghasilkan hasil build yang tidak lengkap.
- Startup pertama sekitar 30-60 detik. Tunggu sampai muncul `Started BackendApplication`.
- Spring Boot 4 memakai Jackson 3; bean ObjectMapper (Jackson 2) untuk kode lama didaftarkan di
  `config/JacksonCompatConfig.java`.
- Log tersimpan di `log-backend.txt`.

## CRUD Berita
Endpoint `/api/berita`: GET (publik), POST, PUT `/{id}`, DELETE `/{id}` (wajib login).
Body JSON: judul, deskripsi, kategori (Akademik/Kemahasiswaan/Umum), tanggal (yyyy-MM-dd), file (opsional).
Tes lengkap ada di `test-berita.http`.
