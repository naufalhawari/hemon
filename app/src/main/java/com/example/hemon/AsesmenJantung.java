package com.example.hemon;



public class AsesmenJantung extends JawabanAsesmen {

    public AsesmenJantung(int umur, float beratBadan, float tinggiBadan,
                          int tekananDarahSis, int tekananDarahDia, String emailPengguna) {
        super(umur, beratBadan, tinggiBadan, tekananDarahSis, tekananDarahDia, emailPengguna);
    }

    @Override
    public String prediksi() {



        if (tekananDarahSis <= 128) {

            if(tekananDarahDia <= 5) {
                return "negatif";
            } else {
                return "positif";
            }

        } else if (umur <= 58) {
            return "negatif";
        } else {
            return "positif";
        }

    }
}
