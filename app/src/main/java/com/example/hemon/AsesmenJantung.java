package com.example.hemon;



public class AsesmenJantung extends Asesmen {

    public AsesmenJantung(int umur, float beratBadan, float tinggiBadan,
                          int tekananDarahSis, int tekananDarahDia, String emailPengguna) {
        super(umur, beratBadan, tinggiBadan, tekananDarahSis, tekananDarahDia, emailPengguna);
    }



    @Override
    public String prediksi() {



        if (this.getTekananDarahSis() <= 128) {

            if(this.getTekananDarahDia() <= 5) {
                return "negatif";
            } else {
                return "positif";
            }

        } else if (this.getUmur() <= 58) {
            return "negatif";
        } else {
            return "positif";
        }

    }
}
