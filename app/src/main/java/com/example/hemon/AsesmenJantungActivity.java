package com.example.hemon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hemon.databinding.ActivityAsesmenJantungBinding;
import com.example.hemon.databinding.ActivityAsesmenStrokeBinding;

public class AsesmenJantungActivity extends AppCompatActivity {

    ActivityAsesmenJantungBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAsesmenJantungBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}