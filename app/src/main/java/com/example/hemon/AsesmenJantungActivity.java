package com.example.hemon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.hemon.databinding.ActivityAsesmenJantungBinding;
import com.example.hemon.databinding.ActivityAsesmenStrokeBinding;

public class AsesmenJantungActivity extends AppCompatActivity {

    ActivityAsesmenJantungBinding binding;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceManager = new PreferenceManager(getApplicationContext());

        binding = ActivityAsesmenJantungBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.asesmenPostButton.setOnClickListener(v->{
            AsesmenJantung asesmenJantung = new AsesmenJantung(
                    Integer.valueOf(binding.inputUsia.getText().toString().trim()),
                    Float.valueOf(binding.inputBeratBadan.getText().toString().trim()),
                    Float.valueOf(binding.inputTinggiBadan.getText().toString().trim()),
                    Integer.valueOf(binding.inputTekananDarah.getText().toString().trim().split("/")[0]),
                    Integer.valueOf(binding.inputTekananDarah.getText().toString().trim().split("/")[1]),
                    preferenceManager.getString("email"));

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
            if (asesmenJantung.prediksi().equals("positif")) {
                alertDialog.setTitle("Hasil Diagnosa")
                        .setMessage("Berdasarkan diagnosa sementara anda dinyatakan positif sakit jantung." +
                                " Anda dapat segera memeriksa lebih lanjut ke rumah sakit.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            }
                        }).show();
            } else {
                alertDialog.setTitle("Hasil Diagnosa")
                        .setMessage("Berdasarkan diagnosa sementara anda dinyatakan negatif sakit jantung." +
                                " Tetap jaga kesehatan.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            }
                        }).show();
            }



        });



        binding.backButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            this.finish();
        });


    }
}