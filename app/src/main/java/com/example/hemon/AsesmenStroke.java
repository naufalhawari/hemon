package com.example.hemon;


public class AsesmenStroke extends Asesmen {
    public boolean perokok;

    public AsesmenStroke(int umur, float beratBadan, float tinggiBadan, int tekananDarahSis,
                         int tekananDarahDia, String emailPengguna) {
        super(umur, beratBadan, tinggiBadan, tekananDarahSis, tekananDarahDia, emailPengguna);

    }

    @Override
    public String prediksi() {


        if (this.getUmur() <= 48) {
            return "negatif";
        } else {
            return "positif";
        }
    }
}
