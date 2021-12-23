package sudoku2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * bu sınıf threadi temsil eder, her çalıştırılan thread bu sınıftan çalışır
 */
public class ThreadSinifi extends Thread {

    public static boolean bittiMi = false; // eğer bittiyse diğer threadler çalışmaz
    public long baslangic, bitis; // süreyi tutmak için
    private List<CozumAdimlari> hamleler; // hamleleri buraya kaydediyoruz sonradan göstermek içiin
    private int[][] sudokuTahtasi;
    int boslukSayisi = 0;

    public ThreadSinifi(int[][] sudokuTahtasi, int boslukSayisi) {
        this.sudokuTahtasi = sudokuTahtasi;
        hamleler = new ArrayList<>();
        this.boslukSayisi = boslukSayisi;
    }

    /**
     * Threadi çalıştıran metod
     */
    @Override
    public void run() {//thread başladıktan sonra yapılan işlem

        baslangic = System.currentTimeMillis();
        int i, j;

        i = 0;//new Random().nextInt(5);//0 ile 5 arası kafadan değer atar
        j = 0;

        if (getName().contains("-")) {
            i = 4;//new Random().nextInt(5);//0 ile 5 arası kafadan değer atar
            j = 4;
        }

        if (coz(i, j, sudokuTahtasi)) {

            sureyiYaz();
            sudokuyuYazdir(sudokuTahtasi);
        }

    }

    boolean coz(int i, int j, int[][] cozulecekSudoku) {//çözümü başltıyor

        if (i == 9) {
            i = 0;
            if (++j == 9)//sütunu bir ileri taşır //Ama sütün en fazla 8 olabilir
            {
                j = 0;//Eğer 9 olursa sutunu 0 yapar
            }
        }

        if (bittimiBak(cozulecekSudoku)) {
            bitis = System.currentTimeMillis();
            return true;
        }

        if (cozulecekSudoku[i][j] != 0)//Bura önemli eğer bulduğumuz bir yere bakıyorsa bi sonraki alana bak boşa uğraşma diyoruz
        {
            return coz(i + 1, j, cozulecekSudoku);
        }

        for (int val = 1; val <= 9; ++val) {
            if (kontrolEt(i, j, val, cozulecekSudoku)) {
                cozulecekSudoku[i][j] = val;//Bulursa zaten recursion kendini çağırır oraya düşmez
                hamleler.add(new CozumAdimlari(i, j, val));
                if (coz(i + 1, j, cozulecekSudoku))//çöz metodunu tekrar çağırır ama dikkat ederseniz satırı 1 artırır öyle çağır//Recursion
                {
                    return true;
                }
            }
        }
        cozulecekSudoku[i][j] = 0;//Bura eğer hiçbirşey bulamazsa emniyet olarak çalışıyor 
        return false;
    }

    /**
     * cozum bulunduktan sonra hamleleri yazdıran metod
     */
    public void hamleleriYazdir() {

        dosyayaHamleleriYaz();
    }

    /*
     * 20ms bekleme yaptırır threadler kendine gelsin diye
     */
 /*kod eğer çok hızlı çözerse hepsi aynı anda çözer ondan dolayı 
    0-20 arası rastgele sayı atıyor ve o kadar milisaniye bekliyor*/
    private void bekle() {
        try {
            int beklemeSuresi = new Random().nextInt(20);
            Thread.sleep(beklemeSuresi);
        } catch (InterruptedException e) {

        }
    }

    /**
     * sudoku çözülmüşmü kontrol eder
     */
    boolean bittimiBak(int[][] cozulecekSudoku) {//thread in kendi çözümü bitmişmi ona bakar
        for (int[] alan : cozulecekSudoku) {//Çift boyutlu arrayi dolaşıypr ondan çift for
            for (int rakam : alan) {
                if (rakam == 0) {
                    return false;
                }
            }
        }
        bittiMi = true;
        return true;
    }

    /**
     * sudokuya değer atamadan önce burda kontrol edilir tüm kurallara uygunmu
     * diye yani satırlarda aynı numaradan olmıycak sutunlarda ve kendi içindeki
     * 3x3 kutularda aynı numara olmaması lazım
     */
    boolean kontrolEt(int i, int j, int val, int[][] cozulecekSudoku) {
        /**
         * satırları kontrol eder
         */
        for (int k = 0; k < 9; ++k) {
            if (val == cozulecekSudoku[k][j]) {
                return false;
            }
        }
        /**
         * sutunları kontrol eder
         */
        for (int k = 0; k < 9; ++k) {
            if (val == cozulecekSudoku[i][k]) {
                return false;
            }
        }

        int satirAlani = (i / 3) * 3;
        int sutunAlani = (j / 3) * 3;
        for (int k = 0; k < 3; ++k) {
            for (int m = 0; m < 3; ++m) {
                if (val == cozulecekSudoku[satirAlani + k][sutunAlani + m]) {
                    return false;
                }
            }
        }

        return true;
    }

    void sudokuyuYazdir(int[][] solution) {
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
    }

    public void sureyiYaz() {
        System.out.println("sudoku " + getName() + " tarafından " + (bitis - baslangic) + " milisaniyede çözülmüştür");

    }

    private void dosyayaHamleleriYaz() {

        if (getName().contains("1")) {
            try {

                Main.bw.write("*****************************************\n");
                Main.bw.write("*****************************************\n");
                Main.bw.write(getName() + " Hamleleri\n");
                int h = 0;
                for (CozumAdimlari hamle : hamleler) {
                    if (h % 10 == 0) {
                        Main.bw.newLine();
                    }
                    Main.bw.write(hamle + "\t");
                    h++;
                }
                Main.bw.newLine();
                Main.bw.write("Sudokunun son durumu: \n");
                Main.bw.write("*****************************************\n");
                Main.bw.write("*****************************************\n");
                for (int i = 0; i < 9; ++i) {
                    if (i % 3 == 0) {
                        Main.bw.write(" -----------------------\n");
                    }
                    for (int j = 0; j < 9; ++j) {
                        if (j % 3 == 0) {
                            Main.bw.write("| ");
                        }
                        Main.bw.write(sudokuTahtasi[i][j] == 0
                                ? "*"
                                : Integer.toString(sudokuTahtasi[i][j]));

                        Main.bw.write(' ');
                    }
                    Main.bw.write("|\n");
                }
                Main.bw.write(" -----------------------\n");
                Main.bw.write("sudoku " + getName() + " tarafından " + (bitis - baslangic) + " milisaniyede çözülmüştür");
                Main.bw.write(getName() + " tarafından " + boslukSayisi + " kutu çözülmüştür");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (getName().contains("2")) {
            try {

                Main.bw2.write("*****************************************\n");
                Main.bw2.write("*****************************************\n");
                Main.bw2.write(getName() + " Hamleleri\n");
                int h = 0;
                for (CozumAdimlari hamle : hamleler) {
                    if (h % 10 == 0) {
                        Main.bw2.newLine();
                    }
                    Main.bw2.write(hamle + "\t");
                    h++;
                }
                Main.bw2.newLine();
                Main.bw2.write("Sudokunun son durumu: \n");
                Main.bw2.write("*****************************************\n");
                Main.bw2.write("*****************************************\n");
                for (int i = 0; i < 9; ++i) {
                    if (i % 3 == 0) {
                        Main.bw2.write(" -----------------------\n");
                    }
                    for (int j = 0; j < 9; ++j) {
                        if (j % 3 == 0) {
                            Main.bw2.write("| ");
                        }
                        Main.bw2.write(sudokuTahtasi[i][j] == 0
                                ? "*"
                                : Integer.toString(sudokuTahtasi[i][j]));

                        Main.bw2.write(' ');
                    }
                    Main.bw2.write("|\n");
                }
                Main.bw2.write(" -----------------------\n");
                Main.bw2.write("sudoku " + getName() + " tarafından " + (bitis - baslangic) + " milisaniyede çözülmüştür");
                Main.bw2.write(getName() + " tarafından " + boslukSayisi + " kutu çözülmüştür");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (getName().contains("3")) {
            try {

                Main.bw3.write("*****************************************\n");
                Main.bw3.write("*****************************************\n");
                Main.bw3.write(getName() + " Hamleleri\n");
                int h = 0;
                for (CozumAdimlari hamle : hamleler) {
                    if (h % 10 == 0) {
                        Main.bw3.newLine();
                    }
                    Main.bw3.write(hamle + "\t");
                    h++;
                }
                Main.bw3.newLine();
                Main.bw3.write("Sudokunun son durumu: \n");
                Main.bw3.write("*****************************************\n");
                Main.bw3.write("*****************************************\n");
                for (int i = 0; i < 9; ++i) {
                    if (i % 3 == 0) {
                        Main.bw3.write(" -----------------------\n");
                    }
                    for (int j = 0; j < 9; ++j) {
                        if (j % 3 == 0) {
                            Main.bw3.write("| ");
                        }
                        Main.bw3.write(sudokuTahtasi[i][j] == 0
                                ? "*"
                                : Integer.toString(sudokuTahtasi[i][j]));

                        Main.bw3.write(' ');
                    }
                    Main.bw3.write("|\n");
                }
                Main.bw3.write(" -----------------------\n");
                Main.bw3.write("sudoku " + getName() + " tarafından " + (bitis - baslangic) + " milisaniyede çözülmüştür");
                Main.bw3.write(getName() + " tarafından " + boslukSayisi + " kutu çözülmüştür");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (getName().contains("4")) {
            try {

                Main.bw4.write("*****************************************\n");
                Main.bw4.write("*****************************************\n");
                Main.bw4.write(getName() + " Hamleleri\n");
                int h = 0;
                for (CozumAdimlari hamle : hamleler) {
                    if (h % 10 == 0) {
                        Main.bw4.newLine();
                    }
                    Main.bw4.write(hamle + "\t");
                    h++;
                }
                Main.bw4.newLine();
                Main.bw4.write("Sudokunun son durumu: \n");
                Main.bw4.write("*****************************************\n");
                Main.bw4.write("*****************************************\n");
                for (int i = 0; i < 9; ++i) {
                    if (i % 3 == 0) {
                        Main.bw4.write(" -----------------------\n");
                    }
                    for (int j = 0; j < 9; ++j) {
                        if (j % 3 == 0) {
                            Main.bw4.write("| ");
                        }
                        Main.bw4.write(sudokuTahtasi[i][j] == 0
                                ? "*"
                                : Integer.toString(sudokuTahtasi[i][j]));

                        Main.bw4.write(' ');
                    }
                    Main.bw4.write("|\n");
                }
                Main.bw4.write(" -----------------------\n");
                Main.bw4.write("sudoku " + getName() + " tarafından " + (bitis - baslangic) + " milisaniyede çözülmüştür");
                Main.bw4.write(getName() + " tarafından " + boslukSayisi + " kutu çözülmüştür");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (getName().contains("5")) {
            try {

                Main.bw5.write("*****************************************\n");
                Main.bw5.write("*****************************************\n");
                Main.bw5.write(getName() + " Hamleleri\n");
                int h = 0;
                for (CozumAdimlari hamle : hamleler) {
                    if (h % 10 == 0) {
                        Main.bw5.newLine();
                    }
                    Main.bw5.write(hamle + "\t");
                    h++;
                }
                Main.bw5.newLine();
                Main.bw5.write("Sudokunun son durumu: \n");
                Main.bw5.write("*****************************************\n");
                Main.bw5.write("*****************************************\n");
                for (int i = 0; i < 9; ++i) {
                    if (i % 3 == 0) {
                        Main.bw5.write(" -----------------------\n");
                    }
                    for (int j = 0; j < 9; ++j) {
                        if (j % 3 == 0) {
                            Main.bw5.write("| ");
                        }
                        Main.bw5.write(sudokuTahtasi[i][j] == 0
                                ? "*"
                                : Integer.toString(sudokuTahtasi[i][j]));

                        Main.bw5.write(' ');
                    }
                    Main.bw5.write("|\n");
                }
                Main.bw5.write(" -----------------------\n");
                Main.bw5.write("sudoku " + getName() + " tarafından " + (bitis - baslangic) + " milisaniyede çözülmüştür");
                Main.bw5.write(getName() + " tarafından " + boslukSayisi + " kutu çözülmüştür");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
