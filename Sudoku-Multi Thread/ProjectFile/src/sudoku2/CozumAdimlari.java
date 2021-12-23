package sudoku2;

public class CozumAdimlari {
    private int satir;
    private int sutun;
    private int deger;

    public CozumAdimlari(int satir, int sutun, int deger) {
        this.satir = satir;
        this.sutun = sutun;
        this.deger = deger;
    }

    public int getSatir() {
        return satir;
    }

    public void setSatir(int satir) {
        this.satir = satir;
    }

    public int getSutun() {
        return sutun;
    }

    public void setSutun(int sutun) {
        this.sutun = sutun;
    }

    public int getDeger() {
        return deger;
    }

    public void setDeger(int deger) {
        this.deger = deger;
    }

    @Override
    public String toString() {
        return "["+satir+"]["+sutun+"] = "+deger+" ";
    }
}
