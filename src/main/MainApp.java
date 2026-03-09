package main;

import java.util.Scanner;
import service.PerpustakaanServiceImpl;

/**
 * Program utama sistem perpustakaan
 */
public class MainApp {

    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        PerpustakaanServiceImpl service = new PerpustakaanServiceImpl();

        int pilihan;

        do{

            System.out.println("\n===== MENU PERPUSTAKAAN =====");
            System.out.println("1 Tambah Buku");
            System.out.println("2 Tampilkan Buku");
            System.out.println("3 Cari Buku");
            System.out.println("4 Update Buku");
            System.out.println("5 Hapus Buku");
            System.out.println("6 Keluar");

            System.out.print("Pilih Menu : ");
            pilihan=input.nextInt();
            input.nextLine();

            if(pilihan==1){

                service.tambahBuku();

            }else if(pilihan==2){

                service.tampilkanBuku();

            }else if(pilihan==3){

                service.cariBuku();

            }else if(pilihan==4){

                service.updateBuku();

            }else if(pilihan==5){

                service.hapusBuku();

            }

        }while(pilihan!=6);

        System.out.println("Program selesai");

    }

}
