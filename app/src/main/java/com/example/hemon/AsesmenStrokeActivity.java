package com.example.hemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hemon.databinding.ActivityAsesmenStrokeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import ai.onnxruntime.OrtSession.Result;

public class AsesmenStrokeActivity extends AppCompatActivity {

    ActivityAsesmenStrokeBinding binding;
    PreferenceManager preferenceManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAsesmenStrokeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(this);



        binding.asesmenPostButton.setOnClickListener(v->{

            if (!binding.inputUsia.getText().toString().trim().matches("[0-9]+")
                    || !binding.inputBeratBadan.getText().toString().trim().matches("[0-9]+")
                    || !binding.inputTinggiBadan.getText().toString().trim().matches("[0-9]+")
                    || !binding.inputTekananDarah.getText().toString().trim().matches("[0-9]+/[0-9]+")) {
                Toast.makeText(getApplicationContext(), "Pastikan jawaban Anda sesuai contoh", Toast.LENGTH_SHORT).show();
            } else {
                AsesmenStroke asesmenStroke = new AsesmenStroke(
                        Integer.valueOf(binding.inputUsia.getText().toString().trim()),
                        Float.valueOf(binding.inputBeratBadan.getText().toString().trim()),
                        Float.valueOf(binding.inputTinggiBadan.getText().toString().trim()),
                        Integer.valueOf(binding.inputTekananDarah.getText().toString().trim().split("/")[0]),
                        Integer.valueOf(binding.inputTekananDarah.getText().toString().trim().split("/")[1]),
                        preferenceManager.getString("email"));


                HashMap<String, Object> data = new HashMap<>();
                data.put("emailPengguna", preferenceManager.getString("email"));
                data.put("hasilDiagnosis", asesmenStroke.prediksi());
                data.put("jenisAsesmen", "stroke");
                data.put("tanggalAsesmen", FieldValue.serverTimestamp());

                db.collection("asesmen")
                        .add(data)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                binding.teksDiagnosis.setText("Hasil diagnosis penyakit stroke Anda adalah " + asesmenStroke.prediksi());
                                binding.dialogDiagnosis.setVisibility(View.VISIBLE);
                            }
                        });
            }

//            builder.show();
//            builder.create();

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

//    private submit(){
//
//    }
//
//    private clear(){
//
//    }
}