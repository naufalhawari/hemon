package com.example.hemon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hemon.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    Boolean isLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        isLogin = true;

        binding.
    }
}