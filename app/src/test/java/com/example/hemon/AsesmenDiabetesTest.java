package com.example.hemon;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Hemon's Assesment unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class AsesmenDiabetesTest {

    @Test
    public void asesmen_diabetes_answer_isCorrect() {
        // Jalur uji 1
        AsesmenDiabetes asesmenDiabetes = new AsesmenDiabetes(27, 130, 170, 120, 80, "budi@mail.com");
        assertEquals("positif", asesmenDiabetes.prediksi());

        // Jalur uji 2
        asesmenDiabetes = new AsesmenDiabetes(27, 60, 170, 120, 80, "budi@mail.com");
        assertEquals("negatif", asesmenDiabetes.prediksi());

        // Jalur uji 3
        asesmenDiabetes = new AsesmenDiabetes(30, 130, 170, 120, 80, "budi@mail.com");
        assertEquals("negatif", asesmenDiabetes.prediksi());

        // Jalur uji 4
        asesmenDiabetes = new AsesmenDiabetes(30, 130, 170, 120, 10, "budi@mail.com");
        assertEquals("positif", asesmenDiabetes.prediksi());

        // Jalur uji 5
        asesmenDiabetes = new AsesmenDiabetes(30, 60, 175, 120, 80, "budi@mail.com");
        assertEquals("positif", asesmenDiabetes.prediksi());

        // Jalur uji 6
        asesmenDiabetes = new AsesmenDiabetes(30, 60, 175, 120, 60, "budi@mail.com");
        assertEquals("negatif", asesmenDiabetes.prediksi());
    }
}
