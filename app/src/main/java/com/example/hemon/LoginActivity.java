package com.example.hemon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

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

        binding.loginButton.setOnClickListener(
                v -> {
                    binding.loginButton.setBackgroundColor(getResources().getColor(R.color.primary));
                    binding.loginButton.setTextColor(getResources().getColor(R.color.white));
                    binding.signupButton.setBackgroundColor(getResources().getColor(R.color.white));
                    binding.signupButton.setTextColor(getResources().getColor(R.color.primary));
                    binding.passwordConfirmationInput.setVisibility(View.GONE);
                    binding.postButton.setText("Login");
                }
        );

        binding.signupButton.setOnClickListener(
                v -> {
                    binding.signupButton.setBackgroundColor(getResources().getColor(R.color.primary));
                    binding.signupButton.setTextColor(getResources().getColor(R.color.white));
                    binding.loginButton.setBackgroundColor(getResources().getColor(R.color.white));
                    binding.loginButton.setTextColor(getResources().getColor(R.color.primary));
                    binding.passwordConfirmationInput.setVisibility(View.VISIBLE);
                    binding.postButton.setText("Sign Up");
                }
        );
    }
}