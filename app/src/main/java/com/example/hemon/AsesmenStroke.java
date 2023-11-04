package com.example.hemon;


public class AsesmenStroke extends JawabanAsesmen {
    public boolean perokok;

    public AsesmenStroke(int umur, float beratBadan, float tinggiBadan, int tekananDarahSis,
                         int tekananDarahDia, String emailPengguna) {
        super(umur, beratBadan, tinggiBadan, tekananDarahSis, tekananDarahDia, emailPengguna);

    }

    @Override
    public String prediksi() {


        float bmi = (float) beratBadan * 10000.0f / (float) tinggiBadan / (float) tinggiBadan;

        if (umur <= 48) {
            return "negatif";
        } else {
            return "positif";
        }
    }
}
