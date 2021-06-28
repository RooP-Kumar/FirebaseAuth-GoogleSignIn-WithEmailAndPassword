package com.example.firebasewithemail;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class DashboardPage extends AppCompatActivity {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private ImageView imageChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_page);

        TextView showEmail = findViewById(R.id.textView2);

        String email = Objects.requireNonNull(auth.getCurrentUser()).getEmail();

        imageChoose = findViewById(R.id.imageUse);

        showEmail.setText(email);

        Toast.makeText(getApplicationContext(), "You Successfully log in.", Toast
                .LENGTH_LONG)
                .show();

        Button signOut = findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                finish();
            }
        });


        imageChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                launcher.launch(intent);
            }
        });

    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result)->{

        if (result.getResultCode() == RESULT_OK) {
            assert result.getData() != null;
            Uri imageUri = result.getData().getData();
            Glide.with(this).load(imageUri).into(imageChoose);
        }

    });

}