package com.example.hemon;



public class AsesmenDiabetes extends Asesmen {

    public AsesmenDiabetes(int umur, float beratBadan, float tinggiBadan,
                           int tekananDarahSis, int tekananDarahDia, String emailPengguna) {
        super(umur, beratBadan, tinggiBadan, tekananDarahSis, tekananDarahDia, emailPengguna);
    }

    @Override
    public String prediksi() {

        float bmi = (float) this.getBeratBadan() * 10000.0f / (float) this.getTinggiBadan() / (float) this.getTinggiBadan();

        if (this.getUmur() <= 28) {
            if (bmi <= 26.7) {
                return "negatif";
            } else {
                return "positif";
            }
        } else {
            if (bmi <= 41.75) {
                if (this.getTekananDarahDia() <= 63) {
                    return "negatif";
                } else {
                    return "positif";
                }
            } else if (this.getTekananDarahDia() <= 12) {
                return "positif";
            } else {
                return "negatif";
            }
        }
    }
}
