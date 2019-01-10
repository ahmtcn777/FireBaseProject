package com.ahmtcn777.firebaseproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;

public class MainActivity extends Activity {

    FirebaseDatabase db;
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;


    public static final int RC_SIGN_IN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Toast.makeText(getApplicationContext(), "Giriş başarılı", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),userPanel.class);
                    startActivity(i);
                }
                else{
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build()
                                    ))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(requestCode==RESULT_OK){
                Toast.makeText(getApplicationContext(), "Giriş Yapıldı", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == RESULT_CANCELED){
                finish();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAuthStateListener != null){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}



/*

db den veri cekme
@Override
public void onClick(View v) {
final ValueEventListener listener=new ValueEventListener() {
@Override
public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
        String a="ahmtcn777";
        String deger=dataSnapshot.child("-LVphOIKDTdS41ySgt1l").child("username").getValue().toString();
        if(a.equals(deger)){
        tv.setText("bu kullanici maili: "+dataSnapshot.child("-LVphOIKDTdS41ySgt1l").child("email").getValue().toString());
        tv.setVisibility(View.VISIBLE);
        }
        }
        }

@Override
public void onCancelled(@NonNull DatabaseError databaseError) {

        }
        };
        readRef.addValueEventListener(listener);

//readRef.removeValue();
*/


/*
public void writeNewUser(String email,String password){
        if(!email.isEmpty() && !password.isEmpty()){
            ImageToDatabase user=new ImageToDatabase(email,password);
            writeRef.child("users").push().setValue(user);
        }
        else{
            Toast.makeText(MainActivity.this, "Lütfen bütün alanları doldurun", Toast.LENGTH_SHORT).show();
        }
    }
 */


/*
FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference writeRef = database.getReference();

    DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("users");
    //Veritabanında verilen parametreyi arar ve dataSnapshot a atar
 */