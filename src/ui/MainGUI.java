package ui;

import service.PerpustakaanServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * GUI Sistem Perpustakaan menggunakan Java Swing
 */
public class MainGUI extends JFrame {

  private JTable tableBuku;
  private DefaultTableModel tableModel;
  private PerpustakaanServiceImpl service;

  private JTextField txtId;
  private JTextField txtJudul;
  private JTextField txtPenulis;
  private JTextField txtGenre;
  private JTextField txtCari;

  private JButton btnTambah;
  private JButton btnUpdate;
  private JButton btnHapus;
  private JButton btnRefresh;
  private JButton btnCari;
  private JButton btnReset;

  public MainGUI() {
    service = new PerpustakaanServiceImpl();

    setTitle("Sistem Perpustakaan");
    setSize(900, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    initComponents();
    loadData();
  }

  private void initComponents() {
    // Main panel dengan BorderLayout
    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Panel atas - Pencarian
    JPanel searchPanel = createSearchPanel();
    mainPanel.add(searchPanel, BorderLayout.NORTH);

    // Panel tengah - Table
    JPanel tablePanel = createTablePanel();
    mainPanel.add(tablePanel, BorderLayout.CENTER);

    // Panel kanan - Form Input
    JPanel formPanel = createFormPanel();
    mainPanel.add(formPanel, BorderLayout.EAST);

    add(mainPanel);
  }

  private JPanel createSearchPanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.setBorder(BorderFactory.createTitledBorder("Pencarian"));

    JLabel lblCari = new JLabel("Cari Judul:");
    txtCari = new JTextField(20);
    btnCari = new JButton("Cari");
    btnReset = new JButton("Reset");

    btnCari.addActionListener(e -> cariBuku());
    btnReset.addActionListener(e -> resetPencarian());

    panel.add(lblCari);
    panel.add(txtCari);
    panel.add(btnCari);
    panel.add(btnReset);

    return panel;
  }

  private JPanel createTablePanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createTitledBorder("Daftar Buku"));

    // Buat table model
    String[] columnNames = { "ID", "Judul", "Penulis", "Genre" };
    tableModel = new DefaultTableModel(columnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false; // Tidak bisa edit langsung di table
      }
    };

    tableBuku = new JTable(tableModel);
    tableBuku.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tableBuku.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) {
        loadSelectedRowToForm();
      }
    });

    JScrollPane scrollPane = new JScrollPane(tableBuku);
    panel.add(scrollPane, BorderLayout.CENTER);

    return panel;
  }

  private JPanel createFormPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createTitledBorder("Form Data Buku"));
    panel.setPreferredSize(new Dimension(300, 0));

    // Form fields
    JPanel fieldsPanel = new JPanel(new GridLayout(4, 2, 5, 10));
    fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel lblId = new JLabel("ID:");
    txtId = new JTextField(15);

    JLabel lblJudul = new JLabel("Judul:");
    txtJudul = new JTextField(15);

    JLabel lblPenulis = new JLabel("Penulis:");
    txtPenulis = new JTextField(15);

    JLabel lblGenre = new JLabel("Genre:");
    txtGenre = new JTextField(15);

    fieldsPanel.add(lblId);
    fieldsPanel.add(txtId);
    fieldsPanel.add(lblJudul);
    fieldsPanel.add(txtJudul);
    fieldsPanel.add(lblPenulis);
    fieldsPanel.add(txtPenulis);
    fieldsPanel.add(lblGenre);
    fieldsPanel.add(txtGenre);

    panel.add(fieldsPanel);

    // Buttons panel
    JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 5, 10));
    buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    btnTambah = new JButton("Tambah Buku");
    btnUpdate = new JButton("Update Buku");
    btnHapus = new JButton("Hapus Buku");
    btnRefresh = new JButton("Refresh Data");

    btnTambah.addActionListener(e -> tambahBuku());
    btnUpdate.addActionListener(e -> updateBuku());
    btnHapus.addActionListener(e -> hapusBuku());
    btnRefresh.addActionListener(e -> loadData());

    buttonsPanel.add(btnTambah);
    buttonsPanel.add(btnUpdate);
    buttonsPanel.add(btnHapus);
    buttonsPanel.add(btnRefresh);

    panel.add(buttonsPanel);

    // Info panel
    JPanel infoPanel = new JPanel();
    infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    JLabel lblInfo = new JLabel("<html><i>Klik baris di tabel<br>untuk edit/hapus</i></html>");
    infoPanel.add(lblInfo);
    panel.add(infoPanel);

    return panel;
  }

  private void loadData() {
    tableModel.setRowCount(0); // Clear table
    List<Object[]> data = service.getAllBukuData();
    for (Object[] row : data) {
      tableModel.addRow(row);
    }
    clearForm();
  }

  private void loadSelectedRowToForm() {
    int selectedRow = tableBuku.getSelectedRow();
    if (selectedRow >= 0) {
      txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
      txtJudul.setText(tableModel.getValueAt(selectedRow, 1).toString());
      txtPenulis.setText(tableModel.getValueAt(selectedRow, 2).toString());

      Object genre = tableModel.getValueAt(selectedRow, 3);
      txtGenre.setText(genre != null ? genre.toString() : "");

      txtId.setEditable(false); // ID tidak bisa diubah saat update
    }
  }

  private void tambahBuku() {
    try {
      int id = Integer.parseInt(txtId.getText().trim());
      String judul = txtJudul.getText().trim();
      String penulis = txtPenulis.getText().trim();
      String genre = txtGenre.getText().trim();

      if (judul.isEmpty() || penulis.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Judul dan Penulis harus diisi!",
            "Warning",
            JOptionPane.WARNING_MESSAGE);
        return;
      }

      boolean success = service.tambahBukuUI(id, judul, penulis, genre);

      if (success) {
        JOptionPane.showMessageDialog(this,
            "Buku berhasil ditambahkan!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
        loadData();
      } else {
        JOptionPane.showMessageDialog(this,
            "Gagal menambahkan buku! ID mungkin sudah digunakan.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
      }

    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this,
          "ID harus berupa angka!",
          "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void updateBuku() {
    try {
      int id = Integer.parseInt(txtId.getText().trim());
      String judul = txtJudul.getText().trim();
      String penulis = txtPenulis.getText().trim();
      String genre = txtGenre.getText().trim();

      if (judul.isEmpty() || penulis.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Judul dan Penulis harus diisi!",
            "Warning",
            JOptionPane.WARNING_MESSAGE);
        return;
      }

      int confirm = JOptionPane.showConfirmDialog(this,
          "Apakah Anda yakin ingin update buku dengan ID " + id + "?",
          "Konfirmasi Update",
          JOptionPane.YES_NO_OPTION);

      if (confirm == JOptionPane.YES_OPTION) {
        boolean success = service.updateBukuUI(id, judul, penulis, genre);

        if (success) {
          JOptionPane.showMessageDialog(this,
              "Buku berhasil diupdate!",
              "Success",
              JOptionPane.INFORMATION_MESSAGE);
          loadData();
        } else {
          JOptionPane.showMessageDialog(this,
              "Gagal update buku! Buku dengan ID tersebut tidak ditemukan.",
              "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }

    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this,
          "ID harus berupa angka!",
          "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void hapusBuku() {
    int selectedRow = tableBuku.getSelectedRow();

    if (selectedRow < 0) {
      JOptionPane.showMessageDialog(this,
          "Pilih buku yang akan dihapus dari tabel!",
          "Warning",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    int id = (int) tableModel.getValueAt(selectedRow, 0);
    String judul = tableModel.getValueAt(selectedRow, 1).toString();

    int confirm = JOptionPane.showConfirmDialog(this,
        "Apakah Anda yakin ingin menghapus buku:\n" + judul + " (ID: " + id + ")?",
        "Konfirmasi Hapus",
        JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
      boolean success = service.hapusBukuUI(id);

      if (success) {
        JOptionPane.showMessageDialog(this,
            "Buku berhasil dihapus!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
        loadData();
      } else {
        JOptionPane.showMessageDialog(this,
            "Gagal menghapus buku!",
            "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void cariBuku() {
    String keyword = txtCari.getText().trim();

    if (keyword.isEmpty()) {
      JOptionPane.showMessageDialog(this,
          "Masukkan kata kunci pencarian!",
          "Warning",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    tableModel.setRowCount(0);
    List<Object[]> data = service.cariBukuUI(keyword);

    if (data.isEmpty()) {
      JOptionPane.showMessageDialog(this,
          "Tidak ada buku yang ditemukan dengan judul: " + keyword,
          "Info",
          JOptionPane.INFORMATION_MESSAGE);
      loadData(); // Reload semua data
    } else {
      for (Object[] row : data) {
        tableModel.addRow(row);
      }
    }
  }

  private void resetPencarian() {
    txtCari.setText("");
    loadData();
  }

  private void clearForm() {
    txtId.setText("");
    txtJudul.setText("");
    txtPenulis.setText("");
    txtGenre.setText("");
    txtId.setEditable(true);
    tableBuku.clearSelection();
  }

  public static void main(String[] args) {
    // Set Look and Feel ke sistem default
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Jalankan GUI di Event Dispatch Thread
    SwingUtilities.invokeLater(() -> {
      MainGUI gui = new MainGUI();
      gui.setVisible(true);
    });
  }
}
