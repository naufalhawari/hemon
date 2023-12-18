package com.example.hemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hemon.databinding.ActivityAsesmenJantungBinding;
import com.example.hemon.databinding.ActivityAsesmenStrokeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AsesmenJantungActivity extends AppCompatActivity {

    ActivityAsesmenJantungBinding binding;
    PreferenceManager preferenceManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceManager = new PreferenceManager(this);

        binding = ActivityAsesmenJantungBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.asesmenPostButton.setOnClickListener(v->{

            if (!binding.inputUsia.getText().toString().trim().matches("[0-9]+")
                    || !binding.inputBeratBadan.getText().toString().trim().matches("[0-9]+")
                    || !binding.inputTinggiBadan.getText().toString().trim().matches("[0-9]+")
                    || !binding.inputTekananDarah.getText().toString().trim().matches("[0-9]+/[0-9]+")) {
                Toast.makeText(getApplicationContext(), "Pastikan jawaban Anda sesuai contoh", Toast.LENGTH_SHORT).show();
            } else {
                AsesmenJantung asesmenJantung = new AsesmenJantung(
                        Integer.valueOf(binding.inputUsia.getText().toString().trim()),
                        Float.valueOf(binding.inputBeratBadan.getText().toString().trim()),
                        Float.valueOf(binding.inputTinggiBadan.getText().toString().trim()),
                        Integer.valueOf(binding.inputTekananDarah.getText().toString().trim().split("/")[0]),
                        Integer.valueOf(binding.inputTekananDarah.getText().toString().trim().split("/")[1]),
                        preferenceManager.getString("email"));


                HashMap<String, Object> data = new HashMap<>();
                data.put("emailPengguna", preferenceManager.getString("email"));
                data.put("hasilDiagnosis", asesmenJantung.prediksi());
                data.put("jenisAsesmen", "jantung");
                data.put("tanggalAsesmen", FieldValue.serverTimestamp());

                db.collection("asesmen")
                        .add(data)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                binding.teksDiagnosis.setText("Hasil diagnosis penyakit jantung Anda adalah " + asesmenJantung.prediksi());
                                binding.dialogDiagnosis.setVisibility(View.VISIBLE);
                            }
                        });
            }



        });



        binding.backButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            this.finish();
        });

        binding.closeDialog.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            this.finish();
        });

        binding.clearJawabanButton.setOnClickListener(v->{
            binding.inputBeratBadan.setText("");
            binding.inputUsia.setText("");
            binding.inputTinggiBadan.setText("");
            binding.inputTekananDarah.setText("");
        });
    }
}