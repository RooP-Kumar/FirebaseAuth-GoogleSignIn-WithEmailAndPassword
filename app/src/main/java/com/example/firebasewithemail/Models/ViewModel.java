package com.example.firebasewithemail.Models;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseUser;

public class ViewModel extends AndroidViewModel {
    public MutableLiveData<FirebaseUser> user;
    FireRepo repository = new FireRepo();
    public ViewModel(Application application) {
        super(application);
    }

    public void signInWithEmail(String email, String pass) {
        user = repository.signUpWithEmail(email, pass);
    }

    public void signUpWithEmail(Context context, String email, String pass) {
        repository.signUpWithEmail(context, email, pass);
    }

    public void signWithGoogle(Context context, String idToken) {
        repository.signInWithGoogle(context, idToken);
    }

}
