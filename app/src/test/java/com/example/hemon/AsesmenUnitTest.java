package com.example.hemon;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Hemon's Assesment unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AsesmenUnitTest {

    @Test
    public void asesmen_diabetes_answer_isCorrect() {
        AsesmenDiabetes asesmenDiabetes = new AsesmenDiabetes(27, 60, 170, 120, 80, "budi@mail.com");
        assertEquals("negatif", asesmenDiabetes.prediksi());

        asesmenDiabetes = new AsesmenDiabetes(30, 130, 170, 120, 10, "budi@mail.com");
        assertEquals("positif", asesmenDiabetes.prediksi());
    }

    @Test
    public void asesmen_stroke_answer_isCorrect() {

        AsesmenStroke asesmen = new AsesmenStroke(40, 65, 175, 120, 80, "budi@mail.com");
        assertEquals("negatif", asesmen.prediksi());

        asesmen = new AsesmenStroke(50, 65, 175, 120, 80, "budi@mail.com");
        assertEquals("positif", asesmen.prediksi());
    }

    @Test
    public void asesmen_jantung_answer_isCorrect() {
        AsesmenJantung asesmen = new AsesmenJantung(50, 65, 175, 130, 80, "budi@mail.com");
        assertEquals("negatif", asesmen.prediksi());

        asesmen = new AsesmenJantung(59, 65, 175, 130, 80, "budi@mail.com");
        assertEquals("positif", asesmen.prediksi());
    }
}