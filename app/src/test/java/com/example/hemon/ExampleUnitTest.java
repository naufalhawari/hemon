package com.example.hemon;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void asesmen_diabetes_answer_isCorrect() {
        AsesmenDiabetes asesmenDiabetes = new AsesmenDiabetes(20, 65, 175, 120, 80, "budi@mail.com");
        assertEquals("negatif", asesmenDiabetes.prediksi());
    }

    @Test
    public void asesmen_stroke_answer_isCorrect() {
        AsesmenStroke asesmen = new AsesmenStroke(50, 65, 175, 120, 80, "budi@mail.com");
        assertEquals("positif", asesmen.prediksi());
    }

    @Test
    public void asesmen_jantung_answer_isCorrect() {
        AsesmenJantung asesmen = new AsesmenJantung(59, 65, 175, 130, 80, "budi@mail.com");
        assertEquals("positif", asesmen.prediksi());
    }
}