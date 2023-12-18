package com.example.hemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.hemon.databinding.ActivityHomeBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    PreferenceManager preferenceManager;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        preferenceManager = new PreferenceManager(getApplicationContext());

        binding.greeting.setText("Halo, " + preferenceManager.getString("username"));

        binding.logoutButton.setOnClickListener(v->{
            preferenceManager.clear();
            if (mGoogleSignInClient != null) {
                mGoogleSignInClient.signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            this.finish();


            }

        });

        binding.asesmenDiabetesButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), AsesmenDiabetesActivity.class);
            startActivity(intent);
            this.finish();
        });

        binding.asesmenJantungButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), AsesmenJantungActivity.class);
            startActivity(intent);
            this.finish();
        });

        binding.asesmenStrokeButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), AsesmenStrokeActivity.class);
            startActivity(intent);
            this.finish();
        });

        binding.historyButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), RiwayatActivity.class);
            startActivity(intent);
            this.finish();
        });

        binding.layoutKonsultasi.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), KonsultasiActivity.class);
            startActivity(intent);
            this.finish();
        });

    }

}