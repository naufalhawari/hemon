package com.example.hemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.hemon.databinding.ActivityAsesmenStrokeBinding;


import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import ai.onnxruntime.OrtSession.Result;

public class AsesmenStrokeActivity extends AppCompatActivity {

    ActivityAsesmenStrokeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAsesmenStrokeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.backButton.setOnClickListener(v->{
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