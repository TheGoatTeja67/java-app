# Sistem Perpustakaan

Aplikasi sederhana untuk mengelola data buku di perpustakaan. Program ini dibuat menggunakan Java dan SQLite sebagai database.

## Fitur

- Tambah buku baru (Fiksi atau Non-Fiksi)
- Tampilkan semua buku
- Cari buku berdasarkan judul
- Update data buku
- Hapus buku
<<<<<<< HEAD
- **Antarmuka GUI** (Java Swing) dan Console

## Demo Program

Video demo penggunaan aplikasi:

[![Demo Sistem Perpustakaan](https://img.youtube.com/vi/VIDEO_ID_DISINI/0.jpg)](https://www.youtube.com/watch?v=VIDEO_ID_DISINI)

Klik gambar di atas untuk menonton video demo program.
=======
>>>>>>> 8607eef527fc2152e39386265216071e2389006c

## Kebutuhan Sistem

- Java Development Kit (JDK) versi 8 atau lebih baru
- Terminal atau Command Prompt

## Download Repository

Ada beberapa cara untuk mendapatkan source code proyek ini dari GitHub:

### Cara 1: Download ZIP

1. Buka halaman repository di GitHub
2. Klik tombol hijau bertuliskan "Code"
3. Pilih "Download ZIP"
4. Setelah download selesai, extract file ZIP ke folder yang diinginkan
5. Buka folder hasil extract, di dalamnya ada semua file proyek

### Cara 2: Clone dengan Git

Kalau sudah install Git, bisa clone repository dengan cara:

```bash
git clone https://github.com/username/repository-name.git
cd repository-name
```

Ganti `username/repository-name` dengan nama user dan repository yang sebenarnya.

### Cara 3: Clone dengan GitHub Desktop

1. Download dan install GitHub Desktop dari desktop.github.com
2. Buka aplikasi GitHub Desktop
3. Klik File > Clone Repository
4. Pilih tab URL, masukkan URL repository
5. Pilih folder tujuan untuk menyimpan proyek
6. Klik Clone

Setelah berhasil download dengan salah satu cara di atas, bisa langsung lanjut ke langkah instalasi Java di bawah.

## Instalasi Java

### Windows

1. Download JDK dari website Oracle atau adoptium.net
2. Jalankan installer yang sudah didownload
3. Ikuti petunjuk instalasi (biasanya tinggal Next, Next, Install)
4. Setelah selesai, buka Command Prompt dan cek instalasi dengan perintah:
   ```
   java -version
   ```
5. Jika muncul versi Java, berarti instalasi berhasil
6. Kalau belum muncul atau error, tambahkan Java ke PATH:
   - Klik kanan "This PC" > Properties > Advanced system settings
   - Klik "Environment Variables"
   - Di System variables, cari "Path", klik Edit
   - Klik New, tambahkan path ke folder bin JDK (contoh: C:\Program Files\Java\jdk-17\bin)
   - Klik OK semua, tutup Command Prompt dan buka lagi

### Linux

```bash
sudo apt update
sudo apt install default-jdk
java -version
```

### MacOS

```bash
brew install openjdk
java -version
```

## Struktur Proyek

```
testoss/
├── lib/
│   ├── sqlite-jdbc-3.51.2.0.jar      # Driver database SQLite
│   └── mysql-connector-j-9.6.0.jar   # Driver database MySQL
├── src/
│   ├── database/
│   │   └── KoneksiDatabase.java      # Koneksi ke database
│   ├── main/
<<<<<<< HEAD
│   │   └── MainApp.java              # Program utama (Console)
│   ├── ui/
│   │   └── MainGUI.java              # Program utama (GUI)
=======
│   │   └── MainApp.java              # Program utama
>>>>>>> 8607eef527fc2152e39386265216071e2389006c
│   ├── model/
│   │   ├── Buku.java                 # Class buku
│   │   ├── BukuFiksi.java            # Class buku fiksi
│   │   └── BukuNonFiksi.java         # Class buku non-fiksi
│   └── service/
│       ├── PerpustakaanService.java  # Interface service
│       └── PerpustakaanServiceImpl.java  # Implementasi service
<<<<<<< HEAD
├── compile.bat                       # Script compile (Windows)
├── run-gui.bat                       # Script jalankan GUI (Windows)
├── run-console.bat                   # Script jalankan Console (Windows)
=======
>>>>>>> 8607eef527fc2152e39386265216071e2389006c
└── perpustakaan.db                   # Database SQLite (otomatis dibuat)
```

## Cara Menjalankan Program

<<<<<<< HEAD
Ada **2 versi** aplikasi yang bisa dijalankan:

1. **Versi GUI** (Tampilan grafis dengan Java Swing) - **Direkomendasikan!**
2. **Versi Console** (Tampilan text di terminal)

### Cara Cepat (Windows)

1. **Compile Program:**
   - Double click `compile.bat`
   - Tunggu sampai selesai

2. **Jalankan Program:**
   - Double click `run-gui.bat` untuk versi GUI
   - Double click `run-console.bat` untuk versi Console

### Cara Manual

#### 1. Compile Program
=======
### 1. Compile Program
>>>>>>> 8607eef527fc2152e39386265216071e2389006c

Buka terminal/command prompt di folder proyek, lalu jalankan:

**Windows:**

```cmd
<<<<<<< HEAD
javac -encoding UTF-8 -cp "lib/*" -d out src/database/*.java src/model/*.java src/service/*.java src/ui/*.java src/main/*.java
=======
javac -cp "lib/*" -d bin src/database/*.java src/model/*.java src/service/*.java src/main/*.java
>>>>>>> 8607eef527fc2152e39386265216071e2389006c
```

**Linux/MacOS:**

```bash
<<<<<<< HEAD
javac -encoding UTF-8 -cp "lib/*" -d out src/database/*.java src/model/*.java src/service/*.java src/ui/*.java src/main/*.java
```

Perintah ini akan membuat folder `out` dan compile semua file Java ke dalamnya.

#### 2. Jalankan Program

**Untuk Versi GUI (Tampilan Grafis):**

Windows:

```cmd
java -cp "out;lib/*" ui.MainGUI
```

Linux/MacOS:

```bash
java -cp "out:lib/*" ui.MainGUI
```

**Untuk Versi Console (Tampilan Text):**

Windows:

```cmd
java -cp "out;lib/*" main.MainApp
```

Linux/MacOS:

```bash
java -cp "out:lib/*" main.MainApp
```

## Tampilan Aplikasi

### Versi GUI (Java Swing)

Aplikasi GUI memiliki fitur:

- 📊 **Tabel data buku** - Lihat semua buku dalam bentuk tabel
- ➕ **Form tambah buku** - Input data buku baru
- ✏️ **Update buku** - Klik baris di tabel untuk edit
- 🗑️ **Hapus buku** - Hapus dengan konfirmasi
- 🔍 **Pencarian** - Cari buku berdasarkan judul
- ✅ **Validasi input** - Otomatis cek data yang diinput

![Screenshot GUI](screenshot-gui.png) _(Tambahkan screenshot jika ada)_

### Versi Console
=======
javac -cp "lib/*" -d bin src/database/*.java src/model/*.java src/service/*.java src/main/*.java
```

Perintah ini akan membuat folder `bin` dan compile semua file Java ke dalamnya.

### 2. Jalankan Program

Setelah compile berhasil, jalankan program dengan perintah:

**Windows:**

```cmd
java -cp "bin;lib/*" main.MainApp
```

**Linux/MacOS:**

```bash
java -cp "bin:lib/*" main.MainApp
```

### 3. Cara Pakai
>>>>>>> 8607eef527fc2152e39386265216071e2389006c

Setelah program jalan, akan muncul menu seperti ini:

```
===== MENU PERPUSTAKAAN =====
1 Tambah Buku
2 Tampilkan Buku
3 Cari Buku
4 Update Buku
5 Hapus Buku
6 Keluar
Pilih Menu :
```

Tinggal ketik angka menu yang diinginkan dan tekan Enter.

## Catatan

- Database `perpustakaan.db` akan dibuat otomatis saat program pertama kali dijalankan
- Semua data buku tersimpan di database ini
- Jika ada error saat compile, pastikan semua file .jar di folder lib sudah ada
- Kalau mau mulai dari awal, hapus file perpustakaan.db

## Troubleshooting

**Error: javac not found**

- Java belum terinstall atau belum masuk ke PATH
- Cek dengan `java -version` dan `javac -version`
- Kalau java -version bisa tapi javac tidak, install JDK (bukan JRE)

**Error: ClassNotFoundException**

- Pastikan path ke lib sudah benar saat menjalankan program
- Cek apakah file .jar ada di folder lib

**Error: Could not find or load main class**

- Pastikan sudah compile dulu sebelum run
<<<<<<< HEAD
- Cek apakah folder out sudah terbuat dan ada file .class di dalamnya

**Error: No suitable driver found for jdbc:sqlite**

- Pastikan file `sqlite-jdbc-3.51.2.0.jar` ada di folder `lib/`
- Pastikan classpath sudah include `lib/*` saat compile dan run

**GUI tidak muncul / langsung close**

- Cek apakah ada error di terminal
- Pastikan semua file sudah di-compile dengan benar
- Coba jalankan dari terminal untuk melihat error message

## Perbedaan GUI vs Console

| Fitur         | GUI (MainGUI)             | Console (MainApp) |
| ------------- | ------------------------- | ----------------- |
| Tampilan      | Grafis dengan window      | Text di terminal  |
| Input data    | Form & text field         | Ketik di terminal |
| Lihat data    | Tabel interaktif          | Print ke terminal |
| Pencarian     | Live search dengan filter | Input manual      |
| Validasi      | Otomatis & popup warning  | Manual cek        |
| User-friendly | ⭐⭐⭐⭐⭐                | ⭐⭐⭐            |
| Mudah dipakai | Klik & isi form           | Ketik angka menu  |

**Rekomendasi:** Gunakan **versi GUI** untuk pengalaman yang lebih baik!
=======
- Cek apakah folder bin sudah terbuat dan ada file .class di dalamnya
>>>>>>> 8607eef527fc2152e39386265216071e2389006c
