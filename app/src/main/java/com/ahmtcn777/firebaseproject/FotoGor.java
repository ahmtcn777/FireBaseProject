package com.ahmtcn777.firebaseproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FotoGor extends Activity {
    TextView tv;
    ImageView img;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference referans = storage.getReference();
    final File localFile = File.createTempFile("images","jpeg");
    String user_email;
    public FotoGor() throws IOException {

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotogor);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        String [] emailArray = email.split("@");
        user_email = emailArray[0];
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference(user_email);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    Toast.makeText(FotoGor.this, "Yüklü resim bulunamadı.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),userPanel.class);
                    startActivity(i);
                }
                else{
                    ImageToDatabase img = dataSnapshot.getValue(ImageToDatabase.class);
                    Log.d("onCreate1", img.imageName+img.userEmail);
                    tv = findViewById(R.id.tv_time);
                    tv.setText(img.time);
                    tv.setVisibility(View.VISIBLE);
                    FotoGoster(img.imageName);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void FotoGoster(String imageName){
        Log.d("FotoGoster", imageName);
        StorageReference indir = referans.child(user_email).child(imageName);
        img=findViewById(R.id.imageView);
        tv = findViewById(R.id.tv_time);
        indir.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(FotoGor.this, "Fotoğraf başarıyla yüklendi", Toast.LENGTH_SHORT).show();

                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                img.setImageBitmap(bitmap);

                Calendar cal = Calendar.getInstance();
                tv.setText(cal.getTime().toString());
            }
        });
    }

}
