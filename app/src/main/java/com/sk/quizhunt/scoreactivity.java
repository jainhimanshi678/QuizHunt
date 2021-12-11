package com.sk.quizhunt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import static com.sk.quizhunt.MainActivity.name;
public class scoreactivity extends AppCompatActivity {
    private TextView t;
    private Button b;
    FirebaseFirestore firestore;
    int set;
    int s;
    FirebaseDatabase database ;
    DatabaseReference myRef ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreactivity);
        t=findViewById(R.id.score);
        b=findViewById(R.id.done);
        firestore=FirebaseFirestore.getInstance();
        // loadData();
        myRef=FirebaseDatabase.getInstance().getReference();
        final String score = getIntent().getStringExtra("SCORE");
        t.setText(score);
         s=getIntent().getIntExtra("S",0);

     myRef.child("Score").child(name).child("score").addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             if(snapshot.exists()) {
                 s += Integer.parseInt(snapshot.getValue().toString());
                 HashMap map=new HashMap();
                 map.put("name",name);
                 map.put("score",s);
             //    snapshot.getRef().setValue(map);
                // myRef.child("Score").child(name).setValue(map);
             }

                 HashMap map=new HashMap();
                 map.put("name",name);
                 map.put("score",s);
                 myRef.child("Score").child(name).setValue(map);


         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });
 /*      myRef.child("Score").child(name).child("Result").setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });*/
     //   FirebaseDatabase.getInstance().getReference("inviteLink").setValue("abc");

        set=getIntent().getIntExtra("class",1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(scoreactivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}