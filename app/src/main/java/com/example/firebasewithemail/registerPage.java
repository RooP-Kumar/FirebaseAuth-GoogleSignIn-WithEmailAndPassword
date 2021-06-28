package com.example.firebasewithemail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebasewithemail.Models.ViewModel;

public class registerPage extends AppCompatActivity {

    private Button registerBtn;
    private Button loginBtn;
    private EditText emailFeild;
    private EditText passFeild;

    private ViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        loginBtn = findViewById(R.id.button4);
        registerBtn = findViewById(R.id.button3);
        emailFeild = findViewById(R.id.editTextTextPersonName2);
        passFeild = findViewById(R.id.editTextTextPassword2);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailFeild.getText().toString();
                String pass = passFeild.getText().toString();
                if (email.isEmpty() && pass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your email and password", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.signUpWithEmail(registerPage.this, email, pass);
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}