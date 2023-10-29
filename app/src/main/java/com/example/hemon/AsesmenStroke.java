package com.example.hemon;

public class AsesmenStroke extends JawabanAsesmen {
    public boolean sakitJantung;

    public AsesmenStroke(int umur, String jenisKelamin, double beratBadan, double tinggiBadan, int tekananDarahSis, int tekananDarahDia, String emailPengguna, boolean sakitJantung) {
        super(umur, jenisKelamin, beratBadan, tinggiBadan, tekananDarahSis, tekananDarahDia, emailPengguna);
        this.sakitJantung = sakitJantung;

    }

    @Override
    public String prediksi() {
        return null;
    }
}
