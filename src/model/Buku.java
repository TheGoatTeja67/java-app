package model;

/**
 * Class Buku menyimpan data dasar buku
 */
public class Buku {

    private int id;
    private String judul;
    private String penulis;

    public Buku(int id, String judul, String penulis){
        this.id = id;
        this.judul = judul;
        this.penulis = penulis;
    }

    public int getId(){
        return id;
    }

    public String getJudul(){
        return judul;
    }

    public String getPenulis(){
        return penulis;
    }

    public void setJudul(String judul){
        this.judul = judul;
    }

    public void setPenulis(String penulis){
        this.penulis = penulis;
    }

    public void tampilkanInfo(){
        System.out.println("ID : " + id);
        System.out.println("Judul : " + judul);
        System.out.println("Penulis : " + penulis);
    }
}