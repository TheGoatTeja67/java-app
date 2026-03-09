package model;

/**
 * Class BukuFiksi turunan dari Buku
 */
public class BukuFiksi extends Buku {

    private String genre;

    public BukuFiksi(int id,String judul,String penulis,String genre){
        super(id,judul,penulis);
        this.genre=genre;
    }

    @Override
    public void tampilkanInfo(){
        super.tampilkanInfo();
        System.out.println("Genre : " + genre);
    }
}
