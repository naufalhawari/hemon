package com.example.hemon;



public class AsesmenDiabetes extends JawabanAsesmen{

    public AsesmenDiabetes(int umur, float beratBadan, float tinggiBadan,
                           int tekananDarahSis, int tekananDarahDia, String emailPengguna) {
        super(umur, beratBadan, tinggiBadan, tekananDarahSis, tekananDarahDia, emailPengguna);
    }

    @Override
    public String prediksi() {

        float encodedGender;
        if (jenisKelamin.equals("L")) {
            encodedGender = 1.0f;
        } else {
            encodedGender = 0.0f;
        }

        float bmi = (float) beratBadan * 10000.0f / (float) tinggiBadan / (float) tinggiBadan;

        if (umur <= 28) {
            if (bmi <= 26.7) {
                return "negatif";
            } else {
                return "positif";
            }
        } else {
            if (bmi <= 41.75) {
                if (tekananDarahDia <= 63) {
                    return "negatif";
                } else {
                    return "positif";
                }
            } else if (tekananDarahDia <= 12) {
                return "positif";
            } else {
                return "negatif";
            }
        }
    }
}
