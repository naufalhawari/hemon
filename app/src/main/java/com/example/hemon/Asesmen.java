package com.example.hemon;

public abstract class Asesmen {

    private int umur;
    private String jenisKelamin;
    private double beratBadan;
    private double tinggiBadan;
    private int tekananDarahSis;
    private int tekananDarahDia;
    private String emailPengguna;

    public Asesmen(int umur, double beratBadan, double tinggiBadan,
                   int tekananDarahSis, int tekananDarahDia, String emailPengguna) {
        this.umur = umur;
        this.jenisKelamin = jenisKelamin;
        this.beratBadan = beratBadan;
        this.tinggiBadan = tinggiBadan;
        this.tekananDarahSis = tekananDarahSis;
        this.tekananDarahDia = tekananDarahDia;
        this.emailPengguna = emailPengguna;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public double getBeratBadan() {
        return beratBadan;
    }

    public void setBeratBadan(double beratBadan) {
        this.beratBadan = beratBadan;
    }

    public double getTinggiBadan() {
        return tinggiBadan;
    }

    public void setTinggiBadan(double tinggiBadan) {
        this.tinggiBadan = tinggiBadan;
    }

    public int getTekananDarahSis() {
        return tekananDarahSis;
    }

    public void setTekananDarahSis(int tekananDarahSis) {
        this.tekananDarahSis = tekananDarahSis;
    }

    public int getTekananDarahDia() {
        return tekananDarahDia;
    }

    public void setTekananDarahDia(int tekananDarahDia) {
        this.tekananDarahDia = tekananDarahDia;
    }

    public String getEmailPengguna() {
        return emailPengguna;
    }

    public void setEmailPengguna(String emailPengguna) {
        this.emailPengguna = emailPengguna;
    }

    public abstract String prediksi();
}
