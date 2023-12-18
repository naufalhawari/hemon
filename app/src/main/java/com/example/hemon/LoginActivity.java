package com.example.hemon;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.hemon.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
    private GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 9001;
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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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
                        String passwordInput = binding.passwordConfirmationInput.getText().toString().trim();
                        if (Patterns.EMAIL_ADDRESS.matcher(binding.emailInput.getText().toString().trim()).matches() &&
                                passwordInput.matches(".*[0-9]+[a-zA-Z]+|[a-zA-Z]+[0-9]+.*") && passwordInput.length() > 8) {
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
                        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailInput.getText().toString().trim()).matches()){
                            Toast.makeText(getApplicationContext(), "Email tidak sesuai format",
                                    Toast.LENGTH_SHORT).show();
                        } else if (passwordInput.length() <= 8) {
                            Toast.makeText(getApplicationContext(), "Password harus lebih dari 8 karakter",
                                    Toast.LENGTH_SHORT).show();
                        } else if (!passwordInput.matches(".*[0-9]+[a-zA-Z]+|[a-zA-Z]+[0-9]+.*")) {
                            Toast.makeText(getApplicationContext(), "Password harus terdiri atas huruf dan angka",
                                    Toast.LENGTH_SHORT).show();
                        }

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
                    signIn();
                }
        );
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }


    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            Log.w(TAG, "signInResult: failed code=" + e.getStatusCode());
            Toast.makeText(this, "Terjadi eror pada sistem. mohon lakukan login ulang.", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateUI(GoogleSignInAccount user) {
        if (user != null){

            preferenceManager.putString("username", user.getDisplayName().split("\\s")[0]);
            preferenceManager.putString("email", user.getEmail());
            preferenceManager.putBoolean("isLogin", true);
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
    }
}