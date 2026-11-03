@echo off
setlocal
cd /d "%~dp0"
title Backend Portal Polinema
if not defined DB_USERNAME set "DB_USERNAME=postgres"
if not defined DB_PASSWORD set "DB_PASSWORD=12345678"

echo ===== [1/4] Cek Java =====
where javac >nul 2>nul
if errorlevel 1 goto nojdk
for /f "delims=" %%i in ('where javac') do set "JC=%%i"
for %%i in ("%JC%\..\..") do set "JAVA_HOME=%%~fi"
set "PATH=%JAVA_HOME%\bin;%PATH%"
echo JAVA_HOME dipakai: %JAVA_HOME%
javac -version
echo.

echo ===== [2/4] Cek PostgreSQL di port 5432 =====
powershell -NoProfile -Command "if((Test-NetConnection 127.0.0.1 -Port 5432 -WarningAction SilentlyContinue).TcpTestSucceeded){exit 0}else{exit 1}"
if errorlevel 1 goto nopg
echo PostgreSQL aktif.
echo.

echo ===== [3/4] Cek database portal_polinema =====
set "PSQL="
for /d %%d in ("%ProgramFiles%\PostgreSQL\*") do if exist "%%d\bin\psql.exe" set "PSQL=%%d\bin\psql.exe"
if not defined PSQL for /f "delims=" %%p in ('where psql 2^>nul') do set "PSQL=%%p"
if not defined PSQL goto nopsql
set "PGPASSWORD=%DB_PASSWORD%"
"%PSQL%" -h 127.0.0.1 -U %DB_USERNAME% -d postgres -tAc "SELECT 1 FROM pg_database WHERE datname='portal_polinema'" 2>nul | findstr 1 >nul
if not errorlevel 1 goto dbok
echo Database belum ada, membuat portal_polinema ...
"%PSQL%" -h 127.0.0.1 -U %DB_USERNAME% -d postgres -c "CREATE DATABASE portal_polinema"
if errorlevel 1 goto dbfail
:dbok
echo Database portal_polinema siap.
echo.
goto runapp

:nopsql
echo [INFO] psql tidak ditemukan, pengecekan database dilewati.
echo        Pastikan database portal_polinema sudah dibuat di pgAdmin.
echo.

:runapp
echo ===== [4/4] Menjalankan backend =====
echo Tunggu sampai muncul "Started BackendApplication". Jangan tutup jendela ini.
echo.
powershell -NoProfile -Command "cmd /c \"mvnw.cmd spring-boot:run 2>&1\" | Tee-Object -FilePath log-backend.txt"
echo.
echo Backend berhenti. Baca pesan ERROR di atas.
echo Seluruh log tersimpan di file: log-backend.txt (kirim file ini jika perlu bantuan).
goto end

:nojdk
echo [ERROR] javac tidak ditemukan. Install JDK (bukan hanya JRE) lalu buka jendela baru.
goto end

:nopg
echo [ERROR] PostgreSQL tidak aktif di port 5432.
echo         Buka "Services" Windows, cari "postgresql-x64-xx", lalu klik Start.
goto end

:dbfail
echo [ERROR] Gagal membuat database. Kemungkinan password user "%DB_USERNAME%" bukan "%DB_PASSWORD%".
echo         Buat manual di pgAdmin: database bernama portal_polinema.
goto end

:end
echo.
pause
