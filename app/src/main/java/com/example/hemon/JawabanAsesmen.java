package com.example.hemon;

public abstract class JawabanAsesmen {

    public int umur;
    public String jenisKelamin;
    public double beratBadan;
    public double tinggiBadan;
    public int tekananDarahSis;
    public int tekananDarahDia;
    public String emailPengguna;

    public JawabanAsesmen(int umur, double beratBadan, double tinggiBadan,
                          int tekananDarahSis, int tekananDarahDia, String emailPengguna) {
        this.umur = umur;
        this.jenisKelamin = jenisKelamin;
        this.beratBadan = beratBadan;
        this.tinggiBadan = tinggiBadan;
        this.tekananDarahSis = tekananDarahSis;
        this.tekananDarahDia = tekananDarahDia;
        this.emailPengguna = emailPengguna;
    }

    public abstract String prediksi();
}
