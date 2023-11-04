package com.example.hemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        binding.backButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            this.finish();
        });
    }
}