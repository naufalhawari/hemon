package com.example.hemon;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hemon.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;

    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        preferenceManager = new PreferenceManager(getApplicationContext());
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (preferenceManager.getBoolean("isLogin")){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }

        binding.signupButton.setOnClickListener(
                v -> {
                    binding.signupButton.setBackgroundColor(getResources().getColor(R.color.primary));
                    binding.signupButton.setTextColor(getResources().getColor(R.color.white));
                    binding.loginButton.setBackgroundColor(getResources().getColor(R.color.white));
                    binding.loginButton.setTextColor(getResources().getColor(R.color.primary));
                    binding.usernameInput.setVisibility(View.VISIBLE);
                    binding.passwordConfirmationInput.setVisibility(View.VISIBLE);
                    binding.postButton.setText("Sign Up");
                }
        );

        binding.loginButton.setOnClickListener(
                v -> {
                    binding.loginButton.setBackgroundColor(getResources().getColor(R.color.primary));
                    binding.loginButton.setTextColor(getResources().getColor(R.color.white));
                    binding.signupButton.setBackgroundColor(getResources().getColor(R.color.white));
                    binding.signupButton.setTextColor(getResources().getColor(R.color.primary));
                    binding.passwordConfirmationInput.setVisibility(View.GONE);
                    binding.usernameInput.setVisibility(View.GONE);
                    binding.postButton.setText("Login");

                });

        login();


    }

    private void login() {
        binding.postButton.setOnClickListener(
                v -> {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    if (binding.postButton.getText().toString().trim().equals("Login")) {
                        User user = new User(binding.emailInput.getText().toString().trim(),
                                binding.passwordInput.getText().toString().trim());

                        db.collection("user")
                                .whereEqualTo("email", user.getEmail())
                                .whereEqualTo("password", user.getPassword())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful() && task.getResult().getDocuments().size() > 0){
                                            DocumentSnapshot data = task.getResult().getDocuments().get(0);
                                            preferenceManager.putString("username", data.getString("username"));
                                            preferenceManager.putString("email", user.getEmail());
                                            preferenceManager.putBoolean("isLogin", true);
                                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Password salah atau Akun belum terdaftar",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    } else if (binding.passwordConfirmationInput.getText().toString().trim().equals(
                            binding.passwordInput.getText().toString().trim()
                    )) {

                        User user = new User(binding.emailInput.getText().toString().trim(),
                                binding.passwordInput.getText().toString().trim(),
                                binding.usernameInput.getText().toString().trim());

                        HashMap<String, Object> data = new HashMap<>();
                        data.put("username", user.getUsername());
                        data.put("email", user.getEmail());
                        data.put("password", user.getPassword());

                        db.collection("user")
                                .add(data)
                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        preferenceManager.putString("email", user.getEmail());
                                        preferenceManager.putBoolean("isLogin", true);
                                        preferenceManager.putString("username", user.getUsername());
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                    }
                                });
                    } else if (!binding.passwordConfirmationInput.getText().toString().trim().equals(
                            binding.passwordInput.getText().toString().trim()
                    )) {
                        Toast.makeText(getApplicationContext(), "Password konfirmasi salah",
                                Toast.LENGTH_SHORT).show();
                    }

                    binding.progressBar.setVisibility(View.INVISIBLE);

                }
        );

        binding.googleLogin.setOnClickListener(
                view -> {
                    oneTapClient = Identity.getSignInClient(this);
                    signInRequest = BeginSignInRequest.builder()
                            .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                    .setSupported(true)
                                    // Your server's client ID, not your Android client ID.
                                    .setServerClientId(getString(R.string.default_web_client_id))
                                    // Only show accounts previously used to sign in.
                                    .setFilterByAuthorizedAccounts(true)
                                    .build())
                            // Automatically sign in when exactly one credential is retrieved.
                            .setAutoSelectEnabled(true)
                            .build();

                }
        );
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    if (idToken !=  null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with Firebase.
                        Log.d(TAG, "Got ID token.");
                    }
                } catch (ApiException e) {
                    Log.d(TAG, e.getMessage());
                }
                break;
        }

        try{
            SignInCredential googleCredential = oneTapClient.getSignInCredentialFromIntent(data);
            String idToken = googleCredential.getGoogleIdToken();
            if (idToken !=  null) {
                // Got an ID token from Google. Use it to authenticate
                // with Firebase.
                AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                mAuth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithCredential:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    preferenceManager.putString("username", user.getDisplayName());
                                    preferenceManager.putString("email", user.getEmail());
                                    updateUI(user);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Login gagal, silakan coba lagi", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        });
            }
        }
        catch (ApiException e){
            Log.d(TAG, e.getMessage());
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
    }
}