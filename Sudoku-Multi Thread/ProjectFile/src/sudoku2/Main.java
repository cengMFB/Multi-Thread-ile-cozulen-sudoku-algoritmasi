package sudoku2;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Main {

    static int calisacakThreadSayisi;
    TextGrafik textgrafik = new TextGrafik();
    Graphic graph = new Graphic();

    public static BufferedWriter bw = null;
    public static BufferedWriter bw2 = null;
    public static BufferedWriter bw3 = null;
    public static BufferedWriter bw4 = null;
    public static BufferedWriter bw5 = null;

    public static FileWriter fw = null;
    public static FileWriter fw2 = null;
    public static FileWriter fw3 = null;
    public static FileWriter fw4 = null;
    public static FileWriter fw5 = null;

    static {
        try {
            fw = new FileWriter("ÇözümAdımları.txt");
            bw = new BufferedWriter(fw);
        } catch (Exception ex) {
        }
    }

    static {
        try {
            fw2 = new FileWriter("ÇözümAdımları2.txt");
            bw2 = new BufferedWriter(fw2);
        } catch (Exception ex) {
        }
    }

    static {
        try {
            fw3 = new FileWriter("ÇözümAdımları3.txt");
            bw3 = new BufferedWriter(fw3);
        } catch (Exception ex) {
        }
    }

    static {
        try {
            fw4 = new FileWriter("ÇözümAdımları4.txt");
            bw4 = new BufferedWriter(fw4);
        } catch (Exception ex) {
        }
    }

    static {
        try {
            fw5 = new FileWriter("ÇözümAdımları5.txt");
            bw5 = new BufferedWriter(fw5);
        } catch (Exception ex) {
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("kaç thread çalışacak");
        calisacakThreadSayisi = sc.nextInt();
        new Main();
        new TextGrafik().setVisible(true);
        new Graphic().setVisible(true);
    }

    public Main() {

        int[][] sudokuTahtasi = new int[9][9];
        int[][] sudokuTahtasi2 = new int[9][9];
        int[][] sudokuTahtasi3 = new int[9][9];
        int[][] sudokuTahtasi4 = new int[9][9];
        int[][] sudokuTahtasi5 = new int[9][9];
        int bosluksay = 0;
        int bosluksay2 = 0;
        int bosluksay3 = 0;
        int bosluksay4 = 0;
        int bosluksay5 = 0;

        try (Stream<String> stream = Files.lines(Paths.get("sudoku1.txt"))) {
            int j = 0;
            int i = 0;
            for (Object satir : stream.toArray()) {
                for (i = 0; i < satir.toString().length(); i++) {

                    if (satir.toString().charAt(i) == '*') {
                        if (i >= 9 && j <= 5) {
                            sudokuTahtasi2[j][i - 9] = 0;
                            bosluksay2++;
                        }
                        if (i >= 12 && j > 5 && j < 9) {
                            sudokuTahtasi2[j][i - 12] = 0;
                            bosluksay2++;
                        }
                        if (i < 9 && j < 9) {
                            sudokuTahtasi[j][i] = 0;
                            bosluksay++;

                        }
                        if (i > 5 && i < 15 && j >= 6 && j < 9) {
                            sudokuTahtasi3[j - 6][i - 6] = 0;
                            bosluksay3++;
                        }
                        if (i < 9 && j >= 9 && j < 12) {
                            sudokuTahtasi3[j - 6][i] = 0;
                            bosluksay3++;
                        }
                        if (i > 5 && i < 15 && j > 11 && j < 15) {
                            sudokuTahtasi3[j - 6][i - 6] = 0;
                            bosluksay3++;
                        }
                        if (i < 9 && j > 11) {
                            sudokuTahtasi4[j - 12][i] = 0;
                            bosluksay4++;
                        }
                        if (i >= 12 && j >= 12 && j < 15) {
                            sudokuTahtasi5[j - 12][i - 12] = 0;
                            bosluksay5++;
                        }
                        if (i >= 9 && j >= 15) {
                            sudokuTahtasi5[j - 12][i - 9] = 0;
                            bosluksay5++;
                        }
                    } else {

                        if (i >= 9 && j <= 5) {
                            sudokuTahtasi2[j][i - 9] = Integer.parseInt("" + satir.toString().charAt(i));
                        }

                        if (i >= 12 && j > 5 && j < 9) {
                            sudokuTahtasi2[j][i - 12] = Integer.parseInt("" + satir.toString().charAt(i));
                        }
                        if (i < 9 && j < 9) {

                            sudokuTahtasi[j][i] = Integer.parseInt("" + satir.toString().charAt(i));
                        }
                        if (i > 5 && i < 15 && j >= 6 && j < 9) {
                            sudokuTahtasi3[j - 6][i - 6] = Integer.parseInt("" + satir.toString().charAt(i));
                        }
                        if (i < 9 && j >= 9 && j < 12) {

                            sudokuTahtasi3[j - 6][i] = Integer.parseInt("" + satir.toString().charAt(i));
                        }
                        if (i > 5 && i < 15 && j > 11 && j < 15) {

                            sudokuTahtasi3[j - 6][i - 6] = Integer.parseInt("" + satir.toString().charAt(i));
                        }
                        if (i < 9 && j > 11) {

                            sudokuTahtasi4[j - 12][i] = Integer.parseInt("" + satir.toString().charAt(i));
                        }
                        if (i >= 12 && j >= 12 && j < 15) {
                            sudokuTahtasi5[j - 12][i - 12] = Integer.parseInt("" + satir.toString().charAt(i));
                        }
                        if (i >= 9 && j >= 15) {
                            sudokuTahtasi5[j - 12][i - 9] = Integer.parseInt("" + satir.toString().charAt(i));
                        }
                    }
                }
                j += 1;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        ThreadSinifi thread1 = new ThreadSinifi(copyTahta(sudokuTahtasi), bosluksay);
        ThreadSinifi thread2 = new ThreadSinifi(copyTahta(sudokuTahtasi2), bosluksay2);
        ThreadSinifi thread3 = new ThreadSinifi(copyTahta(sudokuTahtasi3), bosluksay3);
        ThreadSinifi thread4 = new ThreadSinifi(copyTahta(sudokuTahtasi4), bosluksay4);
        ThreadSinifi thread5 = new ThreadSinifi(copyTahta(sudokuTahtasi5), bosluksay5);
        thread1.setName("Thread 1");
        thread2.setName("Thread 2");
        thread3.setName("Thread 3");
        thread4.setName("Thread 4");
        thread5.setName("Thread 5");

        ThreadSinifi thread11 = new ThreadSinifi(copyTahta(sudokuTahtasi), bosluksay);
        ThreadSinifi thread22 = new ThreadSinifi(copyTahta(sudokuTahtasi2), bosluksay2);
        ThreadSinifi thread33 = new ThreadSinifi(copyTahta(sudokuTahtasi3), bosluksay3);
        ThreadSinifi thread44 = new ThreadSinifi(copyTahta(sudokuTahtasi4), bosluksay4);
        ThreadSinifi thread55 = new ThreadSinifi(copyTahta(sudokuTahtasi5), bosluksay5);

        thread11.setName("Thread 1-1");
        thread22.setName("Thread 2-2");
        thread33.setName("Thread 3-3");
        thread44.setName("Thread 4-4");
        thread55.setName("Thread 5-5");

        if (calisacakThreadSayisi == 5) {

            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread5.start();
            try {
                thread1.join();
                thread2.join();
                thread3.join();
                thread4.join();
                thread5.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (calisacakThreadSayisi == 10) {

            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread5.start();

            thread11.start();
            thread22.start();
            thread33.start();
            thread44.start();
            thread55.start();
            try {
                thread1.join();
                thread2.join();
                thread3.join();
                thread4.join();
                thread5.join();

                thread11.join();
                thread22.join();
                thread33.join();
                thread44.join();
                thread55.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        /**
         * threadler bittikten sonra hamle kontrolü yapılır
         */
        if (calisacakThreadSayisi == 5) {
            boolean thread1Bittimi = false;
            boolean thread2Bittimi = false;
            boolean thread3Bittimi = false;
            boolean thread4Bittimi = false;
            boolean thread5Bittimi = false;
            while (!thread1Bittimi) {

                thread1Bittimi = true;
                thread1.hamleleriYazdir();

            }
            while (!thread2Bittimi) {

                thread2Bittimi = true;
                thread2.hamleleriYazdir();

            }
            while (!thread3Bittimi) {

                thread3Bittimi = true;
                thread3.hamleleriYazdir();

            }
            while (!thread4Bittimi) {

                thread4Bittimi = true;
                thread4.hamleleriYazdir();

            }
            while (!thread5Bittimi) {

                thread5Bittimi = true;
                thread5.hamleleriYazdir();

            }

        }
        if (calisacakThreadSayisi == 10) {
            boolean thread1Bittimi = false;
            boolean thread2Bittimi = false;
            boolean thread3Bittimi = false;
            boolean thread4Bittimi = false;
            boolean thread5Bittimi = false;

            boolean thread11Bittimi = false;
            boolean thread22Bittimi = false;
            boolean thread33Bittimi = false;
            boolean thread44Bittimi = false;
            boolean thread55Bittimi = false;
            while (!thread1Bittimi) {

                thread1Bittimi = true;
                thread1.hamleleriYazdir();

            }
            while (!thread2Bittimi) {

                thread2Bittimi = true;
                thread2.hamleleriYazdir();

            }
            while (!thread3Bittimi) {

                thread3Bittimi = true;
                thread3.hamleleriYazdir();

            }
            while (!thread4Bittimi) {

                thread4Bittimi = true;
                thread4.hamleleriYazdir();

            }
            while (!thread5Bittimi) {

                thread5Bittimi = true;
                thread5.hamleleriYazdir();

            }
            while (!thread11Bittimi) {

                thread11Bittimi = true;
                thread11.hamleleriYazdir();

            }
            while (!thread22Bittimi) {

                thread22Bittimi = true;
                thread22.hamleleriYazdir();

            }
            while (!thread33Bittimi) {

                thread33Bittimi = true;
                thread33.hamleleriYazdir();

            }
            while (!thread44Bittimi) {

                thread44Bittimi = true;
                thread44.hamleleriYazdir();

            }
            while (!thread55Bittimi) {

                thread55Bittimi = true;
                thread55.hamleleriYazdir();

            }
        }

        try {
            if (Main.bw != null)//dosyayı kapatır yazdıktan sonraa
            {
                Main.bw.close();
            }

            if (Main.fw != null)//dosyayı kapatır yazdıktan sonr
            {
                Main.fw.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            if (Main.bw2 != null)//dosyayı kapatır yazdıktan sonraa
            {
                Main.bw2.close();
            }

            if (Main.fw2 != null)//dosyayı kapatır yazdıktan sonr
            {
                Main.fw2.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            if (Main.bw3 != null)//dosyayı kapatır yazdıktan sonraa
            {
                Main.bw3.close();
            }

            if (Main.fw3 != null)//dosyayı kapatır yazdıktan sonr
            {
                Main.fw3.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            if (Main.bw4 != null)//dosyayı kapatır yazdıktan sonraa
            {
                Main.bw4.close();
            }

            if (Main.fw4 != null)//dosyayı kapatır yazdıktan sonr
            {
                Main.fw4.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            if (Main.bw5 != null)//dosyayı kapatır yazdıktan sonraa
            {
                Main.bw5.close();
            }

            if (Main.fw5 != null)//dosyayı kapatır yazdıktan sonr
            {
                Main.fw5.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private int[][] copyTahta(int[][] tahta) {
        int[][] kopyaTahta = new int[9][9];

        int i = 0, j = 0;
        for (int[] ints : tahta) {
            for (int deger : ints) {
                kopyaTahta[i][j] = deger;
                j++;
            }
            i++;
            j = 0;
        }
        return kopyaTahta;
    }

//    void sudokuyuYazdir(int[][] solution) {
//        for (int i = 0; i < 9; ++i) {
//            if (i % 3 == 0) {
//                System.out.println(" -----------------------");
//            }
//            for (int j = 0; j < 9; ++j) {
//                if (j % 3 == 0) {
//                    System.out.print("| ");
//                }
//                System.out.print(solution[i][j] == 0
//                        ? "*"
//                        : Integer.toString(solution[i][j]));
//
//                System.out.print(' ');
//            }
//            System.out.println("|");
//        }
//        System.out.println(" -----------------------");
//    }
}
