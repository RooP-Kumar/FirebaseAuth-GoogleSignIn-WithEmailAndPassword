package com.example.firebasewithemail;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebasewithemail.Models.ViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button loginBtn;
    private Button registerBtn;
    private Button googleSignIn;
    private EditText emailFeild;
    private EditText passFeild;

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private ViewModel viewModel;
    private GoogleSignInClient googleSignClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.button);
        registerBtn = findViewById(R.id.button2);
        emailFeild = findViewById(R.id.editTextTextPersonName);
        passFeild = findViewById(R.id.editTextTextPassword);
        googleSignIn = findViewById(R.id.button5);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailFeild.getText().toString();
                String pass = passFeild.getText().toString();
                if (email.isEmpty() && pass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your email and password", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.signInWithEmail(email, pass);
                    viewModel.user.observe(MainActivity.this, new Observer<FirebaseUser>() {
                        @Override
                        public void onChanged(FirebaseUser firebaseUser) {
                            if (firebaseUser != null) {
                                startActivity(new Intent(MainActivity.this, DashboardPage.class));
                            } else {
                                Toast.makeText(MainActivity.this, "These Credential is not match with our database.", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, registerPage.class));
            }
        });

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                googleSignClient = GoogleSignIn.getClient(MainActivity.this, gso);
                Intent intent = googleSignClient.getSignInIntent();
                launcher.launch(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, DashboardPage.class));
        }
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            assert account != null;
            viewModel.signWithGoogle(MainActivity.this, account.getIdToken());

        } catch (ApiException e) {
            Log.w("TAG", "Google sign in failed", e);
        }

            });

}