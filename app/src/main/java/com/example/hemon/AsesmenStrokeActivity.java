package com.example.hemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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



        binding.asesmenPostButton.setOnClickListener(v->{
            AsesmenStroke asesmenStroke = new AsesmenStroke(
                    Integer.valueOf(binding.inputUsia.getText().toString().trim()),
                    Float.valueOf(binding.inputBeratBadan.getText().toString().trim()),
                    Float.valueOf(binding.inputTinggiBadan.getText().toString().trim()),
                    Integer.valueOf(binding.inputTekananDarah.getText().toString().trim().split("/")[0]),
                    Integer.valueOf(binding.inputTekananDarah.getText().toString().trim().split("/")[1]),
                    preferenceManager.getString("email"));

//            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//            builder.setTitle("Hasil Diagnosis")
//                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                            startActivity(intent);
//                        }
//                    });

            HashMap<String, Object> data = new HashMap<>();
            data.put("emailPengguna", preferenceManager.getString("email"));
            data.put("hasilDiagnosis", asesmenStroke.prediksi());
            data.put("jenisAsesmen", "jantung");
            data.put("tanggalAsesmen", FieldValue.serverTimestamp());

            db.collection("asesmen")
                    .add(data)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            binding.teksDiagnosis.setText("Hasil diagnosis penyakit jantung Anda adalah" + asesmenStroke.prediksi());
                            binding.dialogDiagnosis.setVisibility(View.VISIBLE);
                        }
                    });

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


    }

//    private submit(){
//
//    }
//
//    private clear(){
//
//    }
}