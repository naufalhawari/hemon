package com.example.hemon;

public class AsesmenJantung extends JawabanAsesmen {
    public boolean perokok;
    public boolean olahraga;

    public AsesmenJantung(int umur, String jenisKelamin, double beratBadan, double tinggiBadan, int tekananDarahSis, int tekananDarahDia, String emailPengguna, boolean perokok, boolean olahraga) {
        super(umur, jenisKelamin, beratBadan, tinggiBadan, tekananDarahSis, tekananDarahDia, emailPengguna);
        this.perokok = perokok;
        this.olahraga = olahraga;
    }

    @Override
    public String prediksi() {
        return null;
    }
}
