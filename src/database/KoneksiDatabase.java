package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Koneksi database SQLite menggunakan JDBC
 */
public class KoneksiDatabase {

  public static Connection connect() {

    try {

      String url = "jdbc:sqlite:perpustakaan.db";

      Connection conn = DriverManager.getConnection(url);

      initTable(conn);

      return conn;

    } catch (Exception e) {

      System.out.println("Koneksi gagal : " + e.getMessage());

      return null;

    }

  }

  private static void initTable(Connection conn) throws Exception {

    String sql = "CREATE TABLE IF NOT EXISTS buku (" +
        "id INTEGER PRIMARY KEY, " +
        "judul TEXT NOT NULL, " +
        "penulis TEXT NOT NULL, " +
        "genre TEXT" +
        ")";

    Statement stmt = conn.createStatement();
    stmt.execute(sql);
    stmt.close();

  }

}
