# PORTALKU - Spring Boot Fundamental

Project ini sudah digabung menjadi satu project berdasarkan Praktik 1, 2, dan 3 pada Modul Pemrograman Web Lanjut Pertemuan 2.

## Teknologi
- Spring Boot 3.5.7
- Java 21
- Maven
- Spring Web
- Validation
- Lombok
- DevTools

## Menjalankan di Windows / VS Code
Buka terminal pada folder project `portalku`, lalu:
```bat
mvnw.cmd spring-boot:run
```

Profil default adalah `dev` dan berjalan di port 8080.

## Endpoint
- GET `http://localhost:8080/api/halo`
- GET `http://localhost:8080/api/jurnal`
- GET `http://localhost:8080/api/jurnal/1`
- POST `http://localhost:8080/api/jurnal`
- DELETE `http://localhost:8080/api/jurnal/1`
- GET `http://localhost:8080/api/info`

Contoh body POST:
```json
{
  "judul": "JOIV",
  "penerbit": "Politeknik Negeri Padang",
  "tahunTerbit": 2017
}
```

## Profil production
```bat
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=prod
```
Production menggunakan port 9090.

## Build
```bat
mvnw.cmd clean package
```

Jalankan JAR:
```bat
java -jar target/portalku-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

Catatan: proses build/test di lingkungan ChatGPT tidak dapat diselesaikan karena Maven Wrapper membutuhkan akses internet untuk mengunduh Maven. Source code dan konfigurasi sudah dimasukkan ke project.
