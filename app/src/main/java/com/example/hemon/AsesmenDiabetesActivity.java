package com.example.hemon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hemon.databinding.ActivityAsesmenDiabetesBinding;
import com.example.hemon.databinding.ActivityAsesmenStrokeBinding;

public class AsesmenDiabetesActivity extends AppCompatActivity {

    ActivityAsesmenDiabetesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAsesmenDiabetesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}