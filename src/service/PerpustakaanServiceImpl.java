<<<<<<< HEAD
package service;

import database.KoneksiDatabase;
import model.Buku;
import model.BukuFiksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Implementasi operasi perpustakaan menggunakan SQLite
 */
public class PerpustakaanServiceImpl implements PerpustakaanService {

  Scanner input = new Scanner(System.in);

  // Method untuk UI - mengembalikan semua data buku
  public List<Object[]> getAllBukuData() {
    List<Object[]> dataBuku = new ArrayList<>();
    try (Connection conn = KoneksiDatabase.connect()) {
      String sql = "SELECT * FROM buku ORDER BY id";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        Object[] row = new Object[4];
        row[0] = rs.getInt("id");
        row[1] = rs.getString("judul");
        row[2] = rs.getString("penulis");
        row[3] = rs.getString("genre");
        dataBuku.add(row);
      }

      rs.close();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Gagal ambil data buku : " + e.getMessage());
    }
    return dataBuku;
  }

  // Method untuk UI - tambah buku dengan parameter
  public boolean tambahBukuUI(int id, String judul, String penulis, String genre) {
    try (Connection conn = KoneksiDatabase.connect()) {
      String sql = "INSERT INTO buku (id, judul, penulis, genre) VALUES (?, ?, ?, ?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);
      ps.setString(2, judul);
      ps.setString(3, penulis);
      ps.setString(4, genre == null || genre.trim().isEmpty() ? null : genre);
      ps.executeUpdate();
      ps.close();
      return true;
    } catch (Exception e) {
      System.out.println("Gagal tambah buku : " + e.getMessage());
      return false;
    }
  }

  // Method untuk UI - update buku
  public boolean updateBukuUI(int id, String judul, String penulis, String genre) {
    try (Connection conn = KoneksiDatabase.connect()) {
      String sql = "UPDATE buku SET judul = ?, penulis = ?, genre = ? WHERE id = ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, judul);
      ps.setString(2, penulis);
      ps.setString(3, genre == null || genre.trim().isEmpty() ? null : genre);
      ps.setInt(4, id);
      int rows = ps.executeUpdate();
      ps.close();
      return rows > 0;
    } catch (Exception e) {
      System.out.println("Gagal update buku : " + e.getMessage());
      return false;
    }
  }

  // Method untuk UI - hapus buku
  public boolean hapusBukuUI(int id) {
    try (Connection conn = KoneksiDatabase.connect()) {
      String sql = "DELETE FROM buku WHERE id = ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);
      int rows = ps.executeUpdate();
      ps.close();
      return rows > 0;
    } catch (Exception e) {
      System.out.println("Gagal hapus buku : " + e.getMessage());
      return false;
    }
  }

  // Method untuk UI - cari buku by judul
  public List<Object[]> cariBukuUI(String keyword) {
    List<Object[]> dataBuku = new ArrayList<>();
    try (Connection conn = KoneksiDatabase.connect()) {
      String sql = "SELECT * FROM buku WHERE LOWER(judul) LIKE LOWER(?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, "%" + keyword + "%");
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Object[] row = new Object[4];
        row[0] = rs.getInt("id");
        row[1] = rs.getString("judul");
        row[2] = rs.getString("penulis");
        row[3] = rs.getString("genre");
        dataBuku.add(row);
      }

      rs.close();
      ps.close();
    } catch (Exception e) {
      System.out.println("Gagal cari buku : " + e.getMessage());
    }
    return dataBuku;
  }

  // Method Overloading
  public void tambahBuku(int id, String judul, String penulis) {
    try (Connection conn = KoneksiDatabase.connect()) {
      String sql = "INSERT INTO buku (id, judul, penulis, genre) VALUES (?, ?, ?, NULL)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);
      ps.setString(2, judul);
      ps.setString(3, penulis);
      ps.executeUpdate();
      ps.close();
    } catch (Exception e) {
      if (e.getMessage() != null && e.getMessage().contains("SQLITE_CONSTRAINT")) {
        System.out.println("Gagal tambah buku : ID " + id + " sudah digunakan");
      } else {
        System.out.println("Gagal tambah buku : " + e.getMessage());
      }
    }
  }

  @Override
  public void tambahBuku() {

    System.out.print("Masukkan ID Buku : ");
    int id = input.nextInt();
    input.nextLine();

    System.out.print("Masukkan Judul Buku : ");
    String judul = input.nextLine();

    System.out.print("Masukkan Penulis : ");
    String penulis = input.nextLine();

    System.out.print("Masukkan Genre (kosongkan jika tidak ada) : ");
    String genre = input.nextLine();

    try (Connection conn = KoneksiDatabase.connect()) {
      String sql = "INSERT INTO buku (id, judul, penulis, genre) VALUES (?, ?, ?, ?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);
      ps.setString(2, judul);
      ps.setString(3, penulis);
      ps.setString(4, genre.isEmpty() ? null : genre);
      ps.executeUpdate();
      ps.close();
      System.out.println("Buku berhasil ditambahkan");
    } catch (Exception e) {
      if (e.getMessage() != null && e.getMessage().contains("SQLITE_CONSTRAINT")) {
        System.out.println("Gagal tambah buku : ID " + id + " sudah digunakan, silakan gunakan ID lain");
      } else {
        System.out.println("Gagal tambah buku : " + e.getMessage());
      }
    }

  }

  @Override
  public void tampilkanBuku() {

    try (Connection conn = KoneksiDatabase.connect()) {
      String sql = "SELECT * FROM buku";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      boolean ada = false;

      while (rs.next()) {
        ada = true;
        int id = rs.getInt("id");
        String judul = rs.getString("judul");
        String penulis = rs.getString("penulis");
        String genre = rs.getString("genre");

        Buku buku;
        if (genre != null && !genre.isEmpty()) {
          buku = new BukuFiksi(id, judul, penulis, genre);
        } else {
          buku = new Buku(id, judul, penulis);
        }

        buku.tampilkanInfo();
        System.out.println("------------------");
      }

      if (!ada) {
        System.out.println("Belum ada buku");
      }

      rs.close();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Gagal tampilkan buku : " + e.getMessage());
    }

  }

  @Override
  public void cariBuku() {

    System.out.print("Masukkan judul buku : ");
    String cari = input.nextLine();

    try (Connection conn = KoneksiDatabase.connect()) {
      String sql = "SELECT * FROM buku WHERE LOWER(judul) = LOWER(?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, cari);
      ResultSet rs = ps.executeQuery();

      boolean ditemukan = false;

      while (rs.next()) {
        ditemukan = true;
        int id = rs.getInt("id");
        String judul = rs.getString("judul");
        String penulis = rs.getString("penulis");
        String genre = rs.getString("genre");

        Buku buku;
        if (genre != null && !genre.isEmpty()) {
          buku = new BukuFiksi(id, judul, penulis, genre);
        } else {
          buku = new Buku(id, judul, penulis);
        }

        buku.tampilkanInfo();
      }

      if (!ditemukan) {
        System.out.println("Buku tidak ditemukan");
      }

      rs.close();
      ps.close();
    } catch (Exception e) {
      System.out.println("Gagal cari buku : " + e.getMessage());
    }

  }

  @Override
  public void updateBuku() {

    System.out.print("Masukkan ID buku yang akan diupdate : ");
    int id = input.nextInt();
    input.nextLine();

    try (Connection conn = KoneksiDatabase.connect()) {

      String check = "SELECT id FROM buku WHERE id = ?";
      PreparedStatement ps = conn.prepareStatement(check);
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();

      if (!rs.next()) {
        System.out.println("Buku tidak ditemukan");
        rs.close();
        ps.close();
        return;
      }

      rs.close();
      ps.close();

      System.out.print("Masukkan judul baru : ");
      String judulBaru = input.nextLine();

      System.out.print("Masukkan penulis baru : ");
      String penulisBaru = input.nextLine();

      String sql = "UPDATE buku SET judul = ?, penulis = ? WHERE id = ?";
      PreparedStatement update = conn.prepareStatement(sql);
      update.setString(1, judulBaru);
      update.setString(2, penulisBaru);
      update.setInt(3, id);
      update.executeUpdate();
      update.close();

      System.out.println("Data buku berhasil diupdate");

    } catch (Exception e) {
      System.out.println("Gagal update buku : " + e.getMessage());
    }

  }

  @Override
  public void hapusBuku() {

    System.out.print("Masukkan ID buku yang akan dihapus : ");
    int id = input.nextInt();

    try (Connection conn = KoneksiDatabase.connect()) {
      String sql = "DELETE FROM buku WHERE id = ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);
      int rows = ps.executeUpdate();
      ps.close();

      if (rows > 0) {
        System.out.println("Buku berhasil dihapus");
      } else {
        System.out.println("ID buku tidak ditemukan");
      }
    } catch (Exception e) {
      System.out.println("Gagal hapus buku : " + e.getMessage());
    }

  }

}
=======
package service;

import database.KoneksiDatabase;
import model.Buku;
import model.BukuFiksi;
import java.sql.*;
import java.util.Scanner;

/**
 * Implementasi operasi perpustakaan menggunakan SQLite
 */
public class PerpustakaanServiceImpl implements PerpustakaanService {

  Scanner input = new Scanner(System.in);

  // Method Overloading
  public void tambahBuku(int id, String judul, String penulis) {
    try (Connection conn = KoneksiDatabase.connect()) {
      String sql = "INSERT INTO buku (id, judul, penulis, genre) VALUES (?, ?, ?, NULL)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);
      ps.setString(2, judul);
      ps.setString(3, penulis);
      ps.executeUpdate();
      ps.close();
    } catch (Exception e) {
      if (e.getMessage() != null && e.getMessage().contains("SQLITE_CONSTRAINT")) {
        System.out.println("Gagal tambah buku : ID " + id + " sudah digunakan");
      } else {
        System.out.println("Gagal tambah buku : " + e.getMessage());
      }
    }
  }

  @Override
  public void tambahBuku() {

    System.out.print("Masukkan ID Buku : ");
    int id = input.nextInt();
    input.nextLine();

    System.out.print("Masukkan Judul Buku : ");
    String judul = input.nextLine();

    System.out.print("Masukkan Penulis : ");
    String penulis = input.nextLine();

    System.out.print("Masukkan Genre (kosongkan jika tidak ada) : ");
    String genre = input.nextLine();

    try (Connection conn = KoneksiDatabase.connect()) {
      String sql = "INSERT INTO buku (id, judul, penulis, genre) VALUES (?, ?, ?, ?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);
      ps.setString(2, judul);
      ps.setString(3, penulis);
      ps.setString(4, genre.isEmpty() ? null : genre);
      ps.executeUpdate();
      ps.close();
      System.out.println("Buku berhasil ditambahkan");
    } catch (Exception e) {
      if (e.getMessage() != null && e.getMessage().contains("SQLITE_CONSTRAINT")) {
        System.out.println("Gagal tambah buku : ID " + id + " sudah digunakan, silakan gunakan ID lain");
      } else {
        System.out.println("Gagal tambah buku : " + e.getMessage());
      }
    }

  }

  @Override
  public void tampilkanBuku() {

    try (Connection conn = KoneksiDatabase.connect()) {
      String sql = "SELECT * FROM buku";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      boolean ada = false;

      while (rs.next()) {
        ada = true;
        int id = rs.getInt("id");
        String judul = rs.getString("judul");
        String penulis = rs.getString("penulis");
        String genre = rs.getString("genre");

        Buku buku;
        if (genre != null && !genre.isEmpty()) {
          buku = new BukuFiksi(id, judul, penulis, genre);
        } else {
          buku = new Buku(id, judul, penulis);
        }

        buku.tampilkanInfo();
        System.out.println("------------------");
      }

      if (!ada) {
        System.out.println("Belum ada buku");
      }

      rs.close();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Gagal tampilkan buku : " + e.getMessage());
    }

  }

  @Override
  public void cariBuku() {

    System.out.print("Masukkan judul buku : ");
    String cari = input.nextLine();

    try (Connection conn = KoneksiDatabase.connect()) {
      String sql = "SELECT * FROM buku WHERE LOWER(judul) = LOWER(?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, cari);
      ResultSet rs = ps.executeQuery();

      boolean ditemukan = false;

      while (rs.next()) {
        ditemukan = true;
        int id = rs.getInt("id");
        String judul = rs.getString("judul");
        String penulis = rs.getString("penulis");
        String genre = rs.getString("genre");

        Buku buku;
        if (genre != null && !genre.isEmpty()) {
          buku = new BukuFiksi(id, judul, penulis, genre);
        } else {
          buku = new Buku(id, judul, penulis);
        }

        buku.tampilkanInfo();
      }

      if (!ditemukan) {
        System.out.println("Buku tidak ditemukan");
      }

      rs.close();
      ps.close();
    } catch (Exception e) {
      System.out.println("Gagal cari buku : " + e.getMessage());
    }

  }

  @Override
  public void updateBuku() {

    System.out.print("Masukkan ID buku yang akan diupdate : ");
    int id = input.nextInt();
    input.nextLine();

    try (Connection conn = KoneksiDatabase.connect()) {

      String check = "SELECT id FROM buku WHERE id = ?";
      PreparedStatement ps = conn.prepareStatement(check);
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();

      if (!rs.next()) {
        System.out.println("Buku tidak ditemukan");
        rs.close();
        ps.close();
        return;
      }

      rs.close();
      ps.close();

      System.out.print("Masukkan judul baru : ");
      String judulBaru = input.nextLine();

      System.out.print("Masukkan penulis baru : ");
      String penulisBaru = input.nextLine();

      String sql = "UPDATE buku SET judul = ?, penulis = ? WHERE id = ?";
      PreparedStatement update = conn.prepareStatement(sql);
      update.setString(1, judulBaru);
      update.setString(2, penulisBaru);
      update.setInt(3, id);
      update.executeUpdate();
      update.close();

      System.out.println("Data buku berhasil diupdate");

    } catch (Exception e) {
      System.out.println("Gagal update buku : " + e.getMessage());
    }

  }

  @Override
  public void hapusBuku() {

    System.out.print("Masukkan ID buku yang akan dihapus : ");
    int id = input.nextInt();

    try (Connection conn = KoneksiDatabase.connect()) {
      String sql = "DELETE FROM buku WHERE id = ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);
      int rows = ps.executeUpdate();
      ps.close();

      if (rows > 0) {
        System.out.println("Buku berhasil dihapus");
      } else {
        System.out.println("ID buku tidak ditemukan");
      }
    } catch (Exception e) {
      System.out.println("Gagal hapus buku : " + e.getMessage());
    }

  }

}
>>>>>>> 8607eef527fc2152e39386265216071e2389006c
