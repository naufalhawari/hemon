package com.example.hemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.hemon.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());

        binding.greeting.setText("Halo, " + preferenceManager.getString("username"));

        binding.logoutButton.setOnClickListener(v->{
            preferenceManager.clear();
        });

        binding.asesmenDiabetesButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), AsesmenDiabetesActivity.class);
            startActivity(intent);
        });

        binding.asesmenJantungButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), AsesmenJantungActivity.class);
            startActivity(intent);
        });

        binding.asesmenStrokeButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), AsesmenStrokeActivity.class);
            startActivity(intent);
        });

        binding.logoutButton.setOnClickListener(v->{
            preferenceManager.clear();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

//        this.setClickListener();
    }

//    void setClickListener(){
//        binding.helpButton.setOnClickListener(v->{
//
//        });
//    }
}