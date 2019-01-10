package com.ahmtcn777.firebaseproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class FotoYukle extends Activity {
    Button btnyukle;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference referans = storage.getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotoyukle);
        btnyukle = findViewById(R.id.btn_yukle);
        btnyukle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,1);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String email = user.getEmail();
            Uri uri = data.getData();
            StorageReference ref = referans.child(email).child(uri.getLastPathSegment());
            ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(FotoYukle.this, "Yükleme başarılı", Toast.LENGTH_SHORT).show();
                }
            });
            Calendar c = Calendar.getInstance();
            dbRef.child("images").child(c.getTime().toString()).setValue(email + " tarafından yükleme yapıldı");
        }
    }
}
