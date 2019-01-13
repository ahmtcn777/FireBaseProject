package com.ahmtcn777.firebaseproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class userPanel extends Activity {
    TextView tv_email;

    public static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpanel);
        tv_email=findViewById(R.id.tv_email);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        tv_email.setText(email);
    }

    public void fotoyukle(View view){
        Intent i = new Intent(getApplicationContext(),FotoYukle.class);
        startActivity(i);
    }

    public void fotogor(View view){
        Intent i = new Intent(getApplicationContext(),FotoGor.class);
        startActivity(i);
    }


    public void cikisYap(View view){
        AuthUI.getInstance().signOut(getApplicationContext());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build()
                        ))
                        .build(),
                RC_SIGN_IN);



        /*
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        */
        Toast.makeText(getApplicationContext(), "Çıkış yapıldı", Toast.LENGTH_SHORT).show();
    }

}
