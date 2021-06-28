package com.example.firebasewithemail.Models;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.firebasewithemail.DashboardPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class FireRepo {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    MutableLiveData<FirebaseUser> signUpWithEmail(String email, String pass) {
        MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                user.setValue(auth.getCurrentUser());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        return user;
    }


    void signUpWithEmail(Context context, String email, String pass) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                addingUserToFirebase(task.getResult().getUser().getEmail());
                Toast.makeText(context, "You successfully register.", Toast.LENGTH_LONG)
                        .show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Something Went Wrong.", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }


    void signInWithGoogle(Context context, String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                addingUserToFirebase(task.getResult().getUser().getEmail());
                context.startActivity(new Intent(context, DashboardPage.class));
            }
        });
    }

    void addingUserToFirebase(String email) {
        Map<String, String> data = new HashMap<>();
        data.put("Email", email);
        db.collection("Users")
                .add(data);
    }

}
