package com.example.hemon;

public class AsesmenDiabetes extends JawabanAsesmen{
    public int banyakKehamilan;

    public AsesmenDiabetes(int umur, String jenisKelamin, double beratBadan, double tinggiBadan, int tekananDarahSis, int tekananDarahDia, String emailPengguna, int banyakKehamilan) {
        super(umur, jenisKelamin, beratBadan, tinggiBadan, tekananDarahSis, tekananDarahDia, emailPengguna);
        this.banyakKehamilan = banyakKehamilan;
    }

    @Override
    public String prediksi() {
        return null;
    }
}
